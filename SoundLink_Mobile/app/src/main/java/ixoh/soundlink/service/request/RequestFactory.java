package ixoh.soundlink.service.request;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

/**
 * Created by Ixoh on 21/04/2016.
 */
@EBean(scope = EBean.Scope.Singleton)
public class RequestFactory extends SimpleClientHttpRequestFactory {

    @AfterInject
    void afterinject() {
        setReadTimeout(10*1000); //set 20s read timeout
        setConnectTimeout(10*1000); //set 20s connect timeout
    }
}