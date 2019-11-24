package dyplomowa.fiszki.Fiszki.service;

import dyplomowa.fiszki.Fiszki.model.entity.FlashcardSet;

import java.util.List;

public interface FlashcardSetService {

    FlashcardSet save(FlashcardSet set);
    FlashcardSet update(FlashcardSet newSet);
    List<FlashcardSet> findAll();
    FlashcardSet findById(long id);
    void delete(long id);

    //TODO Get by user
}
