import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PetsComponent } from './pets/pets.component';
import { PeopleComponent } from './people/people.component';
import { TransactionsComponent } from './transactions/transactions.component';
import { PersonNewComponent } from './people/person-new/person-new.component';
import { PetListComponent } from './pets/pet-list/pet-list.component';
import { PetNewComponent } from './pets/pet-new/pet-new.component';
import { TransactionListComponent } from './transactions/transaction-list/transaction-list.component';
import { TransactionNewComponent } from './transactions/transaction-new/transaction-new.component';
import {HttpClientModule} from "@angular/common/http";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {PersonService} from "./people/shared/person.service";
import {PetService} from "./pets/shared/pet.service";
import {TransactionService} from "./transactions/shared/transaction.service";
import { FoodsComponent } from './foods/foods.component';
import { ToysComponent } from './toys/toys.component';
import { FoodListComponent } from './foods/food-list/food-list.component';
import { FoodNewComponent } from './foods/food-new/food-new.component';
import { ToyListComponent } from './toys/toy-list/toy-list.component';
import { ToyNewComponent } from './toys/toy-new/toy-new.component';
import {FoodService} from "./foods/shared/food.service";
import {ToyService} from "./toys/shared/toy.service";
import {PersonListComponent} from "./people/person-list/person-list.component";

@NgModule({
  declarations: [
    AppComponent,
    PetsComponent,
    PeopleComponent,
    TransactionsComponent,
    PersonListComponent,
    PersonNewComponent,
    PetListComponent,
    PetNewComponent,
    TransactionListComponent,
    TransactionNewComponent,
    FoodsComponent,
    ToysComponent,
    FoodListComponent,
    FoodNewComponent,
    ToyListComponent,
    ToyNewComponent
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        HttpClientModule,
        FormsModule,
        ReactiveFormsModule
    ],
  providers: [PersonService,PetService,TransactionService,FoodService,ToyService],
  bootstrap: [AppComponent]
})
export class AppModule { }
