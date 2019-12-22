package dyplomowa.fiszki.Fiszki.controller;

import dyplomowa.fiszki.Fiszki.config.JWTTokenUtil;
import dyplomowa.fiszki.Fiszki.model.*;
import dyplomowa.fiszki.Fiszki.model.dto.UserDto;
import dyplomowa.fiszki.Fiszki.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import dyplomowa.fiszki.Fiszki.service.UserService;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/token")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/generate-token", method = RequestMethod.POST)
    public ApiResponse<AuthToken> register(@RequestBody LoginUser loginUser) throws AuthenticationException {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));
            final User user = userService.findOne(loginUser.getUsername());
            final String token = jwtTokenUtil.generateToken(user);
            return new ApiResponse<>(200, "success",new AuthToken(token, user.getUsername()));
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid credentials", e);
        }
    }

    @RequestMapping(value="/signup", method = RequestMethod.POST)
    public ApiResponse<AuthToken> saveUser(@RequestBody UserDto user){
        //Check if username not taken
        if(userService.findOne(user.getUsername()) != null) {
            //return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "User already exists", null);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exists", null);
        }
        final User result =  userService.save(user);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        final String token = jwtTokenUtil.generateToken(result);
        return new ApiResponse<>(200, "success",new AuthToken(token, result.getUsername()));
    }
}
