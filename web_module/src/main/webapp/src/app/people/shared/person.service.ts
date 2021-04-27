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

  deletePeople(id:number) : Observable<Person> {
    const url = `${this.peopleUrl}/${id}`;
    console.log(id);
    return this.httpClient.delete<Person>(url);

  }
  updatePeople(person:Person) : Observable<Person> {
    const url = `${this.peopleUrl}/${person.id}`;
    return this.httpClient
      .put<Person>(url, person);
  }

}
