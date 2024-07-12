package com.univercode.getfast.controllers;

import com.univercode.getfast.models.dtos.AccountDTO;
import com.univercode.getfast.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> findById(@PathVariable String id) {
        return ResponseEntity.ok(accountService.findById(id));
    }

    @PostMapping
    public ResponseEntity<AccountDTO> insert(@RequestBody @Valid AccountDTO dto) {
        return ResponseEntity.ok(accountService.insert(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountDTO> update(@PathVariable String id, @RequestBody @Valid AccountDTO dto) {
        return ResponseEntity.ok(accountService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        accountService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
