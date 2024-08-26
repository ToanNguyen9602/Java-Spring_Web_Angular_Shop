import {
  IsEmail,
  IsNotEmpty,
  IsNumber,
  IsPhoneNumber,
  IsString,
} from "class-validator";
import { CartItemDTO } from "./cart.item.dto";

export class OrderDTO {
  userId: number;
  fullname: string;
  email: string;
  phone_number: string;
  address: string;
  note: string;
  total_money: number;
  shipping_method: string;
  payment_method: string;
  coupon_code: string;
  cart_items: { product_id: number; quantity: number }[];

  constructor(data: any) {
    this.userId = data.user_id;
    this.fullname = data.fullname;
    this.email = data.email;
    this.phone_number = data.phone_number;
    this.cart_items = data.cart_items;
    this.total_money = data.total_money;
    this.address = data.address;
    this.note = data.note;
    this.shipping_method = data.shipping_method;
    this.payment_method = data.payment_method;
    this.coupon_code = data.coupon_code;
    this.cart_items = data.cart_items;
  }
}
