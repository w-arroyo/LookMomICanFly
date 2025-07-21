import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Router, RouterOutlet, RouterLinkWithHref, ActivatedRoute } from '@angular/router';
import { AuthenticationService } from '../../../services/authentication/authentication.service';

@Component({
  selector: 'app-profile',
  imports: [CommonModule, RouterOutlet, RouterLinkWithHref],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent{

  private router:Router;
  private authenticationService:AuthenticationService;
  selectedSection: string = 'profile';

  constructor(router:Router,authenticationService:AuthenticationService) {
    this.router=router;
    this.authenticationService=authenticationService;
  }

  selectSection(section:string):void {
    this.selectedSection = section;
  }

  isActive(section:string): boolean{
    return this.selectedSection===section;
  }

  logout():void{
    this.authenticationService.logout();
    //this.router.navigate(['home']);
  }
}

