import { Component, Input } from '@angular/core';
import { NavController } from 'ionic-angular';

import { LoginPageService } from './login.service'
import { TabsPage } from "../tabs/tabs"

@Component({
  selector: 'page-login',
  templateUrl: 'login.html',
  providers: [LoginPageService]
})
export class LoginPage {

  @Input() credentiel = {
    login: "",
    password: ""
  };

  @Input() loginMessage;

  constructor(public navCtrl: NavController, private service: LoginPageService) {
  }

  login() {
    this.service.login(this.credentiel.login, this.credentiel.password).then(data => {
      this.loginMessage = "Success";
      this.navCtrl.setRoot(TabsPage);
    }).catch(error => {
      this.loginMessage = "Error";
    });
  }
}
