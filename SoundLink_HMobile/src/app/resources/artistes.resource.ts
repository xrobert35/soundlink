import { Http, Headers, RequestOptions, URLSearchParams } from '@angular/http';
import { AppConfig } from '../config/appConfig';
import { Injectable } from '@angular/core';
import { TokenStorage } from '../utils/tokenStorage'
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/toPromise';

@Injectable()
export class ArtistesResource {

  constructor(private http: Http, private tokenStorage: TokenStorage) {
  }

  artistesUrl = AppConfig.SERVER_URL + "soundlink/artistes/";

  getUserArtistes() {
    return this.tokenStorage.retrieve().then(token => {
      let headers = new Headers();
      headers.append('X-AUTH-TOKEN', token);
      let options = new RequestOptions({
        headers: headers,
      });
      return this.http.get(this.artistesUrl + "userArtistes", options).map(response => response.json()).toPromise();
    });
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
      return this.http.get(this.artistesUrl + "artistesStartWith", options).map(response => response.json()).toPromise();
    });
  }

  getArtisteCoverUrl(artisteId) {
    return AppConfig.SERVER_BASE_URL + "covers/artistes/" + artisteId;
  }
}