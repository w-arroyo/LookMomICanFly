import { ShippingOption } from "./shipping_option.model";
import { Transaction } from "./transaction.model";

export class Order extends Transaction{

    operationalFee!:number;
    shippingOption!:ShippingOption;

}