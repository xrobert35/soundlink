import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import { AlbumsResource } from '../../app/resources/albums.resource';
import { AlbumMusicsPage } from "../album-musics/album-musics";

@Component({
  selector: 'page-mymusics.page-myalbums',
  templateUrl: 'myalbums.html'
})
export class MyAlbumsPage {

  albums = [];

  constructor(public navCtrl: NavController, public navParams: NavParams, private albumsResource: AlbumsResource) { }

  onAlbumSelected(album) {
    this.navCtrl.push(AlbumMusicsPage, { album: album });
  }

  getAlbumCoverUrl(album) {
    return this.albumsResource.getAlbumCoverUrl(album.id);
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad My Albums Page');
    this.albumsResource.getUserAlbums().then(albums => {
      this.albums = albums;
    });
  }
}