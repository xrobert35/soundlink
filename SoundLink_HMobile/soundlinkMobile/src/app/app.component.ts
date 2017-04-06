import { Component } from '@angular/core';
import { Platform } from 'ionic-angular';
// import { StatusBar } from '@ionic-native/status-bar';
// import { SplashScreen } from '@ionic-native/splash-screen';

import { LoginPage } from '../pages/login/login';

import { SoundlinkResources } from './resources/soundlink.resources';
import { PlaylistResources } from './resources/playlist.resource';
import { TokenStorage } from './utils/tokenStorage';
import { AppStorage } from './utils/appStorage';
import { FileManager } from './utils/fileManager';

@Component({
  templateUrl: 'app.html',
  providers: [SoundlinkResources, PlaylistResources, AppStorage, TokenStorage, FileManager]
})
export class MyApp {
  rootPage = LoginPage;

  constructor(platform: Platform) {
    platform.ready().then(() => {
      // Okay, so the platform is ready and our plugins are available.
      // Here you can do any higher level native things you might need.
      // statusBar.styleDefault();
      // splashScreen.hide();
    });
  }
}
