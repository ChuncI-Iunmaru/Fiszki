package dyplomowa.fiszki.Fiszki.service;

import dyplomowa.fiszki.Fiszki.model.entity.FlashcardSet;
import dyplomowa.fiszki.Fiszki.model.entity.SetSubscription;
import dyplomowa.fiszki.Fiszki.model.entity.User;

import java.util.List;

public interface SetSubscriptionService {

    SetSubscription save(SetSubscription subscription);
    List<SetSubscription> findAll();
    void delete(long id);
    SetSubscription update(SetSubscription newSubscription);
    //TODO findById i inne jak potrzeba
    SetSubscription findById(long id);
    List<SetSubscription> findByUser(User user);
    List<SetSubscription> findBySet(FlashcardSet set);
    void resetProgress(long id);
}
