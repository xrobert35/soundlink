import { Http, Headers, RequestOptions, URLSearchParams } from '@angular/http';
import { AppConfig } from '../config/appConfig';
import { Injectable } from '@angular/core';
import { TokenStorage } from '../utils/tokenStorage'

import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/toPromise';

@Injectable()
export class MusicsResource {
  
  constructor(private http: Http, private tokenStorage: TokenStorage) {
  }

  musicsUrl = AppConfig.SERVER_URL + "/soundlink/musics/";

  getMusicsFromAlbum(albumId) {
    return this.tokenStorage.retrieve().then(token => {
      let params = new URLSearchParams();
      params.set('albumId', albumId);

      let headers = new Headers();
      headers.append('X-AUTH-TOKEN', token);
      let options = new RequestOptions({
        headers: headers,
        search: params
      });
      return this.http.get(this.musicsUrl + "fromAlbum", options).map(response => response.json()).toPromise();
    });
  }
}