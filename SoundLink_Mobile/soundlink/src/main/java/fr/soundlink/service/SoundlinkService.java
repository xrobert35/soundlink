package fr.soundlink.service;

import org.androidannotations.annotations.EBean;
import org.androidannotations.rest.spring.annotations.RestService;

import fr.soundlink.bean.Connexion;
import fr.soundlink.bean.User;
import fr.soundlink.service.async.AsyncCallBack;
import fr.soundlink.service.async.AsyncRunner;

/**
 * Created by Ixoh on 19/04/2016.
 */
@EBean(scope = EBean.Scope.Singleton)
public class SoundlinkService {
	
	public static final String SERVEUR_URL = "http://192.168.1.86:8080/SoundLink_Server/";

    @RestService
    protected SoundlinkRestService soundlinkRestService;

    public SoundlinkService(){
    }

    public void login(Connexion connexion, AsyncCallBack<User> callBack){
        new AsyncRunner<Connexion, User>(callBack){
            @Override
            protected User run(Connexion connexion) {
                return soundlinkRestService.login(connexion);
            }
        }.execute(connexion);
    }
}
