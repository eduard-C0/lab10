import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Food} from "./food.model";


@Injectable()
export class FoodService {
  private foodUrl = 'http://localhost:8080/api/foods';

  constructor(private httpClient: HttpClient) {
  }

  getFoods(): Observable<Food[]> {
    return this.httpClient
      .get<Array<Food>>(this.foodUrl);
  }

  saveFoods(food: Food): Observable<Food> {
    return this.httpClient
      .post<Food>(this.foodUrl, food);
  }
}
