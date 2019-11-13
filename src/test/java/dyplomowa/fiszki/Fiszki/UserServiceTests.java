package dyplomowa.fiszki.Fiszki;

import dyplomowa.fiszki.Fiszki.model.User;
import dyplomowa.fiszki.Fiszki.model.UserDto;
import dyplomowa.fiszki.Fiszki.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.List;

@RunWith(SpringRunner.class)
//DataJpaTest nie działa bo nie ma wbudowanej bd?
@SpringBootTest
public class UserServiceTests {

    @Autowired
    UserService userService;

    @Test
    public void testSaveUser(){
        UserDto userDto = new UserDto();
        userDto.setUsername("Testowy Integracyjny");
        userDto.setPassword("integracja");
        userDto.setEmail("testowy@test.com");
        userDto.setRole("user");

        User result = userService.save(userDto);
        assertThat(result.getUsername(), equalTo(userDto.getUsername()));
    }


    @Test
    public void testFindAll(){
        List<User> userList = userService.findAll();
        assertThat(userList.size(), is(greaterThanOrEqualTo(1)));
    }

    @Test
    public void testUpdateUser(){
        User user = userService.findOne("Testowy Integracyjny");
        assertThat(user, notNullValue());
        UserDto userDto = new UserDto();
        //Wszystkie kolumny muszą być podane przy update
        BeanUtils.copyProperties(user, userDto, "password");
        userDto.setUsername("Inny Integracyjny");

        userService.update(userDto);

        User result = userService.findById(user.getId());
        assertThat(result.getUsername(), equalTo(userDto.getUsername()));
    }

    @Test
    //Usunięcie użytkownika usuwa też jego fiszki (a usunięcie fiszek tagi z ich id)
    public void testDeleteUser(){
        User user = userService.findOne("Testowy Integracyjny");
        assertThat(user, notNullValue());

        userService.delete(user.getId());

        assertThat(userService.findOne("Testowy Integracyjny"), nullValue());
    }
}
