package dyplomowa.fiszki.Fiszki;


import dyplomowa.fiszki.Fiszki.model.entity.Flashcard;
import dyplomowa.fiszki.Fiszki.model.entity.FlashcardSet;
import dyplomowa.fiszki.Fiszki.model.entity.User;
import dyplomowa.fiszki.Fiszki.service.FlashcardService;
import dyplomowa.fiszki.Fiszki.service.FlashcardSetService;
import dyplomowa.fiszki.Fiszki.service.UserService;
import dyplomowa.fiszki.Fiszki.utils.TestAccesibility;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FlashcardSetServiceTest {

    @Autowired
    FlashcardSetService flashcardSetService;

    @Autowired
    FlashcardService flashcardService;

    @Autowired
    UserService userService;

    @Test
    public void testSaveFlashcardSet() {
        FlashcardSet flashcardSet = new FlashcardSet();

        User user = userService.findOne("testowy");
        assertThat(user, notNullValue());

        flashcardSet.setCreator(user);

        List<Flashcard> flashcards = flashcardService.findByUser(user);
        assertThat(flashcards.size(), greaterThanOrEqualTo(1));

        flashcardSet.setFlashcards(new HashSet<>(flashcards));

        flashcardSet.setTitle("Testowy Zestaw");
        flashcardSet.setPassword("testowe haslo");
        flashcardSet.setDailyAmount(1);
        flashcardSet.setTestQuestionsNum(10);
        //TODO trochę toprnie to wygląda w praktyce. Może inaczej użyć enuma?
        flashcardSet.setTestAccessible(TestAccesibility.ALWAYS.toString());
        //TestAttempts i TestTime powinny domyślnie wyjść 0 w bd

        FlashcardSet result = flashcardSetService.save(flashcardSet);
        assertThat(result, notNullValue());
        assertThat(result.getTitle(), equalTo(flashcardSet.getTitle()));
        assertThat(result.getTestAttempts(), equalTo(0));
        assertThat(result.getTestTime(), equalTo(0));
    }

    @Test
    public void testFindAll(){
        List<FlashcardSet> flashcardSets = flashcardSetService.findAll();
        assertThat(flashcardSets.size(), greaterThanOrEqualTo(1));
    }

    @Test
    public void testFindById(){
        FlashcardSet result = flashcardSetService.findById(1L);
        assertThat(result, notNullValue());
    }

    @Test
    //TODO nie wiem czy to działa jak powinno - nie widać zmian w bd
    @Transactional
    public void testUpdate(){
        FlashcardSet flashcardSet = flashcardSetService.findById(1L);
        assertThat(flashcardSet, notNullValue());

        Flashcard newFlashcard = flashcardService.findById(9L);
        assertThat(newFlashcard, notNullValue());

        Set<Flashcard> flashcards = flashcardSet.getFlashcards();
        flashcards.add(newFlashcard);
        flashcardSet.setFlashcards(flashcards);

        flashcardSetService.update(flashcardSet);
        assertThat(flashcardSet.getFlashcards().size(), equalTo(3));
    }

    @Test
    public void testDelete(){
        flashcardSetService.delete(1L);
    }
}
