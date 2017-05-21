import { Injectable }     from '@angular/core';
import {Http, Response, Headers, URLSearchParams} from '@angular/http';
import { Polygon }           from '../models/polygon';
import { Observable }     from 'rxjs/Observable';
import {user} from "../session";

@Injectable()
export class PolygonService {
  private baseUrl = 'http://46.101.106.208:3000';  // URL to web API
  public headers = new Headers({'Content-Type': 'application/x-www-form-urlencoded'});
  constructor (private http: Http) {
    if(user.token) {
      this.headers.append('Authorization', user.token);
    }
  }

  list(params = []): Observable<Polygon[]> {
    let search = new URLSearchParams();

    for (let param in params) {
      search.set(param, params[param]);
    }

    return this.http.get(this.baseUrl+'/polygons', {headers: this.headers, search: search})
      .map(PolygonService.extractData)
      .catch(PolygonService.handleError);
  }

  get(id: number): Observable<Polygon> {
    return this.http.get(this.baseUrl+'/polygons/'+id, {headers: this.headers})
      .map(PolygonService.extractData)
      .catch(PolygonService.handleError);
  }

  create(model: Polygon): any {
    const url = this.baseUrl+'/polygons';

    return this.http
      .post(url, JSON.stringify(model), {headers: this.headers})
      .toPromise()
      .catch(PolygonService.handleError);
  }

  remove(id: number): any {
    const url = this.baseUrl+'/polygons/'+id;

    return this.http
      .delete(url, {headers: this.headers})
      .toPromise()
      .catch(PolygonService.handleError);
  }

  update(model: Polygon): any {
    const url = this.baseUrl+'/polygons/'+model.id;

    return this.http
      .put(url, JSON.stringify(model), {headers: this.headers})
      .toPromise()
      .then(() => model)
      .catch(PolygonService.handleError);
  }

  listByAction(actionId: number): Observable<Polygon[]> {
    return this.http.get(this.baseUrl+'/actions/'+actionId+'/polygons', {headers: this.headers})
      .map(PolygonService.extractData)
      .catch(PolygonService.handleError);
  }

  private static extractData(res: Response) {
    let body = res.json();
    return body || { };
  }

  private static handleError (error: Response | any) {
    let errMsg: string;
    if (error instanceof Response) {
      const body = error.json() || '';
      const err = body.error || JSON.stringify(body);
      errMsg = `${error.status} - ${error.statusText || ''} ${err}`;
    } else {
      errMsg = error.message ? error.message : error.toString();
    }
    console.error(errMsg);
    return Observable.throw(errMsg);
  }
}
