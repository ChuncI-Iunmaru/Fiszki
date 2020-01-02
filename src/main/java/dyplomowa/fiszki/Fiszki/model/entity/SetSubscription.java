package dyplomowa.fiszki.Fiszki.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import dyplomowa.fiszki.Fiszki.utils.LongListConverter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "set_subscription")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = SetSubscription.class)
public class SetSubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "id_user")
    @JsonIdentityReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_flashcard_set")
    @JsonIdentityReference
    private FlashcardSet flashcardSet;

    @Column(name = "learned_flashcard_list")
    @Convert(converter = LongListConverter.class)
    private List<Long> learnedFlashcards;

    @Column(name = "second_box")
    @Convert(converter = LongListConverter.class)
    private List<Long> secondBox;

    @OneToMany(mappedBy = "subscription")
    @JsonIdentityReference
    private List<TestScore> scores;

    @Column(name = "subscription_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date subscriptionDate;

    public SetSubscription() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public FlashcardSet getFlashcardSet() {
        return flashcardSet;
    }

    public void setFlashcardSet(FlashcardSet flashcardSet) {
        this.flashcardSet = flashcardSet;
    }

    public List<Long> getLearnedFlashcards() {
        return learnedFlashcards;
    }

    public void setLearnedFlashcards(List<Long> learnedFlashcards) {
        this.learnedFlashcards = learnedFlashcards;
    }

    public List<TestScore> getScores() {
        return scores;
    }

    public void setScores(List<TestScore> scores) {
        this.scores = scores;
    }

    public Date getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(Date subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    public List<Long> getSecondBox() {
        return secondBox;
    }

    public void setSecondBox(List<Long> secondBox) {
        this.secondBox = secondBox;
    }
}
