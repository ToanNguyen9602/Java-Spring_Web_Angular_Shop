import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/dtos/product/product';
import { environtment } from 'src/app/environments/environment';
import { ProductService } from 'src/app/service/product.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  products: Product[] = [];
  currentPage: number = 1;
  itemPerPage: number = 10;
  pages: number[] = [];
  totalPages: number = 0;
  visiblePages: number[] = [];

  constructor(private productService: ProductService) { }

  ngOnInit(): void {
    this.getProducts(this.currentPage, this.itemPerPage);
  }

  getProducts(page: number, limit: number){
    this.productService.getProducts(page, limit).subscribe({
      next: (response: any) => {
        debugger
        response.products.forEach((product: Product) => { 
          //show image
          product.url = `${environtment.apiBaseUrl}/products/images/${product.thumbnail}`;
        });
        this.products = response.products;
        this.totalPages = response.totalPages;
        this.visiblePages = this.generateVisiblePageArray(this.currentPage, this.totalPages);
      },
      complete: () => {debugger},
      error: (error: any) => {
        console.error('Error getting products: ', error);
      }
    });
  }
  //khi bam chuyen phan trang product khac
  onPageChange(page: number) {
    debugger;
    this.currentPage = page;
    this.getProducts(this.currentPage, this.itemPerPage);
  }

  generateVisiblePageArray(currentPage: number, totalPages: number):number[] {
    const maxVisiblePages = 5;
    const halfVisiblePage = Math.floor(maxVisiblePages/2);

    let startPage = Math.max(currentPage - halfVisiblePage, 1);
    let endPage = Math.min(startPage + maxVisiblePages - 1, totalPages);
  
    if(endPage - startPage + 1 < maxVisiblePages) {
      startPage = Math.max(endPage - maxVisiblePages + 1, 1);
    }

    return new Array(endPage - startPage + 1).fill(0)
        .map((_, index) => startPage + index);
  }

}
