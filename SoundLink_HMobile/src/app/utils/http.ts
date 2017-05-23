// import { Injectable } from '@angular/core';
// import { Http, Headers,ConnectionBackend, RequestOptions,RequestOptionsArgs, URLSearchParams } from '@angular/http';

// @Injectable()
// export class CustomHttp extends Http {

//   constructor(backend: ConnectionBackend, defaultOptions: RequestOptions) {
//     super(backend, defaultOptions);
//   }

//   request(url: string | Request, options?: RequestOptionsArgs): Observable<Response> {
//     console.log('request...');
//     return super.request(url, options).catch(res => {
//       // do something
//     });        
//   }

//   get(url: string, options?: RequestOptionsArgs): Observable<Response> {
//     console.log('get...');
//     return super.get(url, options).catch(res => {
//       // do something
//     });
//   }
// }