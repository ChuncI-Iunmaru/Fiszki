package dyplomowa.fiszki.Fiszki.controller;

import dyplomowa.fiszki.Fiszki.model.ApiResponse;
import dyplomowa.fiszki.Fiszki.model.entity.Flashcard;
import dyplomowa.fiszki.Fiszki.model.dto.FlashcardDTO;
import dyplomowa.fiszki.Fiszki.model.entity.User;
import dyplomowa.fiszki.Fiszki.service.FlashcardService;
import dyplomowa.fiszki.Fiszki.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/flashcards")
public class FlashcardController {

    @Autowired
    private FlashcardService flashcardService;

    @Autowired
    private UserService userService;

    @PostMapping
    //TODO nie powinno być całego usera w odpowiedzi raczej
    public ApiResponse<Flashcard> saveFlashcard(@RequestBody FlashcardDTO flashcard){
        return new ApiResponse<>(HttpStatus.OK.value(), "Flashcard saved successfully.",flashcardService.save(flashcard));
    }

    //Bierze usera z tokena, ale statyczny call
    @GetMapping("/user")
    public ApiResponse<List<Flashcard>> getByTokenUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findOne(username);
        if (user == null) return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "No user found with username " + username + ", possible authorization error.", Collections.emptyList());
            else return new ApiResponse<>(HttpStatus.OK.value(), "Flashcards for username = " + username, flashcardService.findByUser(user));
    }

    @GetMapping("/tags_{tags}")
    public ApiResponse<List<Flashcard>> getByUserIdAndTags(@PathVariable List<String> tags){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findOne(username);
        if (user == null) return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "No user found with username " + username + ", possible authorization error.", Collections.emptyList());
        else return new ApiResponse<>(HttpStatus.OK.value(), "Flashcards for username = " + username + " and tags { " + tags +" }", flashcardService.findByUserAndTags(user, tags));
    }

    @GetMapping("/{id}")
    public ApiResponse<Flashcard> getById(@PathVariable long id){
        return new ApiResponse<>(HttpStatus.OK.value(), "Flashcard fetched successfully", flashcardService.findById(id));
    }

    @PutMapping("/{id}")
    public ApiResponse<FlashcardDTO> update(@RequestBody FlashcardDTO flashcardDTO){
        return new ApiResponse<>(HttpStatus.OK.value(), "Flashcard fetched successfully", flashcardService.update(flashcardDTO));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable long id){
        flashcardService.delete(id);
        return new ApiResponse<>(HttpStatus.OK.value(), "Flashcard deleted successfully", null);
    }
}