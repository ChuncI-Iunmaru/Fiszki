package dyplomowa.fiszki.Fiszki.service.impl;

import dyplomowa.fiszki.Fiszki.dao.FlashcardDAO;
import dyplomowa.fiszki.Fiszki.model.entity.Flashcard;
import dyplomowa.fiszki.Fiszki.model.dto.FlashcardDTO;
import dyplomowa.fiszki.Fiszki.model.entity.User;
import dyplomowa.fiszki.Fiszki.service.FlashcardService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service(value = "flashcardService")
public class FlashcardServiceImpl implements FlashcardService {

    @Autowired
    private FlashcardDAO flashcardDAO;

    @Autowired
    private UserServiceImpl userService;

    @Override
    public Flashcard save(FlashcardDTO flashcard) {
        Flashcard newFlashcard = new Flashcard();
        newFlashcard.setFrontText(flashcard.getFrontText());
        newFlashcard.setBackText(flashcard.getBackText());
        newFlashcard.setExtraText(flashcard.getExtraText());
        newFlashcard.setTags(flashcard.getTags());
        newFlashcard.setUser(userService.findById(flashcard.getUserId()));
        return flashcardDAO.save(newFlashcard);
    }

    @Override
    public List<Flashcard> findAll() {
        List<Flashcard> flashcardList = new ArrayList<>();
        flashcardDAO.findAll().iterator().forEachRemaining(flashcardList::add);
        return flashcardList;
    }

    //TODO metody poniżej: może zwracać List<FlashcardDTO>, bo jest wystawione
    @Override
    public List<Flashcard> findByUser(User user) {
        List<Flashcard> flashcardList = new ArrayList<>();
        flashcardDAO.findAllByUser(user).iterator().forEachRemaining(flashcardList::add);
        return flashcardList;
    }

    @Override
    public List<Flashcard> findByUserAndTags(User user, List<String> tags) {
        List<Flashcard> flashcardList = new ArrayList<>();
        flashcardDAO.findDistinctByUserAndTagsIn(user, tags).iterator().forEachRemaining(flashcardList::add);
        return flashcardList;
    }

    @Override
    public Flashcard findById(long id) {
        Optional<Flashcard> flashcard = flashcardDAO.findById(id);
        return flashcard.orElse(null);
    }

    @Override
    public FlashcardDTO update(FlashcardDTO flashcardDTO) {
        Flashcard flashcard = findById(flashcardDTO.getId());
        if (flashcard != null) {
            BeanUtils.copyProperties(flashcardDTO, flashcard);
            flashcardDAO.save(flashcard);
        }
        return flashcardDTO;
    }

    @Override
    public void delete(long id) {
        flashcardDAO.deleteById(id);
    }
}
