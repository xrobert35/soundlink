import { SoundlinkResource } from '../../app/resources/soundlink.resource';
import { TokenStorage } from '../../app/utils/tokenStorage'
import { AppStorage } from '../../app/utils/appStorage'
import { Injectable } from '@angular/core';

@Injectable()
export class LoginPageService {

  constructor(private soundlinkResource: SoundlinkResource, private appStorage: AppStorage, private tokenStorage: TokenStorage) {
  }

  login(username, password) {
    var credentiels = { username: username, password: password };
    return this.soundlinkResource.login(credentiels).then(response => {
      let authToken = response.headers.get('x-auth-token');
      this.tokenStorage.store(authToken);
      return this.soundlinkResource.getUserInformation();
    }).then(userInformation => {
      this.appStorage.setUser(userInformation);
    });
  }
}
