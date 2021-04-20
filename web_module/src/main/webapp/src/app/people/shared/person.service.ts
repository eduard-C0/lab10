import {Injectable} from '@angular/core';

import {HttpClient} from "@angular/common/http";


import {Observable} from "rxjs";
import {map} from "rxjs/operators";
import {Person} from "./person.model";


@Injectable()
export class PersonService {
  private peopleUrl = 'http://localhost:8080/api/people';

  constructor(private httpClient: HttpClient) {
  }

  getPeople(): Observable<Person[]> {
    return this.httpClient
      .get<Array<Person>>(this.peopleUrl);
  }

  savePeople(person: Person): Observable<Person> {
    return this.httpClient
      .post<Person>(this.peopleUrl, person);
  }
}
