package com.univercode.getfast.services;

import com.univercode.getfast.exceptions.AccountException;
import com.univercode.getfast.exceptions.DatabaseException;
import com.univercode.getfast.exceptions.ResourceNotFoundException;
import com.univercode.getfast.models.Account;
import com.univercode.getfast.models.dtos.AccountDTO;
import com.univercode.getfast.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

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
    public AccountDTO insert(AccountDTO dto) {
        Account verify = accountRepository.findByEmailAndDeletedFalse(dto.getEmail());
        if (verify != null) {
            throw new AccountException("CNPJ já cadastrado");
        }
        Account entity = new Account();
        copyDtoToEntity(dto, entity);
        entity = accountRepository.save(entity);
        return new AccountDTO(entity);
    }

    @Transactional
    public AccountDTO update(String id, AccountDTO accountDTO) {
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

    private void copyDtoToEntity(AccountDTO dto, Account entity) {
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
}
