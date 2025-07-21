import { Routes } from "@angular/router";
import { ProductSummaryListComponent } from "../components/main_components/product-summary-list/product-summary-list.component";
import { ProductDetailsComponent } from "../components/main_components/product-details/product-details.component";

export const routes: Routes = [
    {
        path:'category/:category',
        component: ProductSummaryListComponent
    },
    {
        path:'search/:product-name',
        component:ProductSummaryListComponent
    },
    {
        path:'best-sellers',
        component:ProductSummaryListComponent
    },
    {
        path:'all',
        component:ProductSummaryListComponent
    },
    {
        path:':category/details/:productId',
        component:ProductDetailsComponent
    }
]