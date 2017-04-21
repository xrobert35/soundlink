import { Http, Headers, RequestOptions, URLSearchParams } from '@angular/http';
import { AppConfig } from '../config/appConfig';
import { Injectable } from '@angular/core';
import { TokenStorage } from '../utils/tokenStorage'

import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/toPromise';

@Injectable()
export class AlbumsResource {

  constructor(private http: Http, private tokenStorage: TokenStorage) {
  }

  albumsUrl = AppConfig.SERVER_URL + "soundlink/albums/";

  getUserAlbums() {
    return this.tokenStorage.retrieve().then(token => {
      let headers = new Headers();
      headers.append('X-AUTH-TOKEN', token);
      let options = new RequestOptions({
        headers: headers,
      });
      return this.http.get(this.albumsUrl + "userAlbums", options).map(response => response.json()).toPromise();
    });
  }

  getAlbumsFromArtiste(artisteId) {
    return this.tokenStorage.retrieve().then(token => {
      let params = new URLSearchParams();
      params.set('artisteId', artisteId);

      let headers = new Headers();
      headers.append('X-AUTH-TOKEN', token);
      let options = new RequestOptions({
        headers: headers,
        search: params
      });

      return this.http.get(this.albumsUrl + "fromArtiste", options).map(response => response.json()).toPromise();
    });
  }

  getAlbumCoverUrl(albumId){
    return AppConfig.SERVER_BASE_URL + "covers/albums/" + albumId;
  }
}