import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import { SoundlinkResources } from '../../app/resources/soundlink.resources';
import { AlbumsPage } from "../albums/albums";

@Component({
  selector: 'page-search',
  templateUrl: 'search.html'
})
export class SearchPage {

  artistes = [];

  constructor(public navCtrl: NavController, public navParams: NavParams, private soundlinkResources: SoundlinkResources) { }

  onArtisteSelected(artiste: any) {
    this.navCtrl.push(AlbumsPage, { artiste: artiste });
  };

  searchArtistes(event) {
    var val = event.target.value;
    if (val && val.trim() != '') {
      this.soundlinkResources.getArtistesStartWith(val).then(artistes => {
        this.artistes = artistes;
      });
    }
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad SearchPage');
  }

}
