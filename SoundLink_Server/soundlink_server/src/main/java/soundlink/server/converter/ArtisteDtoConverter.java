package soundlink.server.converter;

import org.springframework.stereotype.Component;

import soundlink.model.entities.Artiste;
import soundlink.server.dto.ArtisteDto;

/**
 * Artiste converter
 *
 * @author xrobert
 *
 */
@Component
public class ArtisteDtoConverter extends AbstractDtoConverter<Artiste, ArtisteDto> {

    @Override
    protected void subConvertToDto(Artiste entity, ArtisteDto dto) {
    }

    @Override
    protected void subConvertToEntity(ArtisteDto dto, Artiste entity) {
    }
}
