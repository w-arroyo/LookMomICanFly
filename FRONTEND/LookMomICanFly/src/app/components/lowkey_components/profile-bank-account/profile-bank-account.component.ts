import { CommonModule } from '@angular/common';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { BankAccount } from '../../../models/bank_account.model';
import { FormsModule } from '@angular/forms';
import { ProfileDataService } from '../../../services/profile/profile-data.service';
import { BehaviorSubject, catchError, Observable, of, Subject, takeUntil, tap, throwError } from 'rxjs';

@Component({
  selector: 'app-profile-bank-account',
  imports: [CommonModule, FormsModule],
  templateUrl: './profile-bank-account.component.html',
  styleUrl: './profile-bank-account.component.css'
})
export class ProfileBankAccountComponent implements OnInit, OnDestroy{
  private profileDataService:ProfileDataService;
  bankAccount$!:Observable<BankAccount | null>;
  isEditing = false;
  isCreating = false;
  errorMessage: string | null = null;
  editedAccountNumber: string = '';
  private destroyStream:Subject<void>=new Subject<void>();

  constructor(profileDataService:ProfileDataService){
    this.profileDataService=profileDataService;
  }

  ngOnInit(): void {
    this.loadBankAccount();
  }

  private loadBankAccount(){
    this.bankAccount$=this.profileDataService.getBankAccount().pipe(
      takeUntil(
      this.destroyStream
    ),
      catchError(
        (error)=>{
          this.errorMessage=error.error?.error;
          return of(null)
        }
      )
    );
  }

  startEditing(bankAccount:BankAccount | null) {
    if (bankAccount) {
      this.editedAccountNumber = bankAccount.number;
      this.isEditing = true;
      this.isCreating = false;
      this.errorMessage = null;
    }
  }

  startCreating() {
    this.editedAccountNumber = '';
    this.isEditing = true;
    this.isCreating = true;
    this.errorMessage = null;
  }

  cancelEditing() {
    this.isEditing = false;
    this.isCreating = false;
    this.errorMessage = null;
  }

  saveAccount() {
    if (!this.editedAccountNumber || this.editedAccountNumber.trim().length < 23) {
      this.errorMessage = 'Please enter a valid bank account number.';
      return;
    }
    this.profileDataService.saveBankAccount(this.editedAccountNumber.trim()).pipe(
      tap({
        next: (data)=>{
          this.isEditing = false;
          this.isCreating = false;
          this.errorMessage = null;
          this.loadBankAccount();
        },
        error: (error)=>{
          console.log(error.error?.error);
          this.errorMessage = error.error?.error;
        }
      }),
      takeUntil(
        this.destroyStream
      )
    ).subscribe();
  }

  deleteAccount(){
    this.profileDataService.deleteBankAccount().pipe(
      takeUntil(
        this.destroyStream
      ),
      tap(
        (data)=>{
          this.isEditing = false;
          this.isCreating = false;
          this.errorMessage=data.message;
          this.loadBankAccount();
        }
      )
    ).subscribe();
  }

  ngOnDestroy(): void {
    this.destroyStream.next();
    this.destroyStream.complete();
  }

}
