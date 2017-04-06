import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import { SoundlinkResources } from '../../app/resources/soundlink.resources';

@Component({
  selector: 'page-artistes',
  templateUrl: 'artistes.html'
})
export class ArtistesPage {

  artistes = [];

  constructor(public navCtrl: NavController, public navParams: NavParams, private soundlinkResources: SoundlinkResources) { }
  ionViewDidLoad() {
    console.log('ionViewDidLoad Artistes Page');
  }
}
