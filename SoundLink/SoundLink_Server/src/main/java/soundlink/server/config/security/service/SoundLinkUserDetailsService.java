package soundlink.server.config.security.service;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import soundlink.server.config.security.userrepository.User;
import soundlink.server.config.security.userrepository.UserRepository;

public class SoundLinkUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findOne(username);

		if (user == null) {
			throw new UsernameNotFoundException("Unknow user");
		}

		SoundLinkUserDetails userDetails = new SoundLinkUserDetails();
		userDetails.setUsername(user.getUsername());
		userDetails.setEmail(user.getEmail());
		userDetails.setPassword(user.getPassword());
		userDetails.setAuthorities(getGrantedAuthorities(user.getRole()));

		return userDetails;
	}

	private Collection<GrantedAuthority> getGrantedAuthorities(String userRole) {
		Collection<GrantedAuthority> authorities;
		if (userRole.equals("ADMIN")) {
			SimpleGrantedAuthority admin = new SimpleGrantedAuthority("ROLE_ADMIN");
			authorities = Arrays.asList(admin);
		} else {
			SimpleGrantedAuthority basic = new SimpleGrantedAuthority("ROLE_BASIC");
			authorities = Arrays.asList(basic);
		}
		return authorities;
	}

}
