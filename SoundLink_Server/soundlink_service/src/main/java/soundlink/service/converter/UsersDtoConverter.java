package soundlink.service.converter;

import org.springframework.stereotype.Component;

import soundlink.dto.UsersDto;
import soundlink.model.entities.Users;

/**
 * Users converter
 * 
 * @author xrobert
 *
 */
@Component
public class UsersDtoConverter extends AbstractDtoConverter<Users, UsersDto> {

    @Override
    protected void subConvertToDto(Users entity, UsersDto dto) {
    }

    @Override
    protected void subConvertToEntity(UsersDto dto, Users entity) {
    }

}
