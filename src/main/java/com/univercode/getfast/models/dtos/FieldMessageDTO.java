package com.univercode.getfast.models.dtos;

import lombok.Data;

@Data
public class FieldMessageDTO {
        private String fieldName;
        private String message;

        public FieldMessageDTO(String fieldName, String message) {
            this.fieldName = fieldName;
            this.message = message;
        }

        public FieldMessageDTO(){}
}
