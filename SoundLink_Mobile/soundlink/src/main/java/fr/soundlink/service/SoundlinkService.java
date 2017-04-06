package fr.soundlink.service;

import org.androidannotations.annotations.EBean;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.http.client.ClientHttpResponse;

import fr.soundlink.bean.Connexion;
import fr.soundlink.bean.User;
import fr.soundlink.config.SoundlinkConfig;
import fr.soundlink.service.async.AsyncCallBack;
import fr.soundlink.service.async.AsyncRunner;

/**
 * Created by Ixoh on 19/04/2016.
 */
@EBean(scope = EBean.Scope.Singleton)
public class SoundlinkService {
	
	
    @RestService
    protected SoundlinkRestService soundlinkRestService;

    public SoundlinkService(){
    }

    public void login(Connexion connexion, AsyncCallBack<String> callBack){
        new AsyncRunner<Connexion, String>(callBack){
            @Override
            protected String run(Connexion connexion) {
            	ClientHttpResponse response = soundlinkRestService.login(connexion);
            	return (String)response.getHeaders().get("X-AUTH-TOKEN").get(0);
            }
        }.execute(connexion);
    }
}
