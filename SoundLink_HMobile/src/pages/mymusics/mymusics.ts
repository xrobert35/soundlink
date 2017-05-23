import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';

import { MyArtistesPage } from '../myartistes/myartistes';
import { MyAlbumsPage } from '../myalbums/myalbums';
import { PlaylistsPage } from '../playlists/playlists';

@Component({
  selector: 'page-mymusics',
  templateUrl: 'mymusics.html'
})
export class MyMusicsPage {

  pages;

  constructor(public navCtrl: NavController, public navParams: NavParams) {
    this.pages = [
      {
        title: "My Artistes",
        icon: "ios-star-outline",
        page: MyArtistesPage
      },
      {
        title: "My Albums",
        icon: "ios-albums-outline",
        page: MyAlbumsPage
      },
      {
        title: "Playlists",
        icon: "ios-musical-notes-outline",
        page: PlaylistsPage
      }];
  }

  openPage(page) {
    this.navCtrl.push(page)
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad My Musics Page');
  }
}