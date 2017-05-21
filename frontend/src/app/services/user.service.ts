import { Injectable }     from '@angular/core';
import {Http, Response, URLSearchParams, Headers} from '@angular/http';
import { User }           from '../models/user';
import { Observable }     from 'rxjs/Observable';
import {user} from "../session";

@Injectable()
export class UserService {
  private baseUrl = 'http://46.101.106.208:3000';  // URL to web API
  public headers = new Headers({'Content-Type': 'application/x-www-form-urlencoded'});
  constructor (private http: Http) {
    if(user.token) {
      this.headers.append('Authorization', user.token);
    }
  }

  list(): Observable<User[]> {
    let params = new URLSearchParams();
    params.set('format', 'json');
    params.set('callback', 'JSONP_CALLBACK');

    return this.http.get(this.baseUrl+'/users', params)
      .map(UserService.extractData)
      .catch(UserService.handleError);
  }

  get(id: number): Observable<User> {
    let params = new URLSearchParams();
    params.set('format', 'json');
    params.set('callback', 'JSONP_CALLBACK');

    return this.http.get(this.baseUrl+'/users/'+id, params)
      .map(UserService.extractData)
      .catch(UserService.handleError);
  }


  countAvailable(): Observable<any> {
    return this.http.get(this.baseUrl+'/users/available', {headers: this.headers})
      .map(UserService.extractData)
      .catch(UserService.handleError);
  }

  create(model: User): any {
    const url = this.baseUrl+'/users';

    return this.http
      .post(url, JSON.stringify(model), {headers: this.headers})
      .toPromise()
      .catch(UserService.handleError);
  }

  remove(id: number): any {
    const url = this.baseUrl+'/users/'+id;

    return this.http
      .delete(url, {headers: this.headers})
      .toPromise()
      .catch(UserService.handleError);
  }

  update(model: User): any {
    const url = this.baseUrl+'/users/'+model.id;

    return this.http
      .put(url, JSON.stringify(model), {headers: this.headers})
      .toPromise()
      .then(() => model)
      .catch(UserService.handleError);
  }

  listByAction(actionId: number, params = []): Observable<User[]> {
    let search = new URLSearchParams();

    for (let param in params) {
      search.set(param, params[param]);
    }

    return this.http.get(this.baseUrl+'/actions/'+actionId+'/search', {headers: this.headers, search: search})
      .map(UserService.extractData)
      .catch(UserService.handleError);
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
