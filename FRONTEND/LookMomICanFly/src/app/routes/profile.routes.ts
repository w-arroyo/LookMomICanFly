import { Routes } from "@angular/router"
import { ProfileComponent } from "../components/main_components/profile/profile.component"
import { ProfileDataComponent } from "../components/lowkey_components/profile-data/profile-data.component"
import { ProfileAddressesComponent } from "../components/lowkey_components/profile-addresses/profile-addresses.component"
import { ProfileBankAccountComponent } from "../components/lowkey_components/profile-bank-account/profile-bank-account.component"
import { ProfilePhoneNumberComponent } from "../components/lowkey_components/profile-phone-number/profile-phone-number.component"
import { ProfileUpdateDataComponent } from "../components/lowkey_components/profile-update-data/profile-update-data.component"
import { ProfileTransactionsComponent } from "../components/lowkey_components/profile-transactions/profile-transactions.component"
import { ProfileLikedProductsListComponent } from "../components/lowkey_components/profile-liked-products-list/profile-liked-products-list.component"

export const routes:Routes=[
    {
        path:'',
        redirectTo: 'my-profile',
        pathMatch: 'full'
    },
    {
        path:'my-profile',
        component:ProfileDataComponent
    },
    {
        path:'addresses',
        component:ProfileAddressesComponent
    },
    {
        path:'bank-account',
        component:ProfileBankAccountComponent
    },
    {
        path:'phone-number',
        component:ProfilePhoneNumberComponent
    },
    {
        path:'update',
        component:ProfileUpdateDataComponent
    },
    {
        path:'liking',
        component:ProfileLikedProductsListComponent
    },
    {
        path:':section',
        component: ProfileTransactionsComponent
    }
    
]