import { Component } from '@angular/core';
import { Platform } from 'ionic-angular';

import { LoginPage } from '../pages/login/login';

import { SoundlinkResource } from './resources/soundlink.resource';
import { PlaylistsResource } from './resources/playlists.resource';
import { ArtistesResource } from './resources/artistes.resource';
import { AlbumsResource } from './resources/albums.resource';
import { MusicsResource } from './resources/musics.resource';

import { TokenStorage } from './utils/tokenStorage';
import { AppStorage } from './utils/appStorage';
import { FileManager } from './utils/fileManager';

@Component({
  templateUrl: 'app.html',
  providers: [SoundlinkResource, PlaylistsResource, ArtistesResource, AlbumsResource, MusicsResource,
    AppStorage, TokenStorage, FileManager]
})
export class MyApp {
  rootPage = LoginPage;

  constructor(platform: Platform) {
    platform.ready().then(() => {
    });
  }
}
