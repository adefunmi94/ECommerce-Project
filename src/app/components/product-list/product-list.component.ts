import { Component, OnInit } from '@angular/core';
import { ProductService } from 'src/app/services/product.service';
import { Product } from 'src/app/common/product';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list-grid.component.html',
  // templateUrl: './product-list-table.component.html',
  // templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {

  products: Product[] = [];
  currentCategoryId: number = 1;
  previousCategoryId: number =1;
  currentCategoryName: string = "";
  searchMode: boolean = false;


  // New properties for pagination
  
  thePageNumber: number = 1;
  thePageSize: number= 50;
  theTotalelements: number = 0;
  


  constructor(private productService: ProductService, 
    private route: ActivatedRoute) { }

  ngOnInit() {
    this.route.paramMap.subscribe(() =>{
      this.listProducts()
    });
  }

  listProducts() {

    this.searchMode = this.route.snapshot.paramMap.has('keyword');

    if(this.searchMode){
      this.handleSearchProducts();
    }
    else{

      this.hanldleListProduct();
    }


// not get the products for  the givenn category

    
  }
  handleSearchProducts() {
    const theKeyword: string = String(this.route.snapshot.paramMap.get('keyword'));
 
    // now search for the product
    this.productService.searproducts(theKeyword).subscribe(
      data => {
        this.products = data;
      }
    )
 
  }

  hanldleListProduct(){
    // route: Use the acticated route, snapshot= State of route at this given moment in time
// paramMap= Map of all the route parameter

 // check if "id" parameter is available
 const hasCategoryId: boolean = this.route.snapshot.paramMap.has('id');
 if (hasCategoryId){
   // get th "id param string, convert string to a number using '+' symbol"
   this.currentCategoryId = Number(this.route.snapshot.paramMap.get('id'));
 
    // get the "name" param string
    this.currentCategoryName = String(this.route.snapshot.paramMap.get('name'));
 }
 else{
   // not catgory id available .. default to category id 1
   this.currentCategoryId = 1;
 
   this.currentCategoryName = 'Books';
 }         

//  Check if we have a different categories than previous
// Note: Angular will reuse a component if it is currently being viewed
// 

// if we have a different categories id than previous
// Then set thePageNumber back to 1


if(this.previousCategoryId != this.currentCategoryId){
  this.thePageNumber=1;
}

this.previousCategoryId = this.currentCategoryId
console.log(`currentCategoryId=${this.currentCategoryId}, thePageSize=${this.thePageNumber}`);

// now we get the product for the given category Id
 this.productService.getProductPaginate(this.thePageNumber - 1,
                                   this.thePageSize,
                                   this.currentCategoryId)
                                  .subscribe(this.processResult());

  }

  processResult() {
    return data => {
      this.products = data._embedded.products;
      this.thePageNumber = data.page.number + 1;
      this.thePageSize = data.page.size;
      this.theTotalelements = data.page.totalElements;
    };
  }
}
