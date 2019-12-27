package dyplomowa.fiszki.Fiszki.model.study;

import java.util.List;

public class TestResults {
    private List<TestQuestion> questions;
    private int score;

    public TestResults() {
    }

    public TestResults(List<TestQuestion> questions, int score) {
        this.questions = questions;
        this.score = score;
    }

    public List<TestQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(List<TestQuestion> questions) {
        this.questions = questions;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
