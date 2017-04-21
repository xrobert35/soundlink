import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import { ArtistesResource } from '../../app/resources/artistes.resource';
import { AlbumsPage } from "../albums/albums";

@Component({
  selector: 'page-myartistes',
  templateUrl: 'myartistes.html'
})
export class MyArtistesPage {

  artistes = [];

  constructor(public navCtrl: NavController, public navParams: NavParams, private artistesResource: ArtistesResource) { }

  onArtisteSelected(artiste: any) {
    this.navCtrl.push(AlbumsPage, { artiste: artiste });
  }

  getArtisteCoverUrl(artiste) {
    return this.artistesResource.getArtisteCoverUrl(artiste.id);
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad My Artistes Page');
    this.artistesResource.getUserArtistes().then(artistes => {
      this.artistes = artistes;
    });
  }
}