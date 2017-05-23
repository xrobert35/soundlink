import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import { SocketManager } from './../../app/utils/socketManager';

@Component({
  selector: 'page-remote-control',
  templateUrl: 'remote-control.html'
})
export class RemoteControlPage {

  volume = 50;
  socket;

  constructor(public navCtrl: NavController, public navParams: NavParams, private socketManager: SocketManager) { }

  remoteConnect() {
    this.socketManager.openSocket("remotecontrol").then(remoteControlSocket => {
      this.socket = remoteControlSocket;
      this.socket.connect();
      this.socket.onOpen(function () {
        alert("socket open");
      });
      this.socket.onClose(function (){
        alert("socket close");
      });
    });
  }

  volumeUp() {
    if (this.volume < 100) {
      this.volume = this.volume + 5;
      this.remote('SET_VOLUME:' + this.volume)
    }
  }

  volumeDown() {
    if (this.volume > 0) {
      this.volume = this.volume - 5;
      this.remote('SET_VOLUME:' + this.volume)
    }
  }

  remote(action) {
    if (this.socket != null) {
      this.socket.send(JSON.stringify({ action: action }));
    }
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad RemoteControlPage');
  }

}
