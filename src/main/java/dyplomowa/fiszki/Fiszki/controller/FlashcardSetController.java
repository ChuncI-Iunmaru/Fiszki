package dyplomowa.fiszki.Fiszki.controller;

import dyplomowa.fiszki.Fiszki.model.ApiResponse;
import dyplomowa.fiszki.Fiszki.model.entity.Flashcard;
import dyplomowa.fiszki.Fiszki.model.entity.FlashcardSet;
import dyplomowa.fiszki.Fiszki.model.entity.SetSubscription;
import dyplomowa.fiszki.Fiszki.service.FlashcardSetService;
import dyplomowa.fiszki.Fiszki.service.SetSubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/flashcard_set")
public class FlashcardSetController {

    @Autowired
    FlashcardSetService flashcardSetService;

    @Autowired
    SetSubscriptionService subscriptionService;

    @PostMapping
    public ApiResponse<FlashcardSet> saveFlashcardSet(@RequestBody FlashcardSet flashcardSet){
        return new ApiResponse<>(HttpStatus.OK.value(), "Flashcard set saved successfully.", flashcardSetService.save(flashcardSet));
    }

    @GetMapping
    public ApiResponse<List<FlashcardSet>> getAllForToken(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return new ApiResponse<>(HttpStatus.OK.value(), "FlashcardSets for user " + username, flashcardSetService.findAllByCreatorUsername(username));
    }

    @GetMapping("/{id}")
    public ApiResponse<FlashcardSet> getById(@PathVariable long id){
        return new ApiResponse<>(HttpStatus.OK.value(), "FlashcardSet fetched successfully", flashcardSetService.findById(id));
    }

    @PutMapping("/{id}")
    public ApiResponse<FlashcardSet> update(@RequestBody FlashcardSet flashcardSet){
        FlashcardSet result = flashcardSetService.update(flashcardSet);
        List<SetSubscription> subscriptions= subscriptionService.findBySet(result);
        for (SetSubscription s : subscriptions) {
            subscriptionService.resetProgress(s.getId());
        }
        return new ApiResponse<>(HttpStatus.OK.value(), "FlashcardSet updated successfully", result);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable long id){
        flashcardSetService.delete(id);
        return new ApiResponse<>(HttpStatus.OK.value(), "FlashcardSet deleted successfully", null);
    }

    @GetMapping("/my_sets/{title}")
    public ApiResponse<List<FlashcardSet>> getAllByTitleForToken(@PathVariable String title){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return new ApiResponse<>(HttpStatus.OK.value(),
                "FlashcardSets for user " + username + " and title " + title,
                flashcardSetService.findAllByTitleForUsername(title, username));
    }

    @GetMapping("/all_sets/{title}")
    public ApiResponse<List<FlashcardSet>> getAllByTitle(@PathVariable String title){
        return new ApiResponse<>(HttpStatus.OK.value(),
                "FlashcardSets for title " + title,
                flashcardSetService.findAllByTitle(title));
    }

    @GetMapping("/all_sets")
    public ApiResponse<List<FlashcardSet>> getAll(){
        return new ApiResponse<>(HttpStatus.OK.value(),
                "FlashcardSets ",
                flashcardSetService.findAll());
    }

    @GetMapping("/flashcards/{id}")
    public ApiResponse<List<Flashcard>> getAllFlashcardsFromSet(@PathVariable long id){
        FlashcardSet flashcardSet = flashcardSetService.findById(id);
        if (flashcardSet == null) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "No set with id: " + id, Collections.emptyList());
        } else return new ApiResponse<>(HttpStatus.OK.value(), "Flashcards for set with id: " + id, flashcardSet.getFlashcards());
    }
}
