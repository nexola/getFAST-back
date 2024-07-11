package com.univercode.getfast.services;

import com.univercode.getfast.exceptions.DatabaseException;
import com.univercode.getfast.exceptions.ResourceNotFoundException;
import com.univercode.getfast.models.Account;
import com.univercode.getfast.models.dtos.AccountDTO;
import com.univercode.getfast.models.forms.AccountForm;
import com.univercode.getfast.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

    @Transactional(readOnly = true)
    public AccountDTO findById(String id) {
        Optional <Account> entity = accountRepository.findById(UUID.fromString(id));
        Account account = entity.orElseThrow(() -> new ResourceNotFoundException("Id não encontrado: " + id));
        System.out.println(account);
        AccountDTO dto = new AccountDTO(account);
        System.out.println(dto);
        return dto;
    }

    @Transactional
    public AccountDTO insert(AccountForm form) {
        Account entity = new Account();
        copyDtoToEntity(form, entity);
        entity.setPassword(passwordEncoder.encode(form.getPassword()));
        entity = accountRepository.save(entity);
        return new AccountDTO(entity);
    }

    @Transactional
    public AccountDTO update(String id, AccountForm accountDTO) {
        try {
            Account entity = accountRepository.findById(UUID.fromString(id)).orElseThrow(() -> new ResourceNotFoundException("Id não encontrado: " + id));
            copyDtoToEntity(accountDTO, entity);
            entity = accountRepository.save(entity);
            return new AccountDTO(entity);
        }
        catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id não encontrado: " + id);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(String id) {
        if (!accountRepository.existsById(UUID.fromString(id))) {
            throw new ResourceNotFoundException("Conta não encontrada");
        }
        try {
            accountRepository.deleteById(UUID.fromString(id));
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }
    }

    private void copyDtoToEntity(AccountForm dto, Account entity) {
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setTelephone(dto.getTelephone());
        entity.setCnpj(dto.getCnpj());
        entity.setCpf(dto.getCpf());
        entity.setAddress(dto.getAddress());
        entity.setNeighborhood(dto.getNeighborhood());
        entity.setCep(dto.getCep());
        entity.setNumber(dto.getNumber());
        entity.setCity(dto.getCity());
        entity.setState(dto.getState());
        entity.setComplement(dto.getComplement());
        entity.setLastUpdate(LocalDateTime.now());
    }

    public AccountDTO login(AccountForm form) {
        Account account = accountRepository.findByEmailAndDeletedFalse(form.getEmail());
        if (account == null) {
            throw new ResourceNotFoundException("Usuário não encontrado");
        }
        boolean password = passwordEncoder.matches(form.getPassword(), account.getPassword());
        if (!password) {
            throw new ResourceNotFoundException("Senha inválida");
        }

        String token = generateNewToken();
        Timestamp expiration = Timestamp.valueOf(LocalDateTime.now().plusHours(1));

        account.setToken(token);
        account.setExpiration(expiration);

        account = accountRepository.save(account);
        return new AccountDTO(account);
    }

    public static String generateNewToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }
}
