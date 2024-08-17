import {
  IsEmail,
  IsNotEmpty,
  IsNumber,
  IsPhoneNumber,
  IsString,
} from "class-validator";
import { CartItemDTO } from "./cart.item.dto";

export class OrderDTO {
  @IsNumber()
  userId: number;

  @IsString()
  @IsNotEmpty()
  fullname: string;

  @IsNotEmpty()
  @IsEmail()
  email: string;

  @IsPhoneNumber()
  phone_number: string;

  @IsString()
  @IsNotEmpty()
  address: string;

  @IsString()
  note: string;

  @IsNumber()
  total_money: number;

  @IsString()
  shipping_method: string;

  @IsString()
  payment_method: string;

  @IsString()
  coupon_code: string;

  cart_items: CartItemDTO[];

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
  }
}
