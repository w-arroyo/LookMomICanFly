import { ProductSummary } from "./product_summary.model";

export class TransactionSummary{

    id!:string;
    product!:ProductSummary;
    size!:string;
    status!:string;
    amount!:number;
    date!:string;

}