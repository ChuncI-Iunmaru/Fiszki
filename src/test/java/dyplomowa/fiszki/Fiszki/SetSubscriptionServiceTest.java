package dyplomowa.fiszki.Fiszki;

import dyplomowa.fiszki.Fiszki.model.entity.FlashcardSet;
import dyplomowa.fiszki.Fiszki.model.entity.SetSubscription;
import dyplomowa.fiszki.Fiszki.model.entity.User;
import dyplomowa.fiszki.Fiszki.service.FlashcardSetService;
import dyplomowa.fiszki.Fiszki.service.SetSubscriptionService;
import dyplomowa.fiszki.Fiszki.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
//Zakładam że domyślny element o id = 1 jest już w bazie
public class SetSubscriptionServiceTest {

    @Autowired
    SetSubscriptionService subscriptionService;

    @Autowired
    UserService userService;

    @Autowired
    FlashcardSetService setService;

    @Test
    public void testSave(){
        SetSubscription subscription = new SetSubscription();

        User user = userService.findOne("testowy");
        assertThat(user, notNullValue());

        FlashcardSet flashcardSet = setService.findById(2);
        assertThat(flashcardSet, notNullValue());


        subscription.setUser(user);
        subscription.setFlashcardSet(flashcardSet);
        subscription.setLearnedFlashcards(new ArrayList<>(Arrays.asList(0)));
        subscription.setScores(new ArrayList<>(Collections.emptyList()));
        subscription.setSubscriptionDate(new Date());

        SetSubscription result = subscriptionService.save(subscription);
    }

    @Test
    public void testFindAll(){
        List<SetSubscription> subscriptions = subscriptionService.findAll();
        assertThat(subscriptions.size(), greaterThanOrEqualTo(1));
    }

    @Test
    public void testFindById(){
        SetSubscription result = subscriptionService.findById(1L);
        assertThat(result, notNullValue());
    }

    @Test
    public void testUpdate(){
        SetSubscription subscription = subscriptionService.findById(1L);
        assertThat(subscription, notNullValue());
        subscription.setLearnedFlashcards(new ArrayList<>(Arrays.asList(1,2,3,4,5)));
        subscriptionService.update(subscription);

        SetSubscription result = subscriptionService.findById(1L);
        assertThat(result.getLearnedFlashcards().size(), equalTo(5));
    }

    @Test
    //Usuwa ostatni element na liście, używać tylko w sekwencji testów po save
    public void testDelete(){
        List<SetSubscription> subscriptions = subscriptionService.findAll();
        assertThat(subscriptions.size(), greaterThanOrEqualTo(2));
        subscriptionService.delete(subscriptions.get(subscriptions.size()-1).getId());
        List<SetSubscription> result = subscriptionService.findAll();
        assertThat(result.size(), equalTo(subscriptions.size()-1));
    }
}
