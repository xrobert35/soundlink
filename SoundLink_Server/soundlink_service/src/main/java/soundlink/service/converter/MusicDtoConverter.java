package soundlink.service.converter;

import org.springframework.stereotype.Component;

import soundlink.dto.MusicDto;
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
    }

    @Override
    protected void subConvertToEntity(MusicDto dto, Music entity) {

    }

}
