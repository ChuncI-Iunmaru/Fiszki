package dyplomowa.fiszki.Fiszki.dao;

import dyplomowa.fiszki.Fiszki.model.entity.Flashcard;
import dyplomowa.fiszki.Fiszki.model.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlashcardDAO extends CrudRepository<Flashcard, Long> {

    List<Flashcard> findAllByUser(User user);
    // TODO Wyszukiwanie po tagach
    //To zwraca podwójnie? Czemu?
    //Na razie naprawione (findDistinct nie findAll)
    List<Flashcard> findDistinctByUserAndTagsIn(User user, List<String> tags);
}
