import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {PeopleComponent} from "./people/people.component";
import {PetsComponent} from "./pets/pets.component";
import {TransactionsComponent} from "./transactions/transactions.component";
import {TransactionNewComponent} from "./transactions/transaction-new/transaction-new.component";
import {PetNewComponent} from "./pets/pet-new/pet-new.component";
import {PersonNewComponent} from "./people/person-new/person-new.component";
import {FoodsComponent} from "./foods/foods.component";
import {FoodNewComponent} from "./foods/food-new/food-new.component";
import {ToysComponent} from "./toys/toys.component";
import {ToyNewComponent} from "./toys/toy-new/toy-new.component";

const routes: Routes = [

  {path: 'people', component: PeopleComponent},
  {path: 'person', component: PersonNewComponent},

  {path: 'pets', component: PetsComponent},
  {path: 'pet-new', component: PetNewComponent},

  {path: 'transactions', component: TransactionsComponent},
  {path: 'transaction-new', component: TransactionNewComponent},

  {path: 'foods', component: FoodsComponent},
  {path: 'food-new', component: FoodNewComponent},

  {path: 'toys', component: ToysComponent},
  {path: 'toy-new', component: ToyNewComponent},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
