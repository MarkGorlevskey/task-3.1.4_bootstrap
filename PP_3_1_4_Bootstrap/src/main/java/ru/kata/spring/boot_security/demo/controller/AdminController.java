package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String getAllUsers(Model model, Principal principal) {

        User customer = userService.findByUsername(principal.getName());
        List<Role> allRoles = roleService.getRoles();
        List<User> allCustomers = userService.getAllUsers();
        model.addAttribute("customer", customer);
        model.addAttribute("allCustomers", allCustomers);
        model.addAttribute("allPossibleRoles", allRoles);
        model.addAttribute("emptyUser", new User());
        return "admin";
    }

    @GetMapping("/search")
    public String getUserById(@RequestParam(required = false) Long id, Model model) {
        User user = userService.findById(id);
        if (user == null) {
            return "redirect:/admin";
        }
        model.addAttribute("user", user);
        return "search";
    }

    @GetMapping("/new_user")
    public String showCreateUserForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "new_user";
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/edit/{id}")
    public String showEditUserForm(@PathVariable Long id, Model model) {
        User user = userService.findById(id);
        if (user == null) {
            return "redirect:/";
        }
        model.addAttribute("user", user);
        return "edit_user";
    }

    @PatchMapping("/edit/{id}")
    public String updateUserSubmit(@PathVariable Long id, @ModelAttribute User user) {
        userService.update(user, id);
        return "redirect:/admin";
    }


    @DeleteMapping("/delete/{id}")
    public String deleteUserSubmit(@PathVariable Long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }
}
