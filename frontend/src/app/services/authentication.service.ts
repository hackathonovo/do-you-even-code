import { Injectable }     from '@angular/core';
import {Http, Response, Headers} from '@angular/http';
import { Observable }     from 'rxjs/Observable';
import {user} from "../session";
import {Authentication} from "../models/authentication";

@Injectable()
export class AuthenticationService {
  private baseUrl = 'http://46.101.106.208:3000';  // URL to web API
  private headers = new Headers({'Content-Type': 'application/x-www-form-urlencoded'});
  constructor (private http: Http) {

  }

  public handshake(): Observable<Authentication> {
    const url = this.baseUrl+'/login';

    return this.http
      .post(url, JSON.stringify({"username": "voditelj", "password": "1234"}), {headers: this.headers})
      .map(AuthenticationService.extractData)
      .catch(AuthenticationService.handleError);
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
