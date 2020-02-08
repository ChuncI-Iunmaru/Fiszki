package dyplomowa.fiszki.Fiszki.controller;

import dyplomowa.fiszki.Fiszki.model.ApiResponse;
import dyplomowa.fiszki.Fiszki.model.entity.Flashcard;
import dyplomowa.fiszki.Fiszki.model.entity.FlashcardSet;
import dyplomowa.fiszki.Fiszki.model.entity.SetSubscription;
import dyplomowa.fiszki.Fiszki.model.entity.TestScore;
import dyplomowa.fiszki.Fiszki.model.study.FinalTest;
import dyplomowa.fiszki.Fiszki.model.study.StudySession;
import dyplomowa.fiszki.Fiszki.model.study.TestQuestion;
import dyplomowa.fiszki.Fiszki.model.study.TestResults;
import dyplomowa.fiszki.Fiszki.service.FlashcardSetService;
import dyplomowa.fiszki.Fiszki.service.SetSubscriptionService;
import dyplomowa.fiszki.Fiszki.service.TestScoreService;
import org.aspectj.weaver.ast.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/study")
public class StudyController {

    @Autowired
    private SetSubscriptionService subscriptionService;

    @Autowired
    private FlashcardSetService setService;

    @Autowired
    private TestScoreService scoreService;

    // TODO Mało optymalne rozwiązanie
    private List<String> getAnswerList(List<String> allAnswers, String correct){
        List<String> incorrectAnswers = allAnswers.stream().filter(s -> !(s.compareTo(correct) == 0)).collect(Collectors.toList());
        int size = Math.min(allAnswers.size(), 3);
        Collections.shuffle(incorrectAnswers);
        List<String> result = incorrectAnswers.subList(0, size);
        result.add(correct);
        Collections.shuffle(result);
        return result;
    }

    private TestQuestion createQuestion(List<String> allAnswers, Flashcard f) {
        TestQuestion question = new TestQuestion();
        question.setFlashcardId(f.getId());
        question.setCorrectAnswer(f.getBackText());
        question.setQuestion(f.getFrontText());
        question.setAnswers(getAnswerList(allAnswers, f.getBackText()));
        question.setGivenAnswer("");
        return question;
    }

    @GetMapping("/for_sub/{id}")
    public ApiResponse<StudySession> getStudySession(@PathVariable long id) {
        // Get subscription
        SetSubscription subscription = subscriptionService.findById(id);
        if (subscription == null) return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "No subscription with id: " + id, null);
        // Assign set from it to variable
        FlashcardSet set = setService.findById(subscription.getFlashcardSet().getId());
        Set<Flashcard> allFlashcards = set.getFlashcards();
        if (set == null) return new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error retrieving set", null);
        // Get lists from it
        List<Long> secondBox = subscription.getSecondBox();
        List<Long> thirdBox = subscription.getLearnedFlashcards();

        // Create first box and add to flashcards
        // For ease create list of unseen flashcards first
        List<Flashcard> newFlashcards = allFlashcards.stream().filter(f -> !secondBox.contains(f.getId()) && !thirdBox.contains(f.getId())).collect(Collectors.toList());
        // Create list of all possible answers
        List<String> answers = allFlashcards.stream().map(Flashcard::getBackText).collect(Collectors.toList());

        List<Flashcard> studyFlashcards = new ArrayList<>();
        List<TestQuestion> studyFirstBox = new ArrayList<>();
        int size = Math.min(set.getDailyAmount(), newFlashcards.size());
        for (int i = 0; i < size; i++) {
            studyFlashcards.add(newFlashcards.get(i));
            studyFirstBox.add(createQuestion(answers, newFlashcards.get(i)));
        }

        // Create second box and add to flashcards
        List<Flashcard> secondBoxFlashcards = allFlashcards.stream().filter(f -> secondBox.contains(f.getId())).collect(Collectors.toList());
        List<TestQuestion> studySecondBox = new ArrayList<>();
        size = Math.min(set.getDailyAmount(), secondBoxFlashcards.size());
        for (int i = 0; i < size; i++) {
            studyFlashcards.add(secondBoxFlashcards.get(i));
            studySecondBox.add(createQuestion(answers, secondBoxFlashcards.get(i)));
        }

        // Create third box
        List<Flashcard> thirdBoxFlashcards = allFlashcards.stream().filter(f -> thirdBox.contains(f.getId())).collect(Collectors.toList());
        List<TestQuestion> studyThirdBox = new ArrayList<>();
        if (thirdBoxFlashcards.size() != 0) {
            Random random = new Random(666);
            studyThirdBox.add(createQuestion(answers, thirdBoxFlashcards.get(random.nextInt(thirdBoxFlashcards.size()))));

        }

        // Create new StudySession
        StudySession session = new StudySession();
        session.setFlashcards(studyFlashcards);

        // Assign boxes
        session.setFirstBox(studyFirstBox);
        session.setSecondBox(studySecondBox);
        session.setThirdBox(studyThirdBox);

        return new ApiResponse<>(HttpStatus.OK.value(), "Study session for id: " + id, session);
    }

    @PostMapping("/{sub_id}/check_session")
    public ApiResponse<TestResults> checkStudySession(@PathVariable long sub_id, @RequestBody StudySession session) {
        // Get subscription
        SetSubscription subscription = subscriptionService.findById(sub_id);
        if (subscription == null) return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "No subscription with id: " + sub_id, null);
        // Assign set from it to variable
        FlashcardSet set = setService.findById(subscription.getFlashcardSet().getId());
        Set<Flashcard> allFlashcards = set.getFlashcards();

        // Get boxes from subscription
        List<Long> secondBox = new ArrayList<>(subscription.getSecondBox());
        List<Long> thirdBox = new ArrayList<>(subscription.getLearnedFlashcards());

        // Check first box questions, if correct, move flashcard to second box
        // Set question score appropriately
        List<TestQuestion> firstBoxQuestions = new ArrayList<>(session.getFirstBox());
        for (TestQuestion q : firstBoxQuestions) {
            if (q.getGivenAnswer().compareTo(q.getCorrectAnswer()) == 0) {
                secondBox.add(q.getFlashcardId());
                q.setCorrect(true);
            }
        }


        // Check second box questions, if correct move flashcard to third box, else remove it from second box
        // Set question score appropriately
        List<TestQuestion> secondBoxQuestions = new ArrayList<>(session.getSecondBox());
        for (TestQuestion q : secondBoxQuestions) {
            if (q.getGivenAnswer().compareTo(q.getCorrectAnswer()) == 0) {
                thirdBox.add(q.getFlashcardId());
                q.setCorrect(true);
            } else secondBox.remove(q.getFlashcardId());
        }

        // Check third box questions, if incorrect move flashcard to first box
        // Set question score appropriately
        List<TestQuestion> thirdBoxQuestions = new ArrayList<>(session.getThirdBox());
        for (TestQuestion q : thirdBoxQuestions) {
            if (q.getGivenAnswer().compareTo(q.getCorrectAnswer()) != 0) {
                thirdBox.remove(q.getFlashcardId());
            } else q.setCorrect(true);
        }

        // Update subscription
         subscription.setSecondBox(secondBox);
         subscription.setLearnedFlashcards(thirdBox);
         subscription.setSubscriptionDate(new Date());
         subscriptionService.update(subscription);


        List<TestQuestion> allQuestions = Stream.concat(Stream.concat(firstBoxQuestions.stream(), secondBoxQuestions.stream()), thirdBoxQuestions.stream()).collect(Collectors.toList());
        long correctAnswers = allQuestions.stream().filter(TestQuestion::isCorrect).count();

        // Create test result
        TestResults testResults = new TestResults();
        testResults.setQuestions(allQuestions);

        testResults.setScore((int) Math.floor(((double)correctAnswers/allQuestions.size())*100));

        return new ApiResponse<>(HttpStatus.OK.value(), "Session test results: " + correctAnswers + ", " + allQuestions.size() + ", " + (int) Math.floor(((double)correctAnswers/allQuestions.size())*100), testResults);
    }

    @GetMapping("/test_for_sub/{id}")
    public ApiResponse<FinalTest> getTestForSubscription(@PathVariable long id){
        SetSubscription subscription = subscriptionService.findById(id);
        if (subscription == null) return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "No subscription with id: " + id, null);
        // Assign set from it to variable
        FlashcardSet set = setService.findById(subscription.getFlashcardSet().getId());
        if (set == null) return new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error retrieving set", null);
        //Get all flashcards
        List<Flashcard> allFlashcards = new ArrayList<>(set.getFlashcards());
        List<String> answers = allFlashcards.stream().map(Flashcard::getBackText).collect(Collectors.toList());
        //Pick random testNum from them
        Collections.shuffle(allFlashcards);
        List<Flashcard> forTest = allFlashcards.subList(0, set.getTestQuestionsNum());
        //Create result question List
        List<TestQuestion> questions = new ArrayList<>();
        //Create and add questions to them
        for (Flashcard f:forTest) {
            questions.add(createQuestion(answers, f));
        }
        //Create and set Final test object
        FinalTest result = new FinalTest();
        result.setTime(set.getTestTime());
        result.setQuestions(questions);
        //Return final set
        return new ApiResponse<>(HttpStatus.OK.value(), "Test for subscription id: " + id, result);
    }

    @PostMapping("/{id}/check_test")
    public ApiResponse<TestResults> getTestResults(@PathVariable long id, @RequestBody FinalTest test) {
        // Get subscription
        SetSubscription subscription = subscriptionService.findById(id);
        if (subscription == null) return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "No subscription with id: " + id, null);

        //Get question list
        List<TestQuestion> questions = new ArrayList<>(test.getQuestions());
        //Check all answers
        for (TestQuestion q : questions) {
            if (q.getGivenAnswer().compareTo(q.getCorrectAnswer()) == 0) {
                q.setCorrect(true);
            }
        }

        long correctAnswers = questions.stream().filter(TestQuestion::isCorrect).count();

        TestResults testResults = new TestResults();
        testResults.setQuestions(questions);

        int testScore = (int) Math.floor(((double)correctAnswers/questions.size())*100);
        testResults.setScore(testScore);

        // Update subscription
        List<TestScore> scores = new ArrayList<>(subscription.getScores());
        TestScore newScore = new TestScore();
        newScore.setScore(testScore);
        newScore.setSubscription(subscription);
        newScore.setTestDate(new Date());
        TestScore saved = scoreService.save(newScore);
        scores.add(saved);
        subscription.setScores(scores);
        subscriptionService.update(subscription);

        return new ApiResponse<>(HttpStatus.OK.value(), "Session test results: " + correctAnswers + ", " + questions.size() + ", " + (int) Math.floor(((double)correctAnswers/questions.size())*100), testResults);

    };
}
