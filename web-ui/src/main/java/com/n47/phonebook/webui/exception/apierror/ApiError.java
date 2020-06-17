package com.n47.phonebook.webui.exception.apierror;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {

    private String errorCode;
    private String errorMessage;
}
