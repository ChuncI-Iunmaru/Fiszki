package dyplomowa.fiszki.Fiszki.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import dyplomowa.fiszki.Fiszki.utils.IntegerListConverter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "set_subscription")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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
    @Convert(converter = IntegerListConverter.class)
    private List<Integer> learnedFlashcards;

    @OneToMany
    @JoinColumn(name = "id_set_subscription")
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

    public List<Integer> getLearnedFlashcards() {
        return learnedFlashcards;
    }

    public void setLearnedFlashcards(List<Integer> learnedFlashcards) {
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
}
