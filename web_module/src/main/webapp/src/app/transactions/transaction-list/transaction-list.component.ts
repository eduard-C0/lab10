import { Component, OnInit } from '@angular/core';
import {Person} from "../../people/shared/person.model";
import {PersonService} from "../../people/shared/person.service";
import {Router} from "@angular/router";
import {Transaction} from "../shared/transaction.model";
import {TransactionService} from "../shared/transaction.service";

@Component({
  selector: 'app-transaction-list',
  templateUrl: './transaction-list.component.html',
  styleUrls: ['./transaction-list.component.css']
})
export class TransactionListComponent implements OnInit {
  transactions: Array<Transaction> | undefined;
  errorMessage: string | undefined;
  selectedTransaction: Transaction | undefined;
  constructor(private  transactionService : TransactionService, private router: Router) {
  }

  ngOnInit(): void {
    this.getTransactions();
  }

  getTransactions() {
    this.transactionService.getTransactions()
      .subscribe(
        transactions => this.transactions = transactions,
        error => this.errorMessage = <any>error
      );
  }
  onSelect(transaction: Transaction): void {
    this.selectedTransaction = transaction;
  }


}
