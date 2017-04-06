import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';

import { HomePage } from '../home/home';
import { ArtistesPage } from '../artistes/artistes';
import { AlbumsPage } from '../albums/albums';
import { PlaylistsPage } from '../playlists/playlists';
import { SearchPage } from '../search/search';

@Component({
  selector: 'page-tabs',
  templateUrl: 'tabs.html'
})
export class TabsPage {

  tabHome : any = HomePage;
  tabSearch: any = SearchPage;
  tabPlaylists: any = PlaylistsPage;
  tabArtistes: any = ArtistesPage;
  tabAlbums: any = AlbumsPage;

  constructor(public navCtrl: NavController, public navParams: NavParams) {}

  ionViewDidLoad() {
    console.log('ionViewDidLoad TabsPage');
  }

}
