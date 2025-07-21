import { Component, OnDestroy, OnInit } from '@angular/core';
import { AskService } from '../../../services/ask/ask.service';
import { BidService } from '../../../services/bid/bid.service';
import { PostSummary } from '../../../models/post_summary.model';
import { BehaviorSubject, catchError, Observable, Subject, takeUntil, throwError } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';
import { PostBoxSummaryComponent } from '../../lowkey_components/post-box-summary/post-box-summary.component';
import { CommonModule } from '@angular/common';
import { LoadingScreenComponent } from '../../lowkey_components/loading-screen/loading-screen.component';
import { SuccessfullRequest } from '../../../models/successful_request.model';
import { TransactionSuccess } from '../../../models/transaction_completed.model';
import { UpdatePost } from '../../../models/update_post.model';

@Component({
  selector: 'app-post-list',
  imports: [PostBoxSummaryComponent, CommonModule, LoadingScreenComponent],
  templateUrl: './post-list.component.html',
  styleUrl: './post-list.component.css'
})
export class PostListComponent implements OnInit,OnDestroy{

  private askService:AskService;
  private bidService:BidService;

  posts$!:Observable<PostSummary[]>;
  private destroyStream:Subject<void>=new Subject<void>();
  
  statusMessage: string = '';
  messageType: string = 'success';

  sectionParam:string='';
  private router:Router;
  private activatedRoute:ActivatedRoute;
  loadingBehaviour:BehaviorSubject<boolean>=new BehaviorSubject<boolean>(true);
  loading$:Observable<boolean>=this.loadingBehaviour.asObservable();

  constructor(askService:AskService,bidService:BidService,router:Router,activatedRoute:ActivatedRoute){
    this.askService=askService;
    this.bidService=bidService;
    this.router=router;
    this.activatedRoute=activatedRoute;
  }

  ngOnInit(): void {
    this.loadingBehaviour.next(true)
    this.activatedRoute.paramMap.pipe(
      takeUntil(
        this.destroyStream
      )
    )
    .subscribe({
      next: (data)=>{
        this.sectionParam=data.get('section') || '';
        if(this.sectionParam==='')
          this.router.navigate(['account/profile/my-profile']);
        else this.getPosts();
      },
      error: (error)=> this.router.navigate(['account/profile/my-profile'])
    });
  }

  private getPosts(){
    if(this.sectionParam==='selling')
      this.getAsks();
    else this.getBids();
    this.loadingBehaviour.next(false);
  }

  private getAsks(){
    this.posts$=this.handleObservable(
      this.askService.findAll()
    );
  }

  private getBids(){
    this.posts$=this.handleObservable(
      this.bidService.findAll()
    )
  }

  private handleObservable(observable:Observable<any>){
    return observable.pipe(
      takeUntil(
        this.destroyStream
      ),
          catchError(
            (error)=>{
              console.log(error);
              //this.router.navigate(['home']);
              return throwError(()=> new Error(error.error?.error));
            }
          )
        );
  }

  changeAmount(post:UpdatePost){
    this.loadingBehaviour.next(true);
    if(this.sectionParam==='selling')
      this.handleSubscription(
        this.askService.updateAsk(post),'sales'
      )
    else this.handleSubscription(
      this.bidService.updateBid(post),'orders'
    )
  }

  private handleSubscription(observable:Observable<any>, route:string){
    this.handleObservable(observable).pipe(
      takeUntil(
        this.destroyStream
      )
    ).subscribe({
      next: (data)=>{
        this.loadingBehaviour.next(false);
        this.statusMessage='';
        if('status' in data){
          this.router.navigate([route+'/'+(data as TransactionSuccess).id]);
        }
        this.getPosts();
      },
      error: (error)=>{
        console.log(error);
        this.loadingBehaviour.next(false);
        this.statusMessage=error;
        this.messageType='error';
        this.loadingBehaviour.next(false);
      }
    });
  }

  ngOnDestroy(): void{
    this.destroyStream.next();
    this.destroyStream.complete();
  }

}
