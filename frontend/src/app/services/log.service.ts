import { Injectable }     from '@angular/core';
import {Http, Response, URLSearchParams, Headers} from '@angular/http';
import { Log }           from '../models/log';
import { Observable }     from 'rxjs/Observable';
import {user} from "../session";

@Injectable()
export class LogService {
  private baseUrl = 'http://46.101.106.208:3000';  // URL to web API
  public headers = new Headers({'Content-Type': 'application/x-www-form-urlencoded'});
  constructor (private http: Http) {
    if(user.token) {
      this.headers.append('Authorization', user.token);
    }
  }

  list(): Observable<Log[]> {
    let params = new URLSearchParams();
    params.set('format', 'json');
    params.set('callback', 'JSONP_CALLBACK');

    return this.http.get(this.baseUrl+'/logger', params)
      .map(LogService.extractData)
      .catch(LogService.handleError);
  }

  get(id: number): Observable<Log> {
    let params = new URLSearchParams();
    params.set('format', 'json');
    params.set('callback', 'JSONP_CALLBACK');

    return this.http.get(this.baseUrl+'/logger/'+id, params)
      .map(LogService.extractData)
      .catch(LogService.handleError);
  }

  helpMe(): Observable<any> {
    return this.http.get(this.baseUrl+'/helpme', {headers: this.headers})
      .map(LogService.extractData)
      .catch(LogService.handleError);
  }

  create(model: Log): any {
    const url = this.baseUrl+'/logger';

    return this.http
      .post(url, JSON.stringify(model), {headers: this.headers})
      .toPromise()
      .catch(LogService.handleError);
  }

  remove(id: number): any {
    const url = this.baseUrl+'/logger/'+id;

    return this.http
      .delete(url, {headers: this.headers})
      .toPromise()
      .catch(LogService.handleError);
  }

  update(model: Log): any {
    const url = this.baseUrl+'/logger/'+model.id;

    return this.http
      .put(url, JSON.stringify(model), {headers: this.headers})
      .toPromise()
      .then(() => model)
      .catch(LogService.handleError);
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
