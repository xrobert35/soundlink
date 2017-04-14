package soundlink.server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import soundlink.dto.ArtisteDto;
import soundlink.dto.SearchArtisteDto;
import soundlink.model.entities.Artiste;
import soundlink.model.entities.Users;
import soundlink.service.converter.ArtisteDtoConverter;
import soundlink.service.converter.SearchArtisteDtoConverter;
import soundlink.service.manager.IArtisteManager;
import soundlink.service.manager.IUsersManager;

@RestController
@RequestMapping("/soundlink/artistes")
public class ArtistesController {

    @Autowired
    private IArtisteManager artisteManager;

    @Autowired
    private ArtisteDtoConverter artisteDtoConverter;

    @Autowired
    private SearchArtisteDtoConverter searchArtisteDtoConverter;

    @Autowired
    private IUsersManager usersManager;

    /**
     * Entry point to get all artites
     *
     * @return a List with all artites informations
     */
    @RequestMapping(value = "/artistesStartWith", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<SearchArtisteDto> getArtistesStartWith(@RequestParam String startChain) {
        Users user = usersManager.getUserByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Artiste> artistesStartWith = artisteManager.getArtistesStartWith(startChain, user.getId());
        return searchArtisteDtoConverter.convertToDtoList(artistesStartWith);
    }

    @RequestMapping(value = "/userArtistes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<ArtisteDto> getUserArtistes() {
        Users user = usersManager.getUserByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Artiste> artistes = artisteManager.getArtistesByUserId(user.getId());
        return artisteDtoConverter.convertToDtoList(artistes);
    }
}
