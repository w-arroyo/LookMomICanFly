import { PostDetails } from "./full_post.model";
import { SellingFee } from "./selling_fee.model";

export class AskDetails extends PostDetails{

    shippingFee!:number;
    sellingFee!:SellingFee;

}