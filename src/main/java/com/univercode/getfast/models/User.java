package com.univercode.getfast.models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@Entity
@Table(name = "tb_user")
public class User extends Domain {

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "token")
    private String token;

    @Column(name = "expiration")
    private Timestamp expiration;

    @Column(name = "account_id")
    private UUID accountId;

    public User() {
    }

    public User(String name, String email, String password, String cpf, String telephone, UUID accountId) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.cpf = cpf;
        this.telephone = telephone;
        this.accountId = accountId;
    }
}
