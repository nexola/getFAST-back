package com.univercode.getfast.models.dtos;

import com.univercode.getfast.models.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class UserDTO {

    private String id;
    @NotBlank(message = "Campo obrigatório")
    private String name;
    @Email(message = "Email inválido")
    private String email;
    private String cpf;
    @Size(min = 11, max = 11, message = "Número inválido")
    private String telephone;
    private String token;
    private String expiration;

    public UserDTO(User entity) {
        this.id = entity.getId().toString();
        this.name = entity.getName();
        this.email = entity.getEmail();
        this.cpf = entity.getCpf();
        this.telephone = entity.getTelephone();
        this.token = entity.getToken();
        this.expiration = entity.getExpiration().toString();
    }
}
