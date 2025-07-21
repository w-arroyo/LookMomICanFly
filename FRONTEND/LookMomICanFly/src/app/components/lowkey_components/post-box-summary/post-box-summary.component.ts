import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ProductBoxSummaryComponent } from '../product-box-summary/product-box-summary.component';
import { PostSummary } from '../../../models/post_summary.model';
import { UpdatePost } from '../../../models/update_post.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-post-box-summary',
  imports: [CommonModule, FormsModule, ProductBoxSummaryComponent],
  templateUrl: './post-box-summary.component.html',
  styleUrl: './post-box-summary.component.css'
})
export class PostBoxSummaryComponent{

  @Input() post!: PostSummary;
  @Input() sectionParam!:string;
  @Output() edit = new EventEmitter<UpdatePost>();
  private router:Router;
  
  isEditing = false;
  errorMessage: string | null = null;
  editedAmount = 0;

  constructor(router:Router){
    this.router=router;
  }

  ngOnInit() {
    this.editedAmount = this.post.amount;
  }

  onEdit() {
    this.isEditing = true;
    this.editedAmount = this.post.amount;
    this.errorMessage = null;
  }

  onSave() {
    if(this.editedAmount < 1){
      this.errorMessage = 'Amount must be positive.';
      return;
    }
    this.errorMessage = null;
    this.isEditing = false;
    const updatePost=new UpdatePost();
    updatePost.amount=this.editedAmount;
    updatePost.postId=this.post.id;
    this.edit.emit(updatePost);
  }

  gotToProduct(){
    this.router.navigate([`/products/${this.post.product.category.toLowerCase()}/details/${this.post.product.id}`]);
  }

  goToPost(){
    if(this.sectionParam==='selling')
      this.router.navigate(['asks/'+this.post.id]);
    else if(this,this.sectionParam==='buying')
      this.router.navigate(['bids/'+this.post.id]);
  }

  onCancel() {
    this.isEditing = false;
    this.editedAmount = this.post.amount;
    this.errorMessage = null;
  }

}
