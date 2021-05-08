import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Toy} from './toy.model';


@Injectable()
export class ToyService {
  private toyUrl = 'http://localhost:8080/api/toys';

  constructor(private httpClient: HttpClient) {
  }

  getToys(): Observable<Toy[]> {
    return this.httpClient
      .get<Array<Toy>>(this.toyUrl);
  }

  saveToys(toy: Toy): Observable<Toy> {
    return this.httpClient
      .post<Toy>(this.toyUrl, toy);
  }

  orderBy(): Observable<Toy[]>{
    const url = `${this.toyUrl}/order-by-price/`;
    return this.httpClient.get<Array<Toy>>(url);
  }

}
