package soundlink.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import soundlink.dto.IntegrationDto;
import soundlink.service.business.integration.IMusicExplorerService;

/**
 * Admin controller This controller will take care of all admin action
 *
 * @author xrobert
 *
 */
@RestController
@RequestMapping("/admin")
public class SoundLinkAdminController {

    @Autowired
    private IMusicExplorerService musicExplorerService;

    @RequestMapping(value = "/ping", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    public String ping() {
        return "ok";
    }

    /**
     * Entry point used to load all music from the music folder
     * 
     * @throws Exception
     *
     * @See application.properties
     */
    @RequestMapping(value = "/loadMusics", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public IntegrationDto loadMusics() {
        return musicExplorerService.loadMusics();
    }
}
