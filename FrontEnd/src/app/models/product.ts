import { ProductImage } from "./product_image";

export interface Product {
  id: number;
  name: string;
  price: number;
  thumbnail: string;
  description: string;
  category_id: number;
  url: string;
  product_images : ProductImage[];
}
