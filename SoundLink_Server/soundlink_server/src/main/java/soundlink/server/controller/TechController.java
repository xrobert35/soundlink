package soundlink.server.controller;

import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import soundlink.security.configuration.security.beans.SoundLinkUserDetails;
import soundlink.security.configuration.security.service.TokenHandler;

@RestController
@RequestMapping("soundlink/tech")
public class TechController {

    @Value("${token.secret}")
    private String secret;

    @RequestMapping(value = "/wsToken", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    public String getWsToken() {
        TokenHandler tokenHandler = new TokenHandler(DatatypeConverter.parseBase64Binary(secret));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SoundLinkUserDetails details = (SoundLinkUserDetails) authentication.getDetails();
        return tokenHandler.createTemporaryTokenForUser(details);

    }
}
