package soundlink.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import soundlink.model.entities.Users;

/**
 * User repository
 *
 * @author xrobert
 *
 */
public interface UserRepository extends JpaRepository<Users, String> {

}
