package fr.soundlink.service.request;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import android.content.Context;

import java.io.IOException;

/**
 * Created by Ixoh on 21/04/2016.
 */
@EBean(scope = EBean.Scope.Singleton)
public class RequestInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        traceRequest(request, body);
        ClientHttpResponse response = execution.execute(request, body);
        traceResponse(response);
        return response;
    }

    private void traceRequest(HttpRequest request, byte[] body) throws IOException {
        System.out.println("===========================request begin================================================");
        System.out.println("URI : " + request.getURI());
        System.out.println("Method : " + request.getMethod());
        System.out.println("Header : " + request.getHeaders().toString());
        System.out.println("Request Body : " + new String(body, "UTF-8"));
        System.out.println("==========================request end================================================");
    }

    private void traceResponse(ClientHttpResponse response) throws IOException {
//        StringBuilder inputStringBuilder = new StringBuilder();
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody(), "UTF-8"));
//        String line = bufferedReader.readLine();
//        while (line != null) {
//            inputStringBuilder.append(line);
//            inputStringBuilder.append('\n');
//            line = bufferedReader.readLine();
//        }
//        System.out.println("============================response begin==========================================");
//        System.out.println("status code: " + response.getStatusCode());
//        System.out.println("status text: " + response.getStatusText());
//        System.out.println("Response Body : " + inputStringBuilder.toString());
//        System.out.println("=======================response end=================================================");
//
//        response.getBody().reset();
    }

}