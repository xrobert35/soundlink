package soundlink.server.converter;

import org.springframework.stereotype.Component;

import soundlink.model.entities.Music;
import soundlink.server.dto.MusicDto;

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
    }

    @Override
    protected void subConvertToEntity(MusicDto dto, Music entity) {

    }

}
