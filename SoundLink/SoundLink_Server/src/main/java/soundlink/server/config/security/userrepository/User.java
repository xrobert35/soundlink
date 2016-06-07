package soundlink.server.config.security.userrepository;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "users")
public class User {

	@Id
	private String username;

	private String password;

	private String email;

	private String role;

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
