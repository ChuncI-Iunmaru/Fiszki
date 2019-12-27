package dyplomowa.fiszki.Fiszki.model.study;

import dyplomowa.fiszki.Fiszki.model.entity.Flashcard;

import java.util.List;

public class StudySession {

    private List<Flashcard> flashcards;
    private List<TestQuestion> firstBox;
    private List<TestQuestion> secondBox;
    private List<TestQuestion> thirdBox;

    public StudySession() {
    }

    public StudySession(List<Flashcard> flashcards, List<TestQuestion> firstBox, List<TestQuestion> secondBox, List<TestQuestion> thirdBox) {
        this.flashcards = flashcards;
        this.firstBox = firstBox;
        this.secondBox = secondBox;
        this.thirdBox = thirdBox;
    }

    public List<Flashcard> getFlashcards() {
        return flashcards;
    }

    public void setFlashcards(List<Flashcard> flashcards) {
        this.flashcards = flashcards;
    }

    public List<TestQuestion> getFirstBox() {
        return firstBox;
    }

    public void setFirstBox(List<TestQuestion> firstBox) {
        this.firstBox = firstBox;
    }

    public List<TestQuestion> getSecondBox() {
        return secondBox;
    }

    public void setSecondBox(List<TestQuestion> secondBox) {
        this.secondBox = secondBox;
    }

    public List<TestQuestion> getThirdBox() {
        return thirdBox;
    }

    public void setThirdBox(List<TestQuestion> thirdBox) {
        this.thirdBox = thirdBox;
    }
}
