import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';

import { HomePage } from '../home/home';
import { MyMusicsPage } from '../mymusics/mymusics';
import { SearchPage } from '../search/search';
import { RemoteControlPage } from '../remote-control/remote-control';

@Component({
  selector: 'page-tabs',
  templateUrl: 'tabs.html'
})
export class TabsPage {

  tabHome : any = HomePage;
  tabSearch: any = SearchPage;
  tabMyMusics: any = MyMusicsPage;
  tabRemoteControl : any = RemoteControlPage;

  constructor(public navCtrl: NavController, public navParams: NavParams) {}

  ionViewDidLoad() {
    console.log('ionViewDidLoad TabsPage');
  }

}
