package dyplomowa.fiszki.Fiszki.dao;

import dyplomowa.fiszki.Fiszki.model.entity.SetSubscription;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SetSubscriptionDAO extends CrudRepository<SetSubscription, Long> {
}
