package com.univercode.getfast.models.dtos;

import com.univercode.getfast.models.Account;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class AccountDTO {
    private String id;
    @NotBlank(message = "Campo obrigatório")
    private String name;
    @Email(message = "Email inválido")
    private String email;
    @Size(min = 11, max = 11, message = "Número inválido")
    private String telephone;
    private String cnpj;
    private String cpf;
    private String cep;
    private String address;
    @Positive(message = "Número inválido")
    private String number;
    private String neighborhood;
    private String city;
    private String state;
    private String complement;
    private Timestamp creationDate;
    private Boolean deleted;
    private Timestamp lastUpdate;
    private String token;
    private Timestamp expiration;

    public AccountDTO(String id, String name, String email, String telephone, String cnpj, String cpf, String cep, String address, String number, String neighborhood, String city, String state, String complement, Timestamp creationDate, Boolean deleted, Timestamp lastUpdate, String token, Timestamp expiration) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.telephone = telephone;
        this.cnpj = cnpj;
        this.cpf = cpf;
        this.cep = cep;
        this.address = address;
        this.number = number;
        this.neighborhood = neighborhood;
        this.city = city;
        this.state = state;
        this.complement = complement;
        this.creationDate = creationDate;
        this.deleted = deleted;
        this.lastUpdate = lastUpdate;
        this.token = token;
        this.expiration = expiration;
    }

    public AccountDTO(Account entity) {
        this.id = entity.getId().toString();
        this.name = entity.getName();
        this.email = entity.getEmail();
        this.telephone = entity.getTelephone();
        this.cnpj = entity.getCnpj();
        this.cpf = entity.getCpf();
        this.cep = entity.getCep();
        this.address = entity.getAddress();
        this.number = entity.getNumber();
        this.neighborhood = entity.getNeighborhood();
        this.city = entity.getCity();
        this.state = entity.getState();
        this.complement = entity.getComplement();
        this.creationDate = Timestamp.valueOf(entity.getCreationDate());
        this.deleted = entity.getDeleted();
        this.lastUpdate = Timestamp.valueOf(entity.getLastUpdate());
        this.token = entity.getToken();
        this.expiration = entity.getExpiration();
    }
}
