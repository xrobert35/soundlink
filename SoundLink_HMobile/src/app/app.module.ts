import { NgModule, ErrorHandler } from '@angular/core';
import { IonicApp, IonicModule, IonicErrorHandler } from 'ionic-angular';

import { MyApp } from './app.component';

import { LoginPage } from '../pages/login/login';
import { HomePage } from '../pages/home/home'
import { TabsPage } from '../pages/tabs/tabs'

import { MyMusicsPage } from '../pages/mymusics/mymusics'
import { MyArtistesPage } from '../pages/myartistes/myartistes';
import { MyAlbumsPage } from '../pages/myalbums/myalbums';
import { PlaylistsPage } from '../pages/playlists/playlists';

import { SearchPage } from '../pages/search/search';
import { RemoteControlPage} from '../pages/remote-control/remote-control';

import { AlbumsPage } from '../pages/albums/albums'
import { AlbumMusicsPage } from '../pages/album-musics/album-musics'

import { PlaylistCreatePage } from '../pages/playlists/create/playlist.create';

import { StatusBar } from '@ionic-native/status-bar';
import { SplashScreen } from '@ionic-native/splash-screen';

import { File } from '@ionic-native/file';
import { FileChooser } from '@ionic-native/file-chooser';
import { FilePath } from '@ionic-native/file-path';
import { Diagnostic } from '@ionic-native/diagnostic';

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
    MyMusicsPage,
    MyArtistesPage,
    MyAlbumsPage,
    AlbumsPage,
    AlbumMusicsPage,
    RemoteControlPage
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
    MyArtistesPage,
    MyMusicsPage,
    MyAlbumsPage,
    AlbumsPage,
    AlbumMusicsPage,
    RemoteControlPage
  ],
  providers: [
    StatusBar,
    SplashScreen,
    File,
    FileChooser,
    FilePath,
    Storage,
    Diagnostic,
    { provide: ErrorHandler, useClass: IonicErrorHandler }
  ]
})
export class AppModule { }
