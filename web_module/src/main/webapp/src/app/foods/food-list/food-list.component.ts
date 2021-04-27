import { Component, OnInit } from '@angular/core';

import {Router} from "@angular/router";
import {Food} from "../shared/food.model";
import {FoodService} from "../shared/food.service";
import {Person} from "../../people/shared/person.model";

@Component({
  selector: 'app-food-list',
  templateUrl: './food-list.component.html',
  styleUrls: ['./food-list.component.css']
})
export class FoodListComponent implements OnInit {
  errorMessage: string | undefined;
  foods: Food[] | undefined;
  selectedFood: Food | undefined;
  constructor(private foodService: FoodService, private router:Router) {this.foods = []; }

  ngOnInit(): void {
  this.getFoods();
  }
  getFoods(){
    return this.foodService.getFoods()
      .subscribe(
        foods => this.foods = foods
      );
  }

  onSelect(food: Food): void {
    this.selectedFood = food;
  }

  sortFood():void{
    this.foods?.sort((a, b) => {
      // @ts-ignore
      return (a.price > b.price) ? 1 : -1;
    })
  }

  filterFood(price:string):void{
    // @ts-ignore
    this.foods = this.foods?.filter(x => x.price > +price);
  }

}
