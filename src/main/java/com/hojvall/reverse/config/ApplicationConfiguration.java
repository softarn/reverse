package com.hojvall.reverse.config;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    @Value("${wordDelimiters}")
    private String wordDelimiters;

    public List<String> getWordDelimiters() {
        return new ArrayList<>(asList(wordDelimiters.split(",")));
    }
}
