import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import { MusicsResource } from '../../app/resources/musics.resource';
import { AlbumsResource } from '../../app/resources/albums.resource';

@Component({
  selector: 'page-album-musics',
  templateUrl: 'album-musics.html'
})
export class AlbumMusicsPage {

  album: any;
  musics = [];

  constructor(public navCtrl: NavController, public navParams: NavParams, private musicsResource: MusicsResource, private albumsResource : AlbumsResource) {
    this.album = navParams.get("album");
  }

  getAlbumCoverUrl() {
    return this.albumsResource.getAlbumCoverUrl(this.album.id);
  }

  onMusicSelected(music) {
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad AlbumMusicsPage');

    this.musicsResource.getMusicsFromAlbum(this.album['id']).then(musics => {
      this.musics = musics;
    });
  }

}
