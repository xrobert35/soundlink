package soundlink.service.converter;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import soundlink.dto.SearchArtisteDto;
import soundlink.model.entities.Artiste;

@Component
public class SearchArtisteDtoConverter extends AbstractDtoConverter<Artiste, SearchArtisteDto> {

    @Override
    protected void subConvertToDto(Artiste artiste, SearchArtisteDto dto) {
        if (CollectionUtils.isNotEmpty(artiste.getUsers())) {
            dto.setSelected(true);
        }
    }

    @Override
    protected void subConvertToEntity(SearchArtisteDto dto, Artiste artiste) {
    }
}
