import { Injectable }     from '@angular/core';
import {Http, Response, URLSearchParams, Headers} from '@angular/http';
import { Action }           from '../models/action';
import { Observable }     from 'rxjs/Observable';
import {user} from "../session";

@Injectable()
export class ActionService {
  private baseUrl = 'http://46.101.106.208:3000';  // URL to web API
  public headers = new Headers({'Content-Type': 'application/x-www-form-urlencoded'});
  constructor (private http: Http) {
    if(user.token) {
      this.headers.append('Authorization', user.token);
    }
  }

  list(): Observable<Action[]> {
    let params = new URLSearchParams();
    params.set('format', 'json');
    params.set('callback', 'JSONP_CALLBACK');

    return this.http.get(this.baseUrl+'/actions', params)
      .map(ActionService.extractData)
      .catch(ActionService.handleError);
  }

  get(id: number): Observable<Action> {
    let params = new URLSearchParams();
    params.set('format', 'json');
    params.set('callback', 'JSONP_CALLBACK');

    return this.http.get(this.baseUrl+'/actions/'+id, params)
      .map(ActionService.extractData)
      .catch(ActionService.handleError);
  }

  create(model: Action): any {
    const url = this.baseUrl+'/actions';

    return this.http
      .post(url, JSON.stringify(model), {headers: this.headers})
      .toPromise()
      .catch(ActionService.handleError);
  }

  remove(id: number): any {
    const url = this.baseUrl+'/actions/'+id;

    return this.http
      .delete(url, {headers: this.headers})
      .toPromise()
      .catch(ActionService.handleError);
  }

  update(model: Action): any {
    const url = this.baseUrl+'/actions/'+model.id;

    return this.http
      .put(url, JSON.stringify(model), {headers: this.headers})
      .toPromise()
      .then(() => model)
      .catch(ActionService.handleError);
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
