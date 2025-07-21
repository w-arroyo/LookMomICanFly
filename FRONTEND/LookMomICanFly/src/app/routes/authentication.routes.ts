import { Routes } from "@angular/router";
import { AuthenticationComponent } from "../components/main_components/authentication/authentication.component";
import { NoGuardService } from "../services/guards/no-guard.service";

export const routes:Routes=[
    {
        path:'',
        component:AuthenticationComponent,
        canActivate: [NoGuardService]
    },
    {
        path:':authentication',
        component:AuthenticationComponent,
        canActivate: [NoGuardService]
    }
]