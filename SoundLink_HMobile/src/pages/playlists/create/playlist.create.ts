import { Component, Input } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import { PlaylistsResource } from '../../../app/resources/playlists.resource';
import { FileManager } from '../../../app/utils/fileManager';
import { Diagnostic } from '@ionic-native/diagnostic';

@Component({
  selector: 'page-playlists.create',
  templateUrl: 'playlist.create.html'
})
export class PlaylistCreatePage {

  @Input() playlist = {
    name: "",
    cover: "",
    coverDisplay : ""
  };

  constructor(public navCtrl: NavController, public navParams: NavParams, private playlistsResource: PlaylistsResource,
    private fileManager: FileManager, private diagnostic: Diagnostic) { }

  ionViewDidLoad() {
  }

  chooseCover() {
    this.diagnostic.requestRuntimePermissions(this.diagnostic.permissionGroups.STORAGE).then((status) => {
      this.fileManager.chooseFile().then((filePath) => {
        alert("File " + filePath);
        this.fileManager.toBase64(filePath).then((base64File) =>{
          this.playlist.cover = filePath;
          this.playlist.coverDisplay = filePath;
        });
      });
    });
  }

  createPlaylist() {
    this.playlistsResource.create(this.playlist).then(result => {
    });
  }

}