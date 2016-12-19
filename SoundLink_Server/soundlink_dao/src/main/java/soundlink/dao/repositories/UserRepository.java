package soundlink.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import soundlink.model.entities.Users;

/**
 * User repository
 *
 * @author xrobert
 *
 */
public interface UserRepository extends JpaRepository<Users, Long> {

    @Query("SELECT user FROM Users user where user.login = :login ")
    Users findByLogin(@Param("login") String login);
}
