package com.univercode.getfast.models.forms;

import com.univercode.getfast.models.Account;
import com.univercode.getfast.models.dtos.AccountDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@Data
public class AccountForm extends AccountDTO {
    @NotBlank(message = "Campo obrigatório")
    @Size(min = 6, message = "A senha precisa ter pelo menos 6 caracteres")
    private String password;

    public AccountForm() {
        super();
    }

    public AccountForm(String password) {
        this.password = password;
    }

    public AccountForm(Account entity, String password) {
        super(entity);
        this.password = password;
    }
}
