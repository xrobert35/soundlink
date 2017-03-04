package fr.soundlink.service;

import org.androidannotations.rest.spring.annotations.Body;
import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.Rest;
import org.androidannotations.rest.spring.api.RestClientErrorHandling;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import fr.soundlink.bean.Connexion;
import fr.soundlink.bean.User;
import fr.soundlink.service.request.RequestFactory;
import fr.soundlink.service.request.RequestInterceptor;

/**
 * Created by Ixoh on 19/04/2016.
 */
@Rest(rootUrl = SoundlinkService.SERVEUR_URL + "soundlink", converters = { MappingJackson2HttpMessageConverter.class }, interceptors = {RequestInterceptor.class},
        requestFactory = RequestFactory.class)
public interface SoundlinkRestService extends RestClientErrorHandling {

    @Post("/login")
    User login(@Body Connexion connexion);

    @Get("/ping")
    void hello();

}
