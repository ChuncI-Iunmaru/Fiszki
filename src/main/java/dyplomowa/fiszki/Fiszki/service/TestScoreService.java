package dyplomowa.fiszki.Fiszki.service;

import dyplomowa.fiszki.Fiszki.model.entity.TestScore;

public interface TestScoreService {

    TestScore save(TestScore score);
    TestScore getById(long id);
}
