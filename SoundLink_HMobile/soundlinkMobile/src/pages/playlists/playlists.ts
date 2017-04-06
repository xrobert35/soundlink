import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import { PlaylistResources } from '../../app/resources/playlist.resource';

import { PlaylistCreatePage } from "./create/playlist.create"

@Component({
  selector: 'page-playlists',
  templateUrl: 'playlists.html'
})
export class PlaylistsPage {

  playlists = [];

  constructor(public navCtrl: NavController, public navParams: NavParams, private playlistResources: PlaylistResources) { }

  ionViewDidLoad() {
    this.playlistResources.getCurrentUserPlaylists().then(playlists => {
      this.playlists = playlists;
    });
  }

  goToPlaylistCreation() {
    this.navCtrl.push(PlaylistCreatePage);
  }

}
