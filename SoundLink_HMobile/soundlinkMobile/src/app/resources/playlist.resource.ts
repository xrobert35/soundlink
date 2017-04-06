import { Http, Headers, RequestOptions, URLSearchParams } from '@angular/http';
import { AppConfig } from '../config/appConfig';
import { Injectable } from '@angular/core';
import { TokenStorage } from '../utils/tokenStorage'
import { AppStorage } from '../utils/appStorage'

import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/toPromise';

@Injectable()
export class PlaylistResources {

  constructor(private http: Http, private tokenStorage: TokenStorage, private appStorage : AppStorage) {
  }

  playlistUrl = AppConfig.SERVER_URL + "/soundlink/playlist/"

  getPlaylistsByUser(userId) {
    return this.tokenStorage.retrieve().then(token => {
      let params = new URLSearchParams();
      params.set('userId', userId);

      let headers = new Headers();
      headers.append('X-AUTH-TOKEN', token);
      let options = new RequestOptions({
        headers: headers,
        search: params
      });
      return this.http.get(this.playlistUrl + 'playlistsByUser', options).map(response => response.json()).toPromise();
    });
  };

  getCurrentUserPlaylists(){
    var userId = this.appStorage.getUser().id;
    return this.getPlaylistsByUser(userId);
  }

  addMusicToPlaylist(playlistId, userId) {
    return this.tokenStorage.retrieve().then(token => {
      let headers = new Headers();
      headers.append('X-AUTH-TOKEN', token);
      let options = new RequestOptions({
        headers: headers,
      });
      return this.http.post(this.playlistUrl + 'addMusic', { playlistId: playlistId, userId: userId }, options).map(response => response.json()).toPromise();
    });
  }

  removeMusicFromPlaylist(playlistId, userId) {
    return this.tokenStorage.retrieve().then(token => {
      let headers = new Headers();
      headers.append('X-AUTH-TOKEN', token);
      let options = new RequestOptions({
        headers: headers,
      });
      return this.http.post(this.playlistUrl + 'removeMusic', { playlistId: playlistId, userId: userId }, options).map(response => response.json()).toPromise();
    });
  }

  create(playlist) {
    return this.tokenStorage.retrieve().then(token => {
      let headers = new Headers();
      headers.append('X-AUTH-TOKEN', token);
      let options = new RequestOptions({
        headers: headers,
      });
      return this.http.post(this.playlistUrl + 'create', playlist, options).map(response => response.json()).toPromise();
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
}