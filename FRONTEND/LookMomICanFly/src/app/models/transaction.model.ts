import { Address } from "./address,model";
import { Product } from "./product.model";
import { TransactionStatus } from "./transaction_status.model";

export class Transaction{

    id!:string;
    reference!:string;
    size!:string;
    trackingNumber!:string;
    address!:Address;
    product!:Product;
    amount!:number;
    status!:TransactionStatus;

}