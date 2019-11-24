package dyplomowa.fiszki.Fiszki.dao;

import dyplomowa.fiszki.Fiszki.model.entity.FlashcardSet;
import dyplomowa.fiszki.Fiszki.model.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlashcardSetDAO extends CrudRepository<FlashcardSet, Long> {
    List<FlashcardSet> findAllByCreator(User creator);
}
