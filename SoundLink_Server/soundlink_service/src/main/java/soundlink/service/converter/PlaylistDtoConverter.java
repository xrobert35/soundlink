package soundlink.service.converter;

import org.springframework.stereotype.Component;

import soundlink.dto.playlist.PlaylistDto;
import soundlink.model.entities.Playlist;

@Component
public class PlaylistDtoConverter extends AbstractDtoConverter<Playlist, PlaylistDto> {

    @Override
    protected void subConvertToDto(Playlist entity, PlaylistDto dto) {
    }

    @Override
    protected void subConvertToEntity(PlaylistDto dto, Playlist entity) {
    }
}
