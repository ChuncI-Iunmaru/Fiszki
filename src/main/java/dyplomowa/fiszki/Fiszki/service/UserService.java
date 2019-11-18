package dyplomowa.fiszki.Fiszki.service;

import dyplomowa.fiszki.Fiszki.model.entity.User;
import dyplomowa.fiszki.Fiszki.model.dto.UserDto;

import java.util.List;

public interface UserService {

    User save(UserDto user);
    List<User> findAll();
    void delete(long id);

    User findOne(String username);

    User findById(long id);

    UserDto update(UserDto userDto);
}
