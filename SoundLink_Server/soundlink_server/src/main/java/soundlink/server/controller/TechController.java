package soundlink.server.controller;

import java.util.Locale;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import soundlink.server.configuration.AppConfig.SerializableResourceBundleMessageSource;
import soundlink.service.manager.impl.FileManager;

@RestController
@RequestMapping("/message")
public class TechController {

    @Autowired
    private FileManager fileManager;

    @Autowired
    private SerializableResourceBundleMessageSource messageBundle;

    @RequestMapping(value = "/default", method = RequestMethod.GET)
    public Properties list(@RequestParam String lang) {
        return messageBundle.getAllProperties(new Locale(lang));
    }

    /**
     * Entry point used to get byte content of an image
     *
     * @param path image path
     * @param name image name
     * @param ext image extension
     * @return an image
     */
    @RequestMapping(value = "/image/{path}/{name}.{ext}", produces = MediaType.IMAGE_PNG_VALUE)
    public FileSystemResource showImage(@PathVariable("path") String path, @PathVariable("name") String name,
            @PathVariable("ext") String ext) {
        return new FileSystemResource(fileManager.getFile(path + FileManager.separator + name + "." + ext));
    }
}
