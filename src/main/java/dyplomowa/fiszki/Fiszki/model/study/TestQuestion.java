package dyplomowa.fiszki.Fiszki.model.study;

import java.util.List;

public class TestQuestion {
    private long flashcardId;
    private String question;
    private List<String> answers;
    private String correctAnswer;
    private String givenAnswer;
    private boolean correct;

    public TestQuestion() {
    }

    public TestQuestion(long flashcardId, String question, List<String> answers, String correctAnswer, String givenAnswer) {
        this.flashcardId = flashcardId;
        this.question = question;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
        this.givenAnswer = givenAnswer;
        this.correct = false;
    }

    public long getFlashcardId() {
        return flashcardId;
    }

    public void setFlashcardId(long flashcardId) {
        this.flashcardId = flashcardId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getGivenAnswer() {
        return givenAnswer;
    }

    public void setGivenAnswer(String givenAnswer) {
        this.givenAnswer = givenAnswer;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }
}
