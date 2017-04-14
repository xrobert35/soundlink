package soundlink.service.converter;

import org.springframework.stereotype.Component;

import soundlink.dto.ArtisteDto;
import soundlink.model.entities.Artiste;

/**
 * Artiste converter
 *
 * @author xrobert
 *
 */
@Component
public class ArtisteDtoConverter extends AbstractDtoConverter<Artiste, ArtisteDto> {

    @Override
    protected void subConvertToDto(Artiste artiste, ArtisteDto dto) {
    }

    @Override
    protected void subConvertToEntity(ArtisteDto dto, Artiste artiste) {
    }
}
