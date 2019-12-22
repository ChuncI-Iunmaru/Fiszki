package dyplomowa.fiszki.Fiszki.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "flashcard_set")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = FlashcardSet.class)
public class FlashcardSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    @JsonIdentityReference
    private User creator;

    @Column(name = "title")
    private String title;

    @Column(name = "daily_amount")
    private int dailyAmount;

    @Column(name = "test_questions_num")
    private int testQuestionsNum;

    @Column(name = "test_time")
    private int testTime;

    @Column(name = "test_attempts")
    private int testAttempts;

    @Column(name = "test_accessible")
    private String testAccessible;

    @ManyToMany
    @JoinTable(name = "flashcard_to_flashcard_set",
            joinColumns = {@JoinColumn(name = "id_flashcard_set", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "id_flashcard", referencedColumnName = "id")})
    @JsonIdentityReference
    private Set<Flashcard> flashcards;

    @Column(name = "password")
    private String password;

    public FlashcardSet() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDailyAmount() {
        return dailyAmount;
    }

    public void setDailyAmount(int dailyAmount) {
        this.dailyAmount = dailyAmount;
    }

    public int getTestQuestionsNum() {
        return testQuestionsNum;
    }

    public void setTestQuestionsNum(int testQuestionsNum) {
        this.testQuestionsNum = testQuestionsNum;
    }

    public int getTestTime() {
        return testTime;
    }

    public void setTestTime(int testTime) {
        this.testTime = testTime;
    }

    public int getTestAttempts() {
        return testAttempts;
    }

    public void setTestAttempts(int testAttempts) {
        this.testAttempts = testAttempts;
    }

    public String getTestAccessible() {
        return testAccessible;
    }

    public void setTestAccessible(String testAccessible) {
        this.testAccessible = testAccessible;
    }

    public Set<Flashcard> getFlashcards() {
        return flashcards;
    }

    public void setFlashcards(Set<Flashcard> flashcards) {
        this.flashcards = flashcards;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
