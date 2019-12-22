package dyplomowa.fiszki.Fiszki.controller;

import dyplomowa.fiszki.Fiszki.model.ApiResponse;
import dyplomowa.fiszki.Fiszki.model.entity.SetSubscription;
import dyplomowa.fiszki.Fiszki.model.entity.TestScore;
import dyplomowa.fiszki.Fiszki.service.SetSubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/subscription")
public class SetSubscriptionController {

    @Autowired
    SetSubscriptionService subscriptionService;

    @GetMapping
    public ApiResponse<List<SetSubscription>> getAll(){
        return new ApiResponse<>(HttpStatus.OK.value(), "All subscriptions: ", subscriptionService.findAll());
    }
    
    @GetMapping("/{id}")
    public ApiResponse<SetSubscription> getById(@PathVariable long id) {
        SetSubscription result = subscriptionService.findById(id);
        if (result == null) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "No subscription with id: " + id, null);
        } else return new ApiResponse<>(HttpStatus.OK.value(), "Subscription with id: " + id, result);
    }
    
    @PostMapping
    public ApiResponse<SetSubscription> save(@RequestBody SetSubscription subscription){
        return new ApiResponse<>(HttpStatus.OK.value(), "Subscription saved successfully", subscriptionService.save(subscription));
    }

    @PutMapping("/{id}")
    public ApiResponse<SetSubscription> update(@RequestBody SetSubscription subscriptionSet){
        return new ApiResponse<>(HttpStatus.OK.value(), "Subscription updated successfully", subscriptionService.update(subscriptionSet));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable long id){
        subscriptionService.delete(id);
        return new ApiResponse<>(HttpStatus.OK.value(), "Subscription deleted successfully", null);
    }

    @GetMapping("/scores/{id}")
    public ApiResponse<List<TestScore>> getTestScoresForId(@PathVariable long id){
        SetSubscription result = subscriptionService.findById(id);
        if (result == null) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "No subscription with id: " + id, Collections.emptyList());
        } else return new ApiResponse<>(HttpStatus.OK.value(), "Test scores for subscription with id: " + id, result.getScores());
    }
}