package com.univercode.getfast.services;

import com.univercode.getfast.exceptions.DatabaseException;
import com.univercode.getfast.exceptions.ResourceNotFoundException;
import com.univercode.getfast.models.User;
import com.univercode.getfast.models.dtos.UserDTO;
import com.univercode.getfast.models.forms.UserForm;
import com.univercode.getfast.repositories.UserRepository;
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
public class UserService {

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public UserDTO findById(String id) {
        Optional <User> entity = userRepository.findById(UUID.fromString(id));
        User User = entity.orElseThrow(() -> new ResourceNotFoundException("Id não encontrado: " + id));
        System.out.println(User);
        UserDTO dto = new UserDTO(User);
        System.out.println(dto);
        return dto;
    }

    @Transactional
    public UserDTO insert(UserForm dto) {
        User entity = new User();
        copyDtoToEntity(dto, entity);
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        entity = userRepository.save(entity);
        return new UserDTO(entity);
    }

    @Transactional
    public UserDTO update(String id, UserForm form) {
        try {
            User entity = userRepository.findById(UUID.fromString(id)).orElseThrow(() -> new ResourceNotFoundException("Id não encontrado: " + id));
            copyDtoToEntity(form, entity);
            entity = userRepository.save(entity);
            return new UserDTO(entity);
        }
        catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id não encontrado: " + id);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(String id) {
        if (!userRepository.existsById(UUID.fromString(id))) {
            throw new ResourceNotFoundException("Conta não encontrada");
        }
        try {
            userRepository.deleteById(UUID.fromString(id));
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }
    }

    private void copyDtoToEntity(UserForm dto, User entity) {
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setTelephone(dto.getTelephone());
        entity.setCpf(dto.getCpf());
        entity.setLastUpdate(LocalDateTime.now());
        entity.setToken(entity.getToken());
        entity.setExpiration(entity.getExpiration());
        entity.setAccountId(UUID.fromString(dto.getAccountId()));
    }

    public UserDTO login(UserForm form) {
        User user = userRepository.findByEmailAndDeletedFalse(form.getEmail());
        if (user == null) {
            throw new ResourceNotFoundException("Usuário não encontrado");
        }
        boolean password = passwordEncoder.matches(form.getPassword(), user.getPassword());
        if (!password) {
            throw new ResourceNotFoundException("Senha inválida");
        }

        String token = generateNewToken();
        Timestamp expiration = Timestamp.valueOf(LocalDateTime.now().plusHours(1));

        user.setToken(token);
        user.setExpiration(expiration);

        user = userRepository.save(user);
        return new UserDTO(user);
    }

    public static String generateNewToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }
}
