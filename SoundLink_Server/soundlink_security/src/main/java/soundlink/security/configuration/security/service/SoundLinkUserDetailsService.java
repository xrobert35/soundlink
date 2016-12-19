package soundlink.security.configuration.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import soundlink.dao.repositories.UserRepository;
import soundlink.model.entities.Users;
import soundlink.security.configuration.security.beans.SoundLinkUserDetails;

public class SoundLinkUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Users user = userRepository.findByLogin(login);

        if (user == null) {
            throw new UsernameNotFoundException("Unknow user");
        }

        SoundLinkUserDetails userDetails = new SoundLinkUserDetails();
        userDetails.setUsername(user.getLogin());
        userDetails.setEmail(user.getEmail());
        userDetails.setPassword(user.getPassword());
        if (user.getRole() != null) {
            userDetails.getAuthorities().add(getGrantedAuthorities(user.getRole().name()));
        }

        return userDetails;
    }

    private SimpleGrantedAuthority getGrantedAuthorities(String userRole) {
        if (userRole.equals("ADMIN")) {
            return new SimpleGrantedAuthority("ADMIN");
        } else {
            return new SimpleGrantedAuthority("BASIC");
        }
    }

}
