import { Component, OnInit } from '@angular/core';
import {Person} from "../../people/shared/person.model";
import {Pet} from "../shared/pet.model";
import {PetService} from "../shared/pet.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-pet-list',
  templateUrl: './pet-list.component.html',
  styleUrls: ['./pet-list.component.css']
})
export class PetListComponent implements OnInit {
  pets: Array<Pet> | undefined;
  errorMessage: string | undefined;
  selectedPet: Pet | undefined;
  constructor(private petService: PetService,private router: Router) { }

  ngOnInit(): void {
    this.getPets();
  }


  getPets() {
    this.petService.getPets()
      .subscribe(
        pets => this.pets = pets,
        error => this.errorMessage = <any>error
      );
  }

  onSelect(pet: Pet): void {
    this.selectedPet = pet;
  }
}
