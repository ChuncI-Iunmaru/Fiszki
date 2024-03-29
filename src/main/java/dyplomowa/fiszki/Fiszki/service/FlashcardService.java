package dyplomowa.fiszki.Fiszki.service;

import dyplomowa.fiszki.Fiszki.model.entity.Flashcard;
import dyplomowa.fiszki.Fiszki.model.dto.FlashcardDTO;
import dyplomowa.fiszki.Fiszki.model.entity.User;

import java.util.List;

public interface FlashcardService {

    Flashcard save(FlashcardDTO flashcard);
    List<Flashcard> findAll();
    //TODO reszta metod
    void delete(long id);
    FlashcardDTO update(FlashcardDTO flashcardDTO);
    List<Flashcard> findByUser(User user);
    Flashcard findById(long id);
    List<Flashcard> findByUserAndTags(User user, List<String> tags);
}
