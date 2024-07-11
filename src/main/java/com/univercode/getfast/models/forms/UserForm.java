package com.univercode.getfast.models.forms;

import com.univercode.getfast.models.User;
import com.univercode.getfast.models.dtos.UserDTO;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserForm extends UserDTO {
    @NotBlank(message = "Campo obrigatório")
    @Size(min = 6, message = "A senha precisa ter pelo menos 6 caracteres")
    private String password;

    public UserForm() {
        super();
    }

    public UserForm(String password) {
        this.password = password;
    }

    public UserForm(User entity, String password) {
        super(entity);
        this.password = password;
    }
}
