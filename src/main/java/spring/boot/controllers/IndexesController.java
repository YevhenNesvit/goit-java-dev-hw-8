package spring.boot.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import spring.boot.model.dao.RoleDao;
import spring.boot.model.dao.UserDao;
import spring.boot.services.RoleService;
import spring.boot.services.UserService;

import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("")
public class IndexesController {
    @Autowired
    private final RoleService roleService;
    @Autowired
    private final UserService userService;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    @GetMapping("")
    public ModelAndView getIndex() {

        return new ModelAndView("index");
    }

    @GetMapping("/homepage")
    public ModelAndView getHomePage() {
        Set<String> roles = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
        for (int i = 0; i < roles.size(); i++) {
            if (roles.contains("ROLE_ADMIN")) {

                return new ModelAndView("adminhome");
            }
        }

        return new ModelAndView("userhome");
    }

    @GetMapping("/registration")
    public ModelAndView getRegistrationForm() {

        return new ModelAndView("registration");
    }

    @PostMapping("/users/userRegistered")
    public ModelAndView createUser(@ModelAttribute("email") String email, @ModelAttribute("password") String password,
                                   @ModelAttribute("confirm") String confirm, @ModelAttribute("firstName") String firstName,
                                   @ModelAttribute("lastName") String lastName, UserDao user, RoleDao role) {
        if (!(email.equals("") || password.equals("") || firstName.equals("") || lastName.equals(""))) {
            if (email.matches("^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$")) {
                if (!userService.IsUserEmailExists(email)) {
                    if (password.equals(confirm)) {

                        role.setId(roleService.getRoleIdByName("ROLE_USER"));
                        role.setName("ROLE_USER");

                        user.setEmail(email);
                        user.setPassword(passwordEncoder.encode(password));
                        user.setFirstName(firstName);
                        user.setLastName(lastName);
                        user.setRole(role);

                        userService.create(user);

                        return new ModelAndView("users/userRegistered");
                    } else {

                        return new ModelAndView("/passwordValidation");
                    }

                } else {

                    return new ModelAndView("userAlreadyExists");
                }

            } else {

                return new ModelAndView("/emailWrongFormat");
            }
        } else {
            return new ModelAndView("/fieldsCanNotBeEmpty");
        }
    }
}
