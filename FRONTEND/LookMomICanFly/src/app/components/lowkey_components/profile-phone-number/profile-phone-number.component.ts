import { Component, OnDestroy, OnInit } from '@angular/core';
import { PhoneNumber } from '../../../models/phone_number.model';
import { catchError, Observable, of, Subject, takeUntil, tap, throwError } from 'rxjs';
import { ProfileDataService } from '../../../services/profile/profile-data.service';
import { PhoneNumberFormat } from '../../../models/phone_number_format.model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-profile-phone-number',
  imports: [CommonModule,FormsModule],
  templateUrl: './profile-phone-number.component.html',
  styleUrl: './profile-phone-number.component.css'
})
export class ProfilePhoneNumberComponent implements OnInit, OnDestroy{

  private profileDataService:ProfileDataService;
  phoneNumber$!: Observable<PhoneNumber | null>;
  phonePrefixes$!: Observable<PhoneNumberFormat[]>;
  private destroyStream$:Subject<void>=new Subject<void>();
  
  isEditing = false;
  isCreating = false;
  errorMessage: string | null = null;
  
  editedPhonePrefix: string = '';
  editedPhoneNumber: string = '';
  
  constructor(profileDataService:ProfileDataService) {
    this.profileDataService=profileDataService;
  }
  
  ngOnInit(): void {
    this.getPrefixes();
    this.getPhoneNumber();
  }

  private getPrefixes(){
    this.phonePrefixes$=this.profileDataService.getPhonePrefixes().pipe(
      takeUntil(
      this.destroyStream$
    ),
      catchError(
              (error)=>{
                this.errorMessage=error.error?.error;
                return throwError(()=>new Error(error.error?.error));
              }
            )
    );
  }

  private getPhoneNumber(){
    this.phoneNumber$=this.profileDataService.getPhoneNumber().pipe(
      takeUntil(
      this.destroyStream$
    ),
      catchError(
              (error)=>{
                this.errorMessage=error.error?.error;
                return throwError(()=>new Error(error.error?.error));
              }
            )
    );
  }
  
  startEditing(phoneNumber: PhoneNumber): void {
    this.editedPhonePrefix = phoneNumber.prefix;
    this.editedPhoneNumber = phoneNumber.number;
    this.isEditing = true;
    this.errorMessage = null;
  }
  
  startCreating(): void {
    this.editedPhonePrefix = '';
    this.editedPhoneNumber = '';
    this.isCreating = true;
    this.errorMessage = null;
  }
  
  cancelEditing(): void {
    this.isEditing = false;
    this.isCreating = false;
    this.errorMessage = null;
  }
  
  savePhoneNumber(): void {
    if (!this.editedPhonePrefix || !this.editedPhoneNumber) {
      this.errorMessage = 'Both prefix and phone number are amndatory fields.';
      return;
    }
    this.profileDataService.savePhoneNumber(this.editedPhonePrefix,this.editedPhoneNumber)?.pipe(
      takeUntil(
        this.destroyStream$
      ),
      tap({
        next: (data)=>{
          this.isEditing = false;
          this.isCreating = false;
          this.errorMessage = null;
          this.getPhoneNumber();
        },
        error: (error)=>{
          this.isEditing = false;
          this.isCreating = false;
          this.errorMessage=error.error?.error;
        }
      })
    )
    .subscribe();
  }
  
  deletePhoneNumber(): void {
    this.profileDataService.deletePhoneNumber()
    .pipe(
      takeUntil(
        this.destroyStream$
      ),
      tap({
        next: (data)=>{
          this.isEditing = false;
          this.isCreating = false;
          this.errorMessage=data.message;
          this.getPhoneNumber();
        },
        error: (error)=>{
          this.isEditing = false;
          this.isCreating = false;
          this.errorMessage=error.error?.error;
        }
      })
    )
    .subscribe();
  }

  ngOnDestroy(): void {
    this.destroyStream$.next();
    this.destroyStream$.complete();
  }

}
