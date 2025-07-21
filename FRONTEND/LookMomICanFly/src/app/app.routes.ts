import { Routes } from '@angular/router';
import { HomeCarouselComponent } from './components/secondary_components/home-carousel/home-carousel.component';
import { ProfileComponent } from './components/main_components/profile/profile.component';
import { AuthGuardService } from './services/guards/auth-guard.service';
import { SaleDetailsComponent } from './components/secondary_components/sale-details/sale-details.component';
import { PaymentScreenComponent } from './components/secondary_components/payment-screen/payment-screen.component';
import { OrderDetailsComponent } from './components/secondary_components/order-details/order-details.component';
import { ContactSectionComponent } from './components/lowkey_components/contact-section/contact-section.component';
import { AboutUsSectionComponent } from './components/lowkey_components/about-us-section/about-us-section.component';
import { SocialSectionComponent } from './components/lowkey_components/social-section/social-section.component';

export const routes: Routes = [
    {
        path:'', redirectTo:'home', pathMatch:'full'
    },
    {
        path: 'home',
        component: HomeCarouselComponent
    },
    {
        path:'authentication',
        loadChildren: ()=> import('./routes/authentication.routes').then(route=> route.routes)
    },
    {
        path:'products',
        loadChildren: ()=> import('./routes/product.routes').then(route=> route.routes)
    },
    {
        path:'account/profile',
        loadChildren: ()=> import('./routes/profile.routes').then(route=> route.routes),
        component:ProfileComponent,
        canActivate: [AuthGuardService]
    },
    {
        path:'asks',
        loadChildren: ()=> import('./routes/ask.routes').then(route=> route.routes),
        canActivate: [AuthGuardService]
    },
    {
        path:'bids',
        loadChildren: ()=> import('./routes/bids.routes').then(route=> route.routes),
        canActivate: [AuthGuardService]
    },
    {
        path:'sales/:saleId',
        component:SaleDetailsComponent,
        canActivate: [AuthGuardService]
    },
    {
        path:'orders/:orderId',
        component:OrderDetailsComponent,
        canActivate:[AuthGuardService]
    },
    {
        path:'payment',
        component:PaymentScreenComponent,
        canActivate:[AuthGuardService]
    },
    {
        path:'contact',
        component:ContactSectionComponent
    },
    {
        path:'about-us',
        component:AboutUsSectionComponent
    },
    {
        path:'social',
        component:SocialSectionComponent
    },
    {
        path:'**', redirectTo:'home'
    }

];
