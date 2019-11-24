package dyplomowa.fiszki.Fiszki.controller;

import dyplomowa.fiszki.Fiszki.model.ApiResponse;
import dyplomowa.fiszki.Fiszki.model.entity.FlashcardSet;
import dyplomowa.fiszki.Fiszki.service.FlashcardSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/flashcard_set")
public class FlashcardSetController {

    @Autowired
    FlashcardSetService flashcardSetService;

    @PostMapping
    public ApiResponse<FlashcardSet> saveFlashcardSet(@RequestBody FlashcardSet flashcardSet){
        return new ApiResponse<>(HttpStatus.OK.value(), "Flashcard set saved successfully.", flashcardSetService.save(flashcardSet));
    }

    @GetMapping
    public ApiResponse<FlashcardSet> getAllForToken(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return new ApiResponse<>(HttpStatus.OK.value(), "FlashcardSets for user " + username, flashcardSetService.findAllByCreatorUsername(username));
    }

}
