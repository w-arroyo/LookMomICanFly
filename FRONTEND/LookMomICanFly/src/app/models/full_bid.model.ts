import { PostDetails } from "./full_post.model";
import { ShippingOption } from "./shipping_option.model";

export class BidDetails extends PostDetails{

    shippingOptionDTO!:ShippingOption;
    operatingFee!:number;

}