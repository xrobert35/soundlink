import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';

import { HomePage } from '../home/home';
import { MyArtistesPage } from '../myartistes/myartistes';
import { MyAlbumsPage } from '../myalbums/myalbums';
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
  tabMyArtistes: any = MyArtistesPage;
  tabMyAlbums: any = MyAlbumsPage;

  constructor(public navCtrl: NavController, public navParams: NavParams) {}

  ionViewDidLoad() {
    console.log('ionViewDidLoad TabsPage');
  }

}
