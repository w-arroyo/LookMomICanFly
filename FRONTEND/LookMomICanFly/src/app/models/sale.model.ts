import { Transaction } from "./transaction.model";

export class Sale extends Transaction{

    shippingFee!:number;
    percentage!:number;

}