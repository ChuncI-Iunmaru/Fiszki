package dyplomowa.fiszki.Fiszki;

import dyplomowa.fiszki.Fiszki.model.Flashcard;
import dyplomowa.fiszki.Fiszki.model.FlashcardDTO;
import dyplomowa.fiszki.Fiszki.model.User;
import dyplomowa.fiszki.Fiszki.service.FlashcardService;
import dyplomowa.fiszki.Fiszki.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FlashcardServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    FlashcardService flashcardService;

    @Test
    public void testSaveFlashcard(){
        User user = userService.findOne("Testowy Integracyjny");
        assertThat(user, notNullValue());
        FlashcardDTO flashcardDTO = new FlashcardDTO();
        flashcardDTO.setUserId(user.getId());
        flashcardDTO.setFrontText("Fiszka Integracyjna");
        flashcardDTO.setBackText("To Tylko Test");
        //Powinno przejść bez extra text
        Set<String> tags = new HashSet<>();
        tags.add("Integracyjna");
        tags.add("test");
        tags.add("testowa");
        flashcardDTO.setTags(tags);

        Flashcard result = flashcardService.save(flashcardDTO);
        assertThat(result.getFrontText(), equalTo(flashcardDTO.getFrontText()));
    }

    @Test
    public void testFindByTags(){
        User user = userService.findOne("Testowy Integracyjny");
        assertThat(user, notNullValue());
        assertThat(flashcardService.findByUserAndTags(user, Arrays.asList("Integracyjna")).size(), equalTo(1));
        assertThat(flashcardService.findByUserAndTags(user, Arrays.asList("Integracyjna", "test", "testowa")).size(), equalTo(1));
        assertThat(flashcardService.findByUserAndTags(user, Arrays.asList("NieMaTakiegoTaga")).size(), equalTo(0));
    }

    @Test
    public void testUpdate(){
        User user = userService.findOne("Testowy Integracyjny");
        assertThat("User must exist", user, notNullValue());
        //Id na sztywno
        Flashcard flashcard = flashcardService.findById(5L);
        assertThat("Flashcard must exist before update", flashcard, notNullValue());

        FlashcardDTO flashcardDTO = new FlashcardDTO();
        BeanUtils.copyProperties(flashcard, flashcardDTO);

        flashcardDTO.setTags(new HashSet<>(Arrays.asList("NowyTag")));

        flashcardService.update(flashcardDTO);

        assertThat(flashcardService.findByUserAndTags(user, Arrays.asList("NowyTag")).size(), equalTo(1));
    }

    @Test
    //W bazie usunięcie fiszki usuwa jej tagi
    public void testDelete(){
        //Id na sztywno
        flashcardService.delete(5L);
        Flashcard flashcard = flashcardService.findById(5L);
        assertThat(flashcard, nullValue());
    }
}
