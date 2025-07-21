import { Component, OnDestroy } from '@angular/core';
import { RouterLinkWithHref } from '@angular/router';
import { NewsletterService } from '../../../services/newsletter/newsletter.service';
import { Subject, takeUntil } from 'rxjs';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-footer',
  imports: [RouterLinkWithHref, FormsModule],
  templateUrl: './footer.component.html',
  styleUrl: './footer.component.css'
})
export class FooterComponent implements OnDestroy{

  private newsletterService:NewsletterService;
  email:string='';
  private destroyStream:Subject<void>=new Subject<void>();

  constructor(newsletterService:NewsletterService){
    this.newsletterService=newsletterService;
  }

  send(){
    if(this.email.trim()!==''){
      this.newsletterService.send(this.email).pipe(
        takeUntil(
          this.destroyStream
        )
      ).subscribe({
        next:(data)=>{
          this.email='';
        },
        error:(error)=>{
          console.log(error.error?.error);
          this.email='';
        }
      });
    }
  }

  ngOnDestroy(): void {
    this.destroyStream.next();
    this.destroyStream.complete();
  }

}
