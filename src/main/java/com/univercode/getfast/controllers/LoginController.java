package com.univercode.getfast.controllers;

import com.univercode.getfast.models.dtos.AccountDTO;
import com.univercode.getfast.models.dtos.UserDTO;
import com.univercode.getfast.models.forms.AccountForm;
import com.univercode.getfast.models.forms.UserForm;
import com.univercode.getfast.services.AccountService;
import com.univercode.getfast.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @PostMapping("/user")
    public ResponseEntity<UserDTO> login(@RequestBody UserForm form) {

        UserDTO user = userService.login(form);

        return ResponseEntity.ok(user);
    }

    @PostMapping("/account")
    public ResponseEntity<AccountDTO> login(@RequestBody AccountForm form) {

        AccountDTO account = accountService.login(form);

        return ResponseEntity.ok(account);
    }
}
