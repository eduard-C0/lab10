import {Component, OnInit, ViewChild} from '@angular/core';
import {Person} from "../shared/person.model";
import {PersonService} from "../shared/person.service";
import {Router} from "@angular/router";
import {NgForm, NgModel} from "@angular/forms";

@Component({
  selector: 'app-person-list',
  templateUrl: './person-list.component.html',
  styleUrls: ['./person-list.component.css']
})
export class PersonListComponent implements OnInit {
  errorMessage: string | undefined;
  people: Person[];
  selectedPerson: Person ;

  constructor(private personService: PersonService, private router:Router) {
    this.people = [];
    this.selectedPerson = new Person;
  }

  ngOnInit(): void {
    this.getPeople()
  }

  getPeople() {
    this.personService.getPeople()
      .subscribe(
        people => this.people = people
      );
  }

  onSelect(person: Person): void {
    this.selectedPerson = person;
  }

  addPerson(name:String, budget:string)
  {
    this.errorMessage = "";;
    const newPerson: Person = <Person>{name, budget: +budget};
    this.personService.savePeople(newPerson).subscribe(response => {
      this.getPeople();
      let r: any = response;
      this.errorMessage = r.result;

    })

  }

  deletePerson(id:string)
  {
    this.errorMessage = "";

    this.personService.deletePeople(+id).subscribe(response => {
      this.getPeople();
      let r: any = response;
      this.errorMessage = r.result;

    })

  }

  updatePerson(id:string,name:string, budget:string)
  {
    this.errorMessage = "";
    const newPerson: Person = <Person>{id:+id, name, budget: +budget};
    this.personService.updatePeople(newPerson).subscribe(response => {
      this.getPeople();
      let r: any = response;
      this.errorMessage = r.result;

    })

  }

}

