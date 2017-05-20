import { Injectable }     from '@angular/core';
import {Http, Response, URLSearchParams, Headers} from '@angular/http';
import { Point }           from '../models/point';
import { Observable }     from 'rxjs/Observable';
import {user} from "../session";

@Injectable()
export class PointService {
  private baseUrl = 'http://46.101.106.208:3000';  // URL to web API
  public headers = new Headers({'Content-Type': 'application/x-www-form-urlencoded'});
  constructor (private http: Http) {
    if(user.token) {
      this.headers.append('Authorization', user.token);
    }
  }

  list(): Observable<Point[]> {
    let params = new URLSearchParams();
    params.set('format', 'json');
    params.set('callback', 'JSONP_CALLBACK');

    return this.http.get(this.baseUrl+'/points', params)
      .map(PointService.extractData)
      .catch(PointService.handleError);
  }

  get(id: number): Observable<Point> {
    let params = new URLSearchParams();
    params.set('format', 'json');
    params.set('callback', 'JSONP_CALLBACK');

    return this.http.get(this.baseUrl+'/points/'+id, params)
      .map(PointService.extractData)
      .catch(PointService.handleError);
  }

  create(model: Point): any {
    const url = this.baseUrl+'/points';

    return this.http
      .post(url, JSON.stringify(model), {headers: this.headers})
      .toPromise()
      .catch(PointService.handleError);
  }

  remove(id: number): any {
    const url = this.baseUrl+'/points/'+id;

    return this.http
      .delete(url, {headers: this.headers})
      .toPromise()
      .catch(PointService.handleError);
  }

  update(model: Point): any {
    const url = this.baseUrl+'/points/'+model.id;

    return this.http
      .put(url, JSON.stringify(model), {headers: this.headers})
      .toPromise()
      .then(() => model)
      .catch(PointService.handleError);
  }

  listByAction(actionId: number): Observable<Point[]> {
    return this.http.get(this.baseUrl+'/actions/' + actionId + '/points', {headers: this.headers})
      .map(PointService.extractData)
      .catch(PointService.handleError);
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
