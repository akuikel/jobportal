package com.aavashkuikel.jobportal.controller;

import com.aavashkuikel.jobportal.services.UsersService;
import com.aavashkuikel.jobportal.services.UsersTypeService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import com.aavashkuikel.jobportal.entity.UsersType;
import com.aavashkuikel.jobportal.entity.Users;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class UsersController {

    private final UsersTypeService usersTypeService;

    private final UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService, UsersTypeService usersTypeService) {
        this.usersService = usersService;
        this.usersTypeService = usersTypeService;
    }

    @GetMapping("/register")
    public String register(Model model)
    {
        List<UsersType> usersType= usersTypeService.getAll();
        model.addAttribute("getAllTypes", usersType);
        model.addAttribute("user", new Users());
        return "register";
    }

    @PostMapping("/register/new")
    public String userRegistration(@Valid Users users, Model model)
    {
        Optional<Users> optionalUsers= usersService.getUserByEmail(users.getEmail());

        if (optionalUsers.isPresent())
        {
            model.addAttribute("error", "Email is already registered, try a new email");
            List<UsersType> usersType= usersTypeService.getAll();
            model.addAttribute("getAllTypes", usersType);
            model.addAttribute("user", new Users());
            return "register";
        }
        usersService.addNew(users);
        return "redirect:/dashboard/";
    }

    @GetMapping("/login")
    public String login()
    {
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response)
    {
        Authentication authentication=  SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null)
        {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/";
    }

}
