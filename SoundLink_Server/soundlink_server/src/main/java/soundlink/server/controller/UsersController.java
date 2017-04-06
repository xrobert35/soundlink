package soundlink.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import soundlink.dto.UsersDto;
import soundlink.model.entities.Users;
import soundlink.service.converter.UsersDtoConverter;
import soundlink.service.manager.IUsersManager;

@RestController
@RequestMapping("/soundlink")
public class UsersController {

    @Autowired
    private IUsersManager usersManager;

    @Autowired
    private UsersDtoConverter usersDtoConveter;

    @RequestMapping(value = "/userInformation", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public UsersDto getUserInformation() {
        Users user = usersManager.getUserByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
        return usersDtoConveter.convertToDto(user);
    }

    @RequestMapping(value = "/saveUserInformation", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public UsersDto saveUserInformation(@RequestBody UsersDto usersDto) {
        Users user = usersManager.updateUser(SecurityContextHolder.getContext().getAuthentication().getName(),
                usersDto);
        return usersDtoConveter.convertToDto(user);
    }
}
