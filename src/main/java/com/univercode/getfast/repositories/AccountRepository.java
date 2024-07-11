package com.univercode.getfast.repositories;

import com.univercode.getfast.models.Account;
import com.univercode.getfast.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
    Account findByIdAndDeletedFalse(UUID id);

    Account findByEmailAndDeletedFalse(String email);
}
