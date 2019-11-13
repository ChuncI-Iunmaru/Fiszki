package dyplomowa.fiszki.Fiszki.dao;

import dyplomowa.fiszki.Fiszki.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAO extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
