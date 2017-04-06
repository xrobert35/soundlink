import { Http, Headers, RequestOptions, URLSearchParams } from '@angular/http';
import { AppConfig } from '../config/appConfig';
import { Injectable } from '@angular/core';
import { TokenStorage } from '../utils/tokenStorage'
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/toPromise';

@Injectable()
export class SoundlinkResources {

  constructor(private http: Http, private tokenStorage: TokenStorage) {
  }

  login(credentials) {
    return this.http.post(AppConfig.SERVER_URL + 'security/login', credentials).toPromise();
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

      return this.http.get(AppConfig.SERVER_URL + "soundlink/albumsArtiste", options).map(response => response.json()).toPromise();
    });
  }

  getArtistes() {
    return this.http.get(AppConfig.SERVER_URL + 'soundlink/artistes').map(response => response.json());
  }

  getArtistesStartWith(startChain) {
    return this.tokenStorage.retrieve().then(token => {
      let params = new URLSearchParams();
      params.set('startChain', startChain);

      let headers = new Headers();
      headers.append('X-AUTH-TOKEN', token);
      let options = new RequestOptions({
        headers: headers,
        search: params
      });
      return this.http.get(AppConfig.SERVER_URL + 'soundlink/artistesStartWith', options).map(response => response.json()).toPromise();
    });
  }

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
      return this.http.get(AppConfig.SERVER_URL + 'soundlink/albumMusics', options).map(response => response.json()).toPromise();
    });
  }

  getUserInformation(){
    return this.tokenStorage.retrieve().then(token => {
      let headers = new Headers();
      headers.append('X-AUTH-TOKEN', token);
      let options = new RequestOptions({
        headers: headers
      });
      return this.http.get(AppConfig.SERVER_URL + 'soundlink/userInformation', options).map(response => response.json()).toPromise();
    });
  }
}