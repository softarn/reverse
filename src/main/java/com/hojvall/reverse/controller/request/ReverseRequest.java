package com.hojvall.reverse.controller.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ReverseRequest {

    // TODO: Add size constraint configuration
    @NotNull
    @Size(max = 300)
    private String sentence;

    public String getSentence() {
        return sentence;
    }
}
