package dyplomowa.fiszki.Fiszki.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "test_score")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = TestScore.class)
public class TestScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //Na razie bez dwukierunkowego związku, bo nwm czy będzie potrzebny

    @ManyToOne
    @JoinColumn(name = "id_set_subscription", nullable = false)
    @JsonIdentityReference
    private SetSubscription subscription;

    @Column(name = "score")
    private int score;

    @Column(name = "test_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date testDate;

    public TestScore() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public SetSubscription getSubscription() {
        return subscription;
    }

    public void setSubscription(SetSubscription subscription) {
        this.subscription = subscription;
    }

    public Date getTestDate() {
        return testDate;
    }

    public void setTestDate(Date testDate) {
        this.testDate = testDate;
    }
}
