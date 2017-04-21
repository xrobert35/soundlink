import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import { PlaylistsResource } from '../../app/resources/playlists.resource';

import { PlaylistCreatePage } from "./create/playlist.create"

@Component({
  selector: 'page-playlists',
  templateUrl: 'playlists.html'
})
export class PlaylistsPage {

  playlists = [];

  constructor(public navCtrl: NavController, public navParams: NavParams, private playlistsResource: PlaylistsResource) { }

  ionViewDidLoad() {
    this.playlistsResource.getUserPlaylists().then(playlists => {
      this.playlists = playlists;
    });
  }

  goToPlaylistCreation() {
    this.navCtrl.push(PlaylistCreatePage);
  }

}
