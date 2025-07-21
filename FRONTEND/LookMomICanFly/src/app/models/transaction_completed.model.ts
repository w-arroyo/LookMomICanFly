import { Address } from "./address,model";
import { Product } from "./product.model";

export class TransactionSuccess{

    id!:string;
    reference!:string;
    size!:string;
    address!:Address;
    product!:Product;
    amount!:number;

}