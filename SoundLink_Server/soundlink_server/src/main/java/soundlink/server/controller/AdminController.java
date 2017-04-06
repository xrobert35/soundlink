package soundlink.server.controller;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import soundlink.dto.UploadFileDto;
import soundlink.service.manager.IFileManager;

/**
 * Admin controller This controller will take care of all admin action
 *
 * @author xrobert
 *
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Value("#{environment['SOUNDLINK_HOME']}")
    private String soundlinkFolder;

    @Autowired
    private IFileManager fileManager;

    @RequestMapping(value = "/ping", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    public String ping() {
        return "ok";
    }

    @RequestMapping(value = "/integrateFromZipFile", method = RequestMethod.POST)
    public UploadFileDto integrateFromZipFile(@RequestParam("file") MultipartFile file) {
        if (!file.getOriginalFilename().endsWith(".zip")) {
            throw new InvalidParameterException("File must be a .zip file");
        }

        LocalDate localDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");
        return fileManager.saveFile(file, soundlinkFolder + "/uploadFolder",
                localDate.format(formatter) + file.getOriginalFilename());
    }
}
