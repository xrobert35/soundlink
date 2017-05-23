import { Http, Headers, RequestOptions } from '@angular/http';
import { AppConfig } from '../config/appConfig';
import { Injectable } from '@angular/core';
import { TokenStorage } from '../utils/tokenStorage'
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/toPromise';

@Injectable()
export class TechResource {

  constructor(private http: Http, private tokenStorage: TokenStorage) {
  }

 getWsToken() {
    return this.tokenStorage.retrieve().then(token => {
      let headers = new Headers();
      headers.append('X-AUTH-TOKEN', token);
      let options = new RequestOptions({
        headers: headers
      });
      return this.http.get(AppConfig.SERVER_URL + 'soundlink/tech/wsToken', options).map(response => response.text()).toPromise();
    });
  };
}