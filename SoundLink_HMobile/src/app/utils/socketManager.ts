import { Injectable } from '@angular/core';
import { AppConfig } from '../config/appConfig';
import { $WebSocket, WebSocketSendMode } from 'angular2-websocket/angular2-websocket';
import { TechResource } from '../resources/tech.resource';

@Injectable()
export class SocketManager {

  constructor(private techResource: TechResource) {
  }

  openSocket(name) : Promise<$WebSocket> {
    return this.techResource.getWsToken().then(token => {
      var httpParam = this.getHttpParam({'X-AUTH-TOKEN' : token});
      var ws = new $WebSocket(AppConfig.SOCKET_URL + name + httpParam);
      ws.setSend4Mode(WebSocketSendMode.Direct);
      return ws;
    });
  }

  private getHttpParam(param) {
    var httpParam = '?';
    var isFirst = true;
    for (var key in param) {
      if (param.hasOwnProperty(key)) {
        if (!isFirst) {
          httpParam += '&';
        } else {
          isFirst = false;
        }
        httpParam += key + '=' + param[key];
      }
    }
    return httpParam;
  }
}