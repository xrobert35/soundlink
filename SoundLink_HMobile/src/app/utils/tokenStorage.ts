import { Storage } from '@ionic/storage';
import { Injectable } from '@angular/core';

@Injectable()
export class TokenStorage {

  private storageKey: string = 'auth_token';

  constructor(private storage: Storage) {
  }

  store(token) {
    this.storage.set(this.storageKey, token);
  }
  retrieve() {
    return this.storage.get(this.storageKey);
  }
  clear() {
    this.storage.clear();
  }
}


