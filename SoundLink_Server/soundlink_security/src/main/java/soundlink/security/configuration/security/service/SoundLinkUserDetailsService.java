package soundlink.security.configuration.security.service;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findOne(username);

        if (user == null) {
            throw new UsernameNotFoundException("Unknow user");
        }

        SoundLinkUserDetails userDetails = new SoundLinkUserDetails();
        userDetails.setUsername(user.getLogin());
        userDetails.setEmail(user.getEmail());
        userDetails.setPassword(user.getPassword());
        if (user.getRole() != null) {
            userDetails.setAuthorities(getGrantedAuthorities(user.getRole().name()));
        }

        return userDetails;
    }

    private Collection<GrantedAuthority> getGrantedAuthorities(String userRole) {
        Collection<GrantedAuthority> authorities;
        if (userRole.equals("ADMIN")) {
            SimpleGrantedAuthority admin = new SimpleGrantedAuthority("ADMIN");
            authorities = Arrays.asList(admin);
        } else {
            SimpleGrantedAuthority basic = new SimpleGrantedAuthority("BASIC");
            authorities = Arrays.asList(basic);
        }
        return authorities;
    }

}
