import {Injectable} from '@angular/core';

import {HttpClient} from "@angular/common/http";


import {Observable} from "rxjs";
import {map} from "rxjs/operators";
import {Pet} from "./pet.model";


@Injectable()
export class PetService {
  private petsUrl = 'http://localhost:8080/api/pets';

  constructor(private httpClient: HttpClient) {
  }

  getPets(): Observable<Pet[]> {
    return this.httpClient
      .get<Array<Pet>>(this.petsUrl);
  }

  savePets(pet: Pet): Observable<Pet> {
    return this.httpClient
      .post<Pet>(this.petsUrl, pet);
  }
}
