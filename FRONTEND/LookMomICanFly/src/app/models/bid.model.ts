import { Post } from "./post.model";

export class Bid extends Post{

    shippingOptionId!:string;
    paymentIntentId!:string;

}