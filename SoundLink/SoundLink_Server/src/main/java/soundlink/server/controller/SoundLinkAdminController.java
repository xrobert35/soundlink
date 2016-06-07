package soundlink.server.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class SoundLinkAdminController {

	@RequestMapping(value = "/ping", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
	public String hello() {
		return "ok";
	}
}
