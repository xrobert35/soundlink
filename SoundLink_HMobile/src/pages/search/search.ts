import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import { ArtistesResource } from '../../app/resources/artistes.resource';
import { AlbumsPage } from "../albums/albums";

@Component({
  selector: 'page-search',
  templateUrl: 'search.html'
})
export class SearchPage {

  artistes = [];

  constructor(public navCtrl: NavController, public navParams: NavParams, private artistesResource: ArtistesResource) { }

  onArtisteSelected(artiste: any) {
    this.navCtrl.push(AlbumsPage, { artiste: artiste });
  };

  searchArtistes(event) {
    var val = event.target.value;
    if (val && val.trim() != '') {
      this.artistesResource.getArtistesStartWith(val).then(artistes => {
        this.artistes = artistes;
      });
    }
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad SearchPage');
  }

}
