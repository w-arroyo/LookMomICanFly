import { Address } from "./address,model";
import { ProductSummary } from "./product_summary.model";

export class PostDetails{

    id!:string;
    size!:string;
    addressDTO!:Address;
    productSummaryDTO!:ProductSummary;
    amount!:number;

}