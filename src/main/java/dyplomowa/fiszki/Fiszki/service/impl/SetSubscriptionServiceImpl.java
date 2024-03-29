package dyplomowa.fiszki.Fiszki.service.impl;

import dyplomowa.fiszki.Fiszki.dao.SetSubscriptionDAO;
import dyplomowa.fiszki.Fiszki.model.entity.FlashcardSet;
import dyplomowa.fiszki.Fiszki.model.entity.SetSubscription;
import dyplomowa.fiszki.Fiszki.model.entity.User;
import dyplomowa.fiszki.Fiszki.service.SetSubscriptionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service(value = "setSubscriptionService")
public class SetSubscriptionServiceImpl implements SetSubscriptionService {

    @Autowired
    private SetSubscriptionDAO subscriptionDAO;

    @Override
    public SetSubscription save(SetSubscription subscription) {
        return subscriptionDAO.save(subscription);
    }

    @Override
    public List<SetSubscription> findAll() {
        List<SetSubscription> subscriptions = new ArrayList<>();
        subscriptionDAO.findAll().iterator().forEachRemaining(subscriptions::add);
        return subscriptions;
    }

    @Override
    public List<SetSubscription> findByUser(User user){
        List<SetSubscription> subscriptions = new ArrayList<>();
        subscriptionDAO.findAllByUser(user).iterator().forEachRemaining(subscriptions::add);
        return subscriptions;
    }

    @Override
    public void delete(long id) {
        subscriptionDAO.deleteById(id);
    }

    @Override
    public SetSubscription update(SetSubscription newSubscription) {
        SetSubscription subscription = findById(newSubscription.getId());
        if (subscription != null) {
            BeanUtils.copyProperties(newSubscription, subscription);
            subscriptionDAO.save(subscription);
        }
        return newSubscription;
    }

    @Override
    public SetSubscription findById(long id) {
        Optional<SetSubscription> subscription = subscriptionDAO.findById(id);
        return subscription.orElse(null);
    }

    @Override
    public List<SetSubscription> findBySet(FlashcardSet set) {
        List<SetSubscription> subscriptions = new ArrayList<>();
        subscriptionDAO.findAllByFlashcardSet(set).iterator().forEachRemaining(subscriptions::add);
        return subscriptions;
    }

    @Override
    public void resetProgress(long id) {
        SetSubscription subscription = findById(id);
        if (subscription != null) {
            subscription.setScores(Collections.emptyList());
            subscription.setLearnedFlashcards(Collections.emptyList());
            subscription.setSecondBox(Collections.emptyList());
            subscriptionDAO.save(subscription);
        }
    }
}
