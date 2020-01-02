package dyplomowa.fiszki.Fiszki.service.impl;

import dyplomowa.fiszki.Fiszki.dao.TestScoreDAO;
import dyplomowa.fiszki.Fiszki.model.entity.TestScore;
import dyplomowa.fiszki.Fiszki.service.TestScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service(value = "testScoreService")
public class TestScoreServiceImpl implements TestScoreService {
    @Autowired
    TestScoreDAO testScoreDAO;

    @Override
    public TestScore save(TestScore score) {
        return testScoreDAO.save(score);
    }

    @Override
    public TestScore getById(long id) {
        Optional<TestScore> score = testScoreDAO.findById(id);
        return score.orElse(null);
    }
}
