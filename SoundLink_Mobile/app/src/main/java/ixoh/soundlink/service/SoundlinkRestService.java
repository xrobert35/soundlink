package ixoh.soundlink.service;

import org.androidannotations.rest.spring.annotations.Body;
import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.Rest;
import org.androidannotations.rest.spring.api.RestClientErrorHandling;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import ixoh.soundlink.bean.Connexion;
import ixoh.soundlink.bean.User;
import ixoh.soundlink.service.request.RequestFactory;
import ixoh.soundlink.service.request.RequestInterceptor;

/**
 * Created by Ixoh on 19/04/2016.
 */
@Rest(rootUrl = "http://192.168.1.18:8080/SoundLink_Server/soundlink", converters = { MappingJackson2HttpMessageConverter.class }, interceptors = {RequestInterceptor.class},
        requestFactory = RequestFactory.class)
public interface SoundlinkRestService extends RestClientErrorHandling {

    @Post("/login")
    User login(@Body Connexion connexion);

    @Get("/ping")
    void hello();

}
