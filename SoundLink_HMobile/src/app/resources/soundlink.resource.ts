import { Http, Headers, RequestOptions } from '@angular/http';
import { AppConfig } from '../config/appConfig';
import { Injectable } from '@angular/core';
import { TokenStorage } from '../utils/tokenStorage'
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/toPromise';

@Injectable()
export class SoundlinkResource {

  constructor(private http: Http, private tokenStorage: TokenStorage) {
  }

  login(credentials) {
    return this.http.post(AppConfig.SERVER_URL + 'security/login', credentials).toPromise();
  }

  getUserInformation(){
    return this.tokenStorage.retrieve().then(token => {
      let headers = new Headers();
      headers.append('X-AUTH-TOKEN', token);
      let options = new RequestOptions({
        headers: headers
      });
      return this.http.get(AppConfig.SERVER_URL + 'soundlink/users/userInformation', options).map(response => response.json()).toPromise();
    });
  }
}