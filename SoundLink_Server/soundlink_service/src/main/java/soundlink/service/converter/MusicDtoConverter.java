package soundlink.service.converter;

import org.springframework.stereotype.Component;

import soundlink.dto.MusicDto;
import soundlink.model.entities.Album;
import soundlink.model.entities.Artiste;
import soundlink.model.entities.Music;

/**
 * Music converter
 * 
 * @author xrobert
 *
 */
@Component
public class MusicDtoConverter extends AbstractDtoConverter<Music, MusicDto> {

    @Override
    protected void subConvertToDto(Music entity, MusicDto dto) {
        if (entity != null) {
            Album album = entity.getAlbum();
            dto.setAlbumName(album.getName());
            dto.setAlbumId(album.getId());
            Artiste artiste = album.getArtiste();
            dto.setArtistName(artiste.getName());
            dto.setArtisteId(artiste.getId());
        }
    }

    @Override
    protected void subConvertToEntity(MusicDto dto, Music entity) {
    }
}
