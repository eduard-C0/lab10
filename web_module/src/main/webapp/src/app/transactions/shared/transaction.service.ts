import {Injectable} from '@angular/core';

import {HttpClient} from "@angular/common/http";


import {Observable} from "rxjs";
import {map} from "rxjs/operators";
import {Transaction} from "./transaction.model";


@Injectable()
export class TransactionService {
  private transactionsUrl = 'http://localhost:8080/api/transactions';

  constructor(private httpClient: HttpClient) {
  }

  getTransactions(): Observable<Transaction[]> {
    return this.httpClient
      .get<Array<Transaction>>(this.transactionsUrl);
  }

  saveTransactions(trasaction: Transaction): Observable<Transaction> {
    return this.httpClient
      .post<Transaction>(this.transactionsUrl, trasaction);
  }
}
