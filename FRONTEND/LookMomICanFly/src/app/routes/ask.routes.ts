import { Routes } from "@angular/router";
import { CreateAskComponent } from "../components/main_components/create-ask/create-ask.component";
import { AuthGuardService } from "../services/guards/auth-guard.service";
import { AskDetailsComponent } from "../components/secondary_components/ask-details/ask-details.component";

export const routes:Routes=[
    {
        path:'create/product/:productId/size/:size',
        component: CreateAskComponent,
        canActivate: [AuthGuardService]
    },
    {
        path:':askId',
        component:AskDetailsComponent,
        canActivate: [AuthGuardService]
    }
]