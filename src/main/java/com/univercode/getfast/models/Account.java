package com.univercode.getfast.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tb_account")
@Data
public class Account extends Domain {

    private String name;
    private String email;
    private String telephone;
    private String cnpj;
    private String cpf;
    private String password;
    private String cep;
    private String address;
    private String number;
    private String neighborhood;
    private String city;
    private String state;
    private String complement;
    private String token;
    private Timestamp expiration;

    public Account() {
    }

    public Account(String name, String email, String telephone, String cnpj, String cpf, String password, String cep, String address, String number, String neighborhood, String city, String state, String complement, String token, Timestamp expiration) {
        this.name = name;
        this.email = email;
        this.telephone = telephone;
        this.cnpj = cnpj;
        this.cpf = cpf;
        this.password = password;
        this.cep = cep;
        this.address = address;
        this.number = number;
        this.neighborhood = neighborhood;
        this.city = city;
        this.state = state;
        this.complement = complement;
        this.token = token;
        this.expiration = expiration;
    }
}
