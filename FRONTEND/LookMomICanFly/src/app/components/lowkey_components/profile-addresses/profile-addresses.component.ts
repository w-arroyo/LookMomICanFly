import { Component, OnDestroy, OnInit } from '@angular/core';
import { Address } from '../../../models/address,model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { catchError, Observable, Subject, takeUntil, tap, throwError } from 'rxjs';
import { AddressService } from '../../../services/address/address.service';

@Component({
  selector: 'app-profile-addresses',
  imports: [CommonModule,FormsModule],
  templateUrl: './profile-addresses.component.html',
  styleUrl: './profile-addresses.component.css'
})
export class ProfileAddressesComponent implements OnInit, OnDestroy{

  private addressService:AddressService;
  addresses$!:Observable<Address[]>;
  isAddingAddress: boolean = false;
  errorMessage: string | null = null;
  newAddress:Address;
  private destroyStream:Subject<void>=new Subject<void>();

  constructor(addressService:AddressService){
    this.addressService=addressService;
    this.newAddress=new Address();
  }

  ngOnInit(): void {
    this.getAddresses();
  }

  private getAddresses(){
    this.addresses$=this.addressService.getUserAddresses().pipe(
      takeUntil(
        this.destroyStream
      ),
      catchError(
        (error)=>{
          console.log(error.error?.error);
          return throwError(()=> new Error(error.error?.error));
        }
      )
    );
  }

  saveAddress(){
    if(this.newAddress.fullName.trim().length<1 || this.newAddress.street.trim().length<1 || this.newAddress.zipCode.trim().length<1 || this.newAddress.city.trim().length<1 || this.newAddress.country.trim().length<1){
      try {
        Number(this.newAddress.zipCode);
      } catch (error) {
        this.errorMessage='Zip code must be numeric.';
        return;
      }
      this.handleObservable(
        this.addressService.saveAddress(this.newAddress)
      );
    }
    else{
      this.errorMessage='All fields are required.';
    }
    try {
      Number(this.newAddress.zipCode);
    } catch (error) {
      this.errorMessage='Zip code must be numeric.';
      return;
    }
    this.handleObservable(
      this.addressService.saveAddress(this.newAddress)
    );
    
  }

  deleteAddress(addressId: string): void {
    this.handleObservable(
      this.addressService.deleteAddress(addressId)
    );
  }

  private handleObservable(observable:Observable<any>){
    observable.pipe(
      takeUntil(
        this.destroyStream
      ),
      tap({
        next: (data)=>{
          this.getAddresses();
          this.errorMessage=data.message;
          this.isAddingAddress=false;
        },
        error: (error)=>{
          this.errorMessage=error.error?.error;
        }
      })
    ).subscribe();
  }

  openAddAddressForm(): void {
    this.isAddingAddress = true;
    this.errorMessage = null;
    this.newAddress=new Address();
   
  }

  cancelAddAddress(): void {
    this.isAddingAddress = false;
    this.errorMessage = null;
  }

  ngOnDestroy(): void {
    this.destroyStream.next();
    this.destroyStream.complete();
  }

}
