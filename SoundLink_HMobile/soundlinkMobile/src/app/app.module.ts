import { NgModule, ErrorHandler } from '@angular/core';
import { IonicApp, IonicModule, IonicErrorHandler } from 'ionic-angular';

import { MyApp } from './app.component';

import { LoginPage } from '../pages/login/login';
import { HomePage } from '../pages/home/home'
import { TabsPage } from '../pages/tabs/tabs'

import { ArtistesPage } from '../pages/artistes/artistes';
import { AlbumsPage } from '../pages/albums/albums';
import { AlbumMusicsPage } from '../pages/album-musics/album-musics'

import { SearchPage } from '../pages/search/search';
import { PlaylistsPage } from '../pages/playlists/playlists';
import { PlaylistCreatePage } from '../pages/playlists/create/playlist.create';

import { StatusBar } from '@ionic-native/status-bar';
import { SplashScreen } from '@ionic-native/splash-screen';

import { File } from '@ionic-native/file';
import { FileChooser } from '@ionic-native/file-chooser';

import { IonicStorageModule } from '@ionic/storage';

@NgModule({
  declarations: [
    MyApp,
    LoginPage,
    TabsPage,
    HomePage,
    SearchPage,
    PlaylistsPage,
    PlaylistCreatePage,
    ArtistesPage,
    AlbumsPage,
    AlbumMusicsPage
  ],
  imports: [
    IonicModule.forRoot(MyApp, {
      tabsHideOnSubPages: true,
      tabsPlacement: 'top'
    }),
    IonicStorageModule.forRoot()
  ],
  bootstrap: [IonicApp],
  entryComponents: [
    MyApp,
    LoginPage,
    TabsPage,
    HomePage,
    SearchPage,
    PlaylistsPage,
    PlaylistCreatePage,
    ArtistesPage,
    AlbumsPage,
    AlbumMusicsPage
  ],
  providers: [
    StatusBar,
    SplashScreen,
    File,
    FileChooser,
    Storage,
    { provide: ErrorHandler, useClass: IonicErrorHandler }
  ]
})
export class AppModule { }
