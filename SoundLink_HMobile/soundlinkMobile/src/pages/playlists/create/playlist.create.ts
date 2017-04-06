import { Component, Input } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import { PlaylistResources } from '../../../app/resources/playlist.resource';
import { FileManager } from '../../../app/utils/fileManager'

@Component({
  selector: 'page-playlists.create',
  templateUrl: 'playlist.create.html'
})
export class PlaylistCreatePage {

  @Input() playlist = {
    name: "",
    cover: ""
  };

  constructor(public navCtrl: NavController, public navParams: NavParams, private playlistResources: PlaylistResources, private fileManager: FileManager) { }

  ionViewDidLoad() {
  }

  chooseCover() {
    this.fileManager.chooseFile().then(file => {

    });
  }

  createPlaylist() {
    this.playlistResources.create(this.playlist).then(result => {

    });
  }

}