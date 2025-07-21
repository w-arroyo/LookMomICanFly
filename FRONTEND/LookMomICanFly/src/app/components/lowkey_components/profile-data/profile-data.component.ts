import { Component, OnDestroy, OnInit } from '@angular/core';
import { UserProfileData } from '../../../models/user_profile.model';
import { SellingFee } from '../../../models/selling_fee.model';
import { CommonModule } from '@angular/common';
import { catchError, Observable, Subject, takeUntil, throwError } from 'rxjs';
import { ProfileDataService } from '../../../services/profile/profile-data.service';

@Component({
  selector: 'app-profile-data',
  imports: [CommonModule],
  templateUrl: './profile-data.component.html',
  styleUrl: './profile-data.component.css'
})
export class ProfileDataComponent implements OnInit,OnDestroy{

  private profileDataService:ProfileDataService;
  profileData$!:Observable<UserProfileData>;
  userLevel$!:Observable<SellingFee>;
  private destroyStream:Subject<void>=new Subject<void>();

  constructor(profileDataService:ProfileDataService){
    this.profileDataService=profileDataService;
  }

  ngOnInit(): void {
    this.getUserData();
    this.getUserLevel();
  }

  private getUserData(){
    this.profileData$=this.handleObservable(this.profileDataService.getProfileData());
  }

  private getUserLevel(){
    this.userLevel$=this.handleObservable(this.profileDataService.getUserLevel());
  }

  private handleObservable(observable:Observable<any>){
      return observable.pipe(
            takeUntil(
            this.destroyStream
          ),
        catchError(
          (error)=>{
            const message=error.error?.error;
            console.log(message);
            return throwError(()=>new Error(message));
          }
        )
      );
    }

    ngOnDestroy(): void {
      this.destroyStream.next();
      this.destroyStream.complete();
    }

}
