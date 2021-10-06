import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Product } from '../common/product';
import {map} from 'rxjs/operators';
import { ProductCategory } from '../common/product-category';


@Injectable({
  providedIn: 'root'
})
export class ProductService {
 


  private baseUrl = 'http://localhost:8080/api/products';
 
  private categoryUrl ='http://localhost:8080/api/product_category';

  constructor(private httpClient: HttpClient) { }

  getProductList(theCategoryId: number): Observable<Product[]> {
  
    // need to build url based on category id  

    const searchUrl = `${this.baseUrl}/search/findByCategoryId?id=${theCategoryId}`;
    
    return this.getProducts(searchUrl);
  }

  // Pagination Method

  getProductPaginate(thePage: number, 
    thePageSize: number,
    theCategoryId: number): Observable<GetResponseProducts> {
  
    // need to build url based on category id  page & Size  
    const searchUrl = `${this.baseUrl}/search/findByCategoryId?id=${theCategoryId}`
    +`&page=${thePage}&size=${thePageSize}`;
    
    return this.httpClient.get<GetResponseProducts>(searchUrl);
  }


  // Search product by keyword method
  searproducts(theKeyword: string):Observable<Product[]> {
    // need to build url based on keyword  
   const keywordUrl = `${this.baseUrl}/search/findByNameContaining?name=${theKeyword}`;
   
   return this.getProducts(keywordUrl);
 }




  getProductCategories(): Observable<ProductCategory[]>{
    return this.httpClient.get<GetResponseProductCategory>(this.categoryUrl).pipe(
      map(response => response._embedded.productCategory)
       ) ;
  }


// extracted Method, for code re-use
  private getProducts(keywordUrl: string): Observable<Product[]> {
    return this.httpClient.get<GetResponseProducts>(keywordUrl).pipe(
      map(response => response._embedded.products)
    );
  }
// update product service to call url

getProduct(theProductId: number): Observable<Product> {

  // build the url base on product id

  const productUrl= `${this.baseUrl}/${theProductId}`;
  return this.httpClient.get<Product>(productUrl);

  
}


}

interface GetResponseProducts {
  _embedded: {
    products: Product[];
  },
  page:{
    size:number,
    totalElement: number,
    totalPages: number,
    number: number
  }
}


interface GetResponseProductCategory {
  _embedded: {
    productCategory: ProductCategory[];
  }
}