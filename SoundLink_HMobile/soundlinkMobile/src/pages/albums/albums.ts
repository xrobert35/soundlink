import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import { SoundlinkResources } from '../../app/resources/soundlink.resources';
import { AlbumMusicsPage } from "../album-musics/album-musics"

@Component({
  selector: 'page-albums',
  templateUrl: 'albums.html'
})
export class AlbumsPage {

  artiste: any;
  albums = [];

  constructor(public navCtrl: NavController, public navParams: NavParams, private soundlinkResources: SoundlinkResources) {
    this.artiste = navParams.get("artiste");
  }

  onAlbumSelected(album) {
    this.navCtrl.push(AlbumMusicsPage, { album: album });
  };

  ionViewDidLoad() {
    console.log('ionViewDidLoad AlbumsPage');

    this.soundlinkResources.getAlbumsFromArtiste(this.artiste['id']).then(albums => {
      this.albums = albums;
    });
  }
}
