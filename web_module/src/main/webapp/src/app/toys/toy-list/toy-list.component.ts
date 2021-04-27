import { Component, OnInit } from '@angular/core';
import {Food} from "../../foods/shared/food.model";
import {FoodService} from "../../foods/shared/food.service";
import {Router} from "@angular/router";
import {Toy} from "../shared/toy.model";
import {ToyService} from "../shared/toy.service";
import {FormGroup} from "@angular/forms";

@Component({
  selector: 'app-toy-list',
  templateUrl: './toy-list.component.html',
  styleUrls: ['./toy-list.component.css']
})
export class ToyListComponent implements OnInit {
  errorMessage: string | undefined;
  toys: Toy[] | undefined;
  selectedToy: Toy | undefined;
  constructor(private toyService: ToyService, private router:Router) { this.toys = []}
  model = new Toy();
  ngOnInit(): void {
    this.toyService.getToys()
      .subscribe(
        toys => this.toys = toys
      );
  }

  onSelect(toy: Toy): void {
    this.selectedToy = toy;
  }

  orderBy(){
    this.toyService.orderBy().subscribe(
      toys => this.toys = toys,
      error => this.errorMessage = <any>error
    )
  }
  get diagnostic() { return JSON.stringify(this.model); }

  onSubmit(contactForm: { value: any; })
  {
    console.log(contactForm.value);
    this.model = contactForm.value;
    this.toyService.saveToys(this.model).subscribe(response => {
      this.getToys();
      let r: any = response;
      this.errorMessage = r.result;

    })
  }

  getToys() {
    this.toyService.getToys()
      .subscribe(
        toys => this.toys = toys
      );
  }
}

