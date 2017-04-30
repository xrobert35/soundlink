package soundlink.server.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import soundlink.dto.UsersDto;

@Component
public class UsersDtoValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return UsersDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        System.out.println("Validation");
    }

}
