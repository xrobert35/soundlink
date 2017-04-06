package fr.soundlink.service.request;

import java.io.IOException;
import java.net.URI;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

import android.content.Context;

/**
 * Created by Ixoh on 21/04/2016.
 */
@EBean(scope = EBean.Scope.Singleton)
public class RequestFactory extends SimpleClientHttpRequestFactory {
	
    @RootContext
    protected Context context;

    @AfterInject
    void afterinject() {
        setReadTimeout(10*1000); //set 20s read timeout
        setConnectTimeout(10*1000); //set 20s connect timeout
    }
    
    @Override
    public ClientHttpRequest createRequest(URI uri, HttpMethod httpMethod) throws IOException {
    	ClientHttpRequest request =  super.createRequest(uri, httpMethod);
    	request.getHeaders().add("X-AUTH-TOKEN", context.get);
    	return request;
    }
}