import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import { AlbumsResource } from '../../app/resources/albums.resource';
import { AlbumMusicsPage } from "../album-musics/album-musics";

@Component({
  selector: 'page-albums',
  templateUrl: 'albums.html'
})
export class AlbumsPage {

  artiste: any;
  albums = [];

  constructor(public navCtrl: NavController, public navParams: NavParams, private albumsResource: AlbumsResource) {
    this.artiste = navParams.get("artiste");
  }

  onAlbumSelected(album) {
    this.navCtrl.push(AlbumMusicsPage, { album: album });
  };

  getAlbumCoverUrl(album) {
    return this.albumsResource.getAlbumCoverUrl(album.id);
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad AlbumsPage');

    this.albumsResource.getAlbumsFromArtiste(this.artiste['id']).then(albums => {
      this.albums = albums;
    });
  }
}
