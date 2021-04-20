import { Component, OnInit } from '@angular/core';
import {Person} from "../shared/person.model";
import {PersonService} from "../shared/person.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-person-list',
  templateUrl: './person-list.component.html',
  styleUrls: ['./person-list.component.css']
})
export class PersonListComponent implements OnInit {
  errorMessage: string | undefined;
  people: Person[];
  selectedPerson: Person | undefined;
  constructor(private personService: PersonService, private router:Router) {
    this.people = [];
  }

  ngOnInit(): void {
    this.personService.getPeople()
      .subscribe(
        people => this.people = people
      );
  }

  onSelect(person: Person): void {
    this.selectedPerson = person;
  }


}
