import { Component, OnInit } from '@angular/core';
import {PersonService} from "../shared/person.service";
import {Person} from "../shared/person.model";

@Component({
  selector: 'app-person-new',
  templateUrl: './person-new.component.html',
  styleUrls: ['./person-new.component.css']
})
export class PersonNewComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {

  }



}
