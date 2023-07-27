package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

        User user = userService.findByUsername(principal.getName());
        System.out.println(principal.getName());
        List<Role> allRoles = roleService.getRoles();
        List<User> userList = userService.getAllUsers();
        model.addAttribute("user", user);
        model.addAttribute("userList", userList);
        model.addAttribute("allRoles", allRoles);
        model.addAttribute("emptyUser", new User());
        return "admin";
    }

    @PatchMapping("/edit/{id}")
    public String updateCustomer(@ModelAttribute("emptyUser") User user, @PathVariable("id") Long id,
                                 @RequestParam(value = "customerRolesSelector") String[] selectResult) {
        for (String s : selectResult) {
            user.addRole(roleService.getRoleByName("ROLE_" + s));
        }
        userService.update(user, id);
        return "redirect:/admin";
    }


    @DeleteMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @PostMapping("/new")
    public String createUser(@ModelAttribute("emptyUser") User user,
                             @RequestParam(value = "checkedRoles") String[] selectResult) {
        for (String s : selectResult) {
            user.addRole(roleService.getRoleByName("ROLE_" + s));
        }
        userService.save(user);
        return "redirect:/admin";
    }
}
