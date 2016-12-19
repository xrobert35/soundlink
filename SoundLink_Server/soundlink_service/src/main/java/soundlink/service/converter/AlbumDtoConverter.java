package soundlink.service.converter;

import org.springframework.stereotype.Component;

import soundlink.dto.AlbumDto;
import soundlink.model.entities.Album;

/**
 * Album converter
 * 
 * @author xrobert
 *
 */
@Component
public class AlbumDtoConverter extends AbstractDtoConverter<Album, AlbumDto> {

    @Override
    protected void subConvertToDto(Album album, AlbumDto dto) {
        dto.setArtisteName(album.getArtiste().getName());
    }

    @Override
    protected void subConvertToEntity(AlbumDto dto, Album entity) {
        // Simple properties convertion, we generaly need only id
    }
}
