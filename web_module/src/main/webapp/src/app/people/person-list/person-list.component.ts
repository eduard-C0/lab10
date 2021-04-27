import {Component, OnInit, ViewChild} from '@angular/core';
import {Person} from "../shared/person.model";
import {PersonService} from "../shared/person.service";
import {Router} from "@angular/router";
import {AbstractControl, FormControl, FormGroup, NgForm, NgModel, Validators} from "@angular/forms";

@Component({
  selector: 'app-person-list',
  templateUrl: './person-list.component.html',
  styleUrls: ['./person-list.component.css']
})
export class PersonListComponent implements OnInit {
  errorMessage: string | undefined;
  people: Person[];
  selectedPerson: Person ;
  isShownAdd = true;
  isShownUpdate = true;
  isShownDelete = true;
  addPersonForm = new FormGroup({
    personName : new FormControl('', [Validators.required, Validators.minLength(3)]),
    budget : new FormControl(0, [Validators.required, Validators.min(0), Validators.pattern('^[0-9]+$')]),
  });
  updatePersonForm = new FormGroup({
    personId : new FormControl(0, [Validators.required, Validators.pattern('^[0-9]+$')]),
    personName : new FormControl('', [Validators.required, Validators.minLength(3)]),
    budget : new FormControl(0, [Validators.required, Validators.min(0), Validators.pattern('^[0-9]+$')]),
  });

  deletePersonForm = new FormGroup({
    personId : new FormControl(0, [Validators.required, Validators.pattern('^[0-9]+$')]),
  });

  constructor(private personService: PersonService, private router:Router) {
    this.people = [];
    this.selectedPerson = new Person;
  }

  ngOnInit(): void {
    this.getPeople()
  }

  toggleDisplayAdd() {
    this.isShownAdd = !this.isShownAdd;
  }

  toggleDisplayUpdate()
  {
    this.isShownUpdate = !this.isShownUpdate;
  }

  toggleDisplayDelete()
  {
    this.isShownDelete = !this.isShownDelete;
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
    this.errorMessage = "";
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

  deletePersonF()
  {
    this.errorMessage = "";

    this.personService.deletePeople(+this.deletePersonForm.get('personId')?.value).subscribe(response => {
      this.getPeople();
      let r: any = response;
      this.errorMessage = r.result;

    })
  }

  addPersonF()
  {
    this.errorMessage = "";
    const newPerson: Person = <Person>{name: this.addPersonForm.get('personName')?.value, budget: +this.addPersonForm.get('budget')?.value};
    this.personService.savePeople(newPerson).subscribe(response => {
      this.getPeople();
      let r: any = response;
      this.errorMessage = r.result;

    })
  }

  updatePersonF()
  {
    this.errorMessage = "";
    const newPerson: Person = <Person>{id:this.updatePersonForm.get('personId')?.value, name:this.updatePersonForm.get('personName')?.value, budget: +this.updatePersonForm.get('budget')?.value};
    this.personService.updatePeople(newPerson).subscribe(response => {
      this.getPeople();
      let r: any = response;
      this.errorMessage = r.result;

    })
  }

  get DpersonId():AbstractControl { return this.deletePersonForm.get('personId')?.value; }

  get UpersonId():AbstractControl { return this.updatePersonForm.get('personId')?.value; }

  get UpersonName(): AbstractControl { return this.updatePersonForm.get('personName')?.value; }

  get Ubudget(): AbstractControl { return this.updatePersonForm.get('budget')?.value; }

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

  get personName(): AbstractControl { return this.addPersonForm.get('personName')?.value; }

  get budget(): AbstractControl { return this.addPersonForm.get('budget')?.value; }

}

