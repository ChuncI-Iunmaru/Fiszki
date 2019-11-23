package dyplomowa.fiszki.Fiszki.dao;

import dyplomowa.fiszki.Fiszki.model.entity.FlashcardSet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlashcardSetDAO extends CrudRepository<FlashcardSet, Long> {
}
