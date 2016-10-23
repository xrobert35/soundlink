package soundlink.server.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Admin controller This controller will take care of all admin action
 *
 * @author xrobert
 *
 */
@RestController
@RequestMapping("/admin")
public class SoundLinkAdminController {

    @Value("${file.upload.path}")
    private String filePath;

    @RequestMapping(value = "/ping", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    public String ping() {
        return "ok";
    }

    /**
     * Entry point used to load all music from the music folder
     *
     * @See application.properties
     */
    @RequestMapping(value = "/loadmusic", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void loadNewMusic() {

    }
}
