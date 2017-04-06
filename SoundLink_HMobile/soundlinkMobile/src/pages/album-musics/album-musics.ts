import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import { SoundlinkResources } from '../../app/resources/soundlink.resources';

@Component({
  selector: 'page-album-musics',
  templateUrl: 'album-musics.html'
})
export class AlbumMusicsPage {

  album : any;
  musics = [];

  constructor(public navCtrl: NavController, public navParams: NavParams, private soundlinkResources: SoundlinkResources) {
    this.album = navParams.get("album");
  }

  onMusicSelected(music){

  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad AlbumMusicsPage');

     this.soundlinkResources.getMusicsFromAlbum(this.album['id']).then(musics => {
      this.musics = musics;
    });
  }

}
