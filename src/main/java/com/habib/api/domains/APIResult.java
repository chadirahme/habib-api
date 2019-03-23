package com.habib.api.domains;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class APIResult {

    String message;
    int userId;

    public APIResult(){

    }

    public APIResult(String message)
    {
        this.message = message;
    }
}
