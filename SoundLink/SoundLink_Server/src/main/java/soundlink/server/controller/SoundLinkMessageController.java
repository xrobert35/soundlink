package soundlink.server.controller;

import java.util.Locale;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import soundlink.server.config.AppConfig.SerializableResourceBundleMessageSource;

@RestController
@RequestMapping("/soundlinkMessage")
public class SoundLinkMessageController {

	@Autowired
    private SerializableResourceBundleMessageSource messageBundle;
	
	@RequestMapping(value="/default", method = RequestMethod.GET)
	public Properties list(@RequestParam String lang) {
	    return messageBundle.getAllProperties(new Locale(lang));
	}
	
}
