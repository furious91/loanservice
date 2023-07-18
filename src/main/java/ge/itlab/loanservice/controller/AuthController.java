package ge.itlab.loanservice.controller;

import ge.itlab.loanservice.model.dto.LoginForm;
import ge.itlab.loanservice.model.dto.UserInfoDto;
import ge.itlab.loanservice.model.dto.UserInfoUserDetails;
import ge.itlab.loanservice.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@DependsOn("securityFilterChain")
public class AuthController {
    private final RememberMeServices rememberMeServices;
    private final UserService userService;

    @PostMapping("/login")
    public CurrentUser login(@Valid @RequestBody LoginForm form, BindingResult bindingResult,
                             HttpServletRequest request, HttpServletResponse response) {

        if (request.getUserPrincipal() != null) {
            throw new RuntimeException("Please logout first.");
        }

        if (bindingResult.hasErrors()) {
            throw new RuntimeException("Invalid username or password");
        }

        try {
            request.login(form.getUsername(), form.getPassword());
        } catch (ServletException e) {
            throw new RuntimeException("Invalid username or password");
        }

        var auth = (Authentication) request.getUserPrincipal();
        var user = (UserInfoUserDetails) auth.getPrincipal();

        rememberMeServices.loginSuccess(request, response, auth);
        return new CurrentUser(user.getUsername());
    }

    @PostMapping("/log-out")
    public LogoutResponse logout(HttpServletRequest request) throws ServletException {
        request.logout();
        return new LogoutResponse("Log out with success!");
    }

    @GetMapping("/current-user")
    public CurrentUser getCurrentUser(@AuthenticationPrincipal UserInfoUserDetails user) {
        return new CurrentUser(user.getUsername());
    }

    @PostMapping(value = "/register-operator", consumes = "application/json")
    public String registerUser(@RequestBody UserInfoDto userInfoDto) {
        userService.addUser(userInfoDto);
        return "User: " + userInfoDto.getUsername() + " registered!";
    }

    public record CurrentUser(String id) {
    }

    public record LogoutResponse(String message) {
    }
}
