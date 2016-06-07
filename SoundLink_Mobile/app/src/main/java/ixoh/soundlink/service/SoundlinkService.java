package ixoh.soundlink.service;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.rest.spring.annotations.RestService;

import ixoh.soundlink.bean.Connexion;
import ixoh.soundlink.bean.User;
import ixoh.soundlink.service.async.AsyncCallBack;
import ixoh.soundlink.service.async.AsyncRunner;

/**
 * Created by Ixoh on 19/04/2016.
 */
@EBean(scope = EBean.Scope.Singleton)
public class SoundlinkService {

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
