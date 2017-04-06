package soundlink.server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import soundlink.dto.ArtisteDto;
import soundlink.service.converter.ArtisteDtoConverter;
import soundlink.service.manager.IArtisteManager;

@RestController
@RequestMapping("/soundlink")
public class ArtistesController {

    @Autowired
    private IArtisteManager artisteManager;

    @Autowired
    private ArtisteDtoConverter artisteDtoConverter;

    /**
     * Entry point to get all artites
     *
     * @return a List with all artites informations
     */
    @RequestMapping(value = "/artistes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<ArtisteDto> getArtistes() {
        List<ArtisteDto> allAlbums = artisteDtoConverter.convertToDtoList(artisteManager.getAllArtistesHavingAlbum());
        return allAlbums;
    }

    /**
     * Entry point to get all artites
     *
     * @return a List with all artites informations
     */
    @RequestMapping(value = "/artistesStartWith", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<ArtisteDto> getArtistesStartWith(@RequestParam String startChain) {
        List<ArtisteDto> allAlbums = artisteDtoConverter
                .convertToDtoList(artisteManager.getArtistesStartWith(startChain));
        return allAlbums;
    }
}
