package dyplomowa.fiszki.Fiszki.service.impl;

import dyplomowa.fiszki.Fiszki.dao.FlashcardSetDAO;
import dyplomowa.fiszki.Fiszki.model.entity.FlashcardSet;
import dyplomowa.fiszki.Fiszki.service.FlashcardSetService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service(value = "flashcardSetService")
public class FlashcardSetServiceImpl implements FlashcardSetService {

    @Autowired
    private FlashcardSetDAO flashcardSetDAO;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    @Override
    public FlashcardSet save(FlashcardSet set) {
        FlashcardSet newSet = new FlashcardSet();
        BeanUtils.copyProperties(set, newSet, "password");
        newSet.setPassword(bcryptEncoder.encode(set.getPassword()));
        flashcardSetDAO.save(newSet);

        return newSet;
    }

    @Override
    public FlashcardSet update(FlashcardSet newSet) {
        FlashcardSet set = findById(newSet.getId());
        if (set != null) {
            BeanUtils.copyProperties(newSet, set, "password");
            flashcardSetDAO.save(set);
        }
        return newSet;
    }

    @Override
    public List<FlashcardSet> findAll() {
        List<FlashcardSet> flashcardSets = new ArrayList<>();
        flashcardSetDAO.findAll().iterator().forEachRemaining(flashcardSets::add);
        return flashcardSets;
    }

    @Override
    public FlashcardSet findById(long id) {
        Optional<FlashcardSet> optional = flashcardSetDAO.findById(id);
        return optional.orElse(null);
    }

    @Override
    public void delete(long id) {
        flashcardSetDAO.deleteById(id);
    }
}
