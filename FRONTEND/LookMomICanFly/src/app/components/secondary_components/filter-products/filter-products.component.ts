import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { FilterProductsService } from '../../../services/filter_products/filter-products.service';
import { ProductSummaryService } from '../../../services/product_summary/product-summary.service';
import { Observable, Subject, takeUntil } from 'rxjs';
import { FilterSelectedOptions } from '../../../models/filter_options.model';
import { FormArray, FormBuilder, FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-filter-products',
  imports: [CommonModule,ReactiveFormsModule],
  templateUrl: './filter-products.component.html',
  styleUrl: './filter-products.component.css'
})
export class FilterProductsComponent implements OnInit, OnDestroy{

  private filterProductsService:FilterProductsService;
  private productSummaryService: ProductSummaryService;
  private filterSelectedOptions:FilterSelectedOptions;
  private formBuilder:FormBuilder;
  filterForm:FormGroup;
  isOpen:boolean=false;
  yearList:number[]=[];
  colors:string[]=[];
  manufacturers:string[]=[];
  subcategories:string[]=[];
  category!:string|null;
  private destroyStream:Subject<void>=new Subject<void>();

  constructor(filterProductsService:FilterProductsService, productSummaryService: ProductSummaryService,formBuilder:FormBuilder){
    this.filterProductsService=filterProductsService;
    this.productSummaryService=productSummaryService;
    this.formBuilder=formBuilder;
    this.filterSelectedOptions=new FilterSelectedOptions();
    this.filterForm=this.formBuilder.group({
      subcategories:this.formBuilder.array([]),
      years:this.formBuilder.array([]),
      colors:this.formBuilder.array([]),
      manufacturers:this.formBuilder.array([])
    });
  }

  ngOnInit(): void {
    this.subscribeToVisibility();
    this.subscribeToYears();
    this.subscribeToColors();
    this.subscribeToManufacturers();
    this.subscribeToSubcategories();
  }

  private createCheckboxesControls(items:any[],formArray:FormArray){
    while(formArray.length>0)
      formArray.removeAt(0);
    items.forEach(
      (item)=>{
        formArray.push(
          new FormControl(false)
        );
      }
    );
  }

  private getFormArray(section:string){
    return this.filterForm.get(section) as FormArray;
  }

  private subscribeToVisibility(){
    this.filterProductsService.isVisible$.pipe(
      takeUntil(
        this.destroyStream
      )
    )
    .subscribe({
      next: (data)=> {
        this.isOpen=data
      },
      error: (error)=>{
        console.log(error);
        this.isOpen=false;
      }
    });
  }

  private subscribeToYears(){
    this.filterProductsService.getReleaseYears().pipe(
      takeUntil(
        this.destroyStream
      )
    ).subscribe({
      next: (data)=> {
        this.yearList=data;
        this.createCheckboxesControls(data,this.getFormArray('years'));
      },
      error: (error) => {
        console.log(error);
        this.yearList=[]
      }
    });
  }

  private subscribeToColors(){
    this.filterProductsService.getDifferentColors().pipe(
      takeUntil(
        this.destroyStream
      )
    ).subscribe({
      next: (data)=> {
        this.colors=data;
        this.createCheckboxesControls(data,this.getFormArray('colors'));
      },
      error: (error) => {
        console.log(error);
        this.colors=[]
      }
    });
  }

  private subscribeToManufacturers(){
    this.filterProductsService.getDifferentManufacturers().pipe(
      takeUntil(
        this.destroyStream
      )
    ).subscribe({
      next: (data)=> {
        this.manufacturers=data;
        this.createCheckboxesControls(data,this.getFormArray('manufacturers'));
      },
      error: (error) => {
        console.log(error);
        this.manufacturers=[]
      }
    });
  }

  private subscribeToSubcategories(){
    this.productSummaryService.currentCategorySubject.asObservable().pipe(
      takeUntil(
        this.destroyStream
      )
    ).subscribe({
      next: (data)=> {
        if(data!=null){
          this.category=data;
          this.filterProductsService.getCategorySubcategories(this.category).pipe(
            takeUntil(
              this.destroyStream
            )
          ).subscribe({
            next: (data)=> {
              this.subcategories=data;
              this.createCheckboxesControls(data,this.getFormArray('subcategories'));
            },
            error: (error) => {
              console.log(error);
              this.subcategories=[]
            }
          });
        }
      },
      error: (error) => {
        console.log(error);
        this.category=null;
      }
    });
  }

  private getSelectedValues(list:any[], formArray:FormArray): any[]{
    return list.filter((unused,position)=>{ // item,index
      return formArray.at(position).value // only values with the form control as TRUE
    });
  }

  private getSelectedCheckboxes(){
    this.filterSelectedOptions.years=this.getSelectedValues(
      this.yearList,this.getFormArray('years')
    );
    this.filterSelectedOptions.colors=this.getSelectedValues(
      this.colors,this.getFormArray('colors')
    );
    this.filterSelectedOptions.subcategories=this.getSelectedValues(
      this.subcategories,this.getFormArray('subcategories')
    );
    this.filterSelectedOptions.manufacturers=this.getSelectedValues(
      this.manufacturers,this.getFormArray('manufacturers')
    );
  }

  applyFilters() {
    this.getSelectedCheckboxes();
    if(!this.filterSelectedOptions.checkIfEmpty())
      this.productSummaryService.getFilteredProducts(this.filterSelectedOptions);
    this.filterProductsService.isVisibleSubject.next(false);
  }

  closeFilterEvent() {
    this.clearSelection();
    this.filterProductsService.isVisibleSubject.next(false);
  }

  preventScrollJump() {
    // this just to fix random ass movements inside the list when you scroll
    setTimeout(() => {
      // makes sure angular processed all changes
    }, 0);
  }

  clearSelection(){
    this.getFormArray('years').controls.forEach((item)=>{
      item.setValue(false)
    });
    this.getFormArray('manufacturers').controls.forEach((item)=>{
      item.setValue(false)
    });
    this.getFormArray('subcategories').controls.forEach((item)=>{
      item.setValue(false)
    });
    this.getFormArray('colors').controls.forEach((item)=>{
      item.setValue(false)
    });
    this.filterSelectedOptions.clearOptions();
  }

  ngOnDestroy(): void {
    this.destroyStream.next();
    this.destroyStream.complete();
  }

}
