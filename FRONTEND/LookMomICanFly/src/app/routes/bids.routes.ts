import { Routes } from "@angular/router";
import { CreateBidComponent } from "../components/main_components/create-bid/create-bid.component";
import { AuthGuardService } from "../services/guards/auth-guard.service";
import { BidDetailsComponent } from "../components/secondary_components/bid-details/bid-details.component";

export const routes:Routes=[
    {
        path:'create/product/:productId/size/:size',
        component: CreateBidComponent,
        canActivate: [AuthGuardService]
    },
    {
        path:':bidId',
        component:BidDetailsComponent,
        canActivate: [AuthGuardService]
    }
]