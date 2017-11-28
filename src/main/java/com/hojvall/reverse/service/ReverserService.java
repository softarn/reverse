package com.hojvall.reverse.service;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

import com.hojvall.reverse.config.ApplicationConfiguration;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Reverse words in sentences.
 */
@Service
public class ReverserService {

    private static final Logger LOG = LoggerFactory.getLogger(ReverserService.class);
    private static final String keepDelimiterRegex = "(?<=[%s])|(?=[%s])";

    private final List<String> regexWordDelimiters;
    private final String wordSplitRegex;

    @Autowired
    public ReverserService(final ApplicationConfiguration config) {
        List<String> wordDelimiters = config.getWordDelimiters();
        wordDelimiters.add(",");

        LOG.info("Using word delimiters: ");
        wordDelimiters.forEach(LOG::info);

        //Escape characters
        regexWordDelimiters = wordDelimiters.stream().map(it -> "\\" + it).collect(toList());

        wordSplitRegex = buildWordSplitRegex(regexWordDelimiters);
        LOG.info(wordSplitRegex);
    }

    /**
     * Reversed the words in the sentence. Determines word boundary by using delimiters defined in
     * {@link #regexWordDelimiters}
     *
     * @param sentence Sentence to reverse words in
     */
    public String reverseWords(String sentence) {
        StringBuilder builder = new StringBuilder();

        String[] strings = sentence.split(wordSplitRegex);

        for (String string : strings) {
            String reversed = new StringBuilder(string).reverse().toString();
            builder.append(reversed);
        }

        return builder.toString();
    }

    /**
     * Builds a regex that keeps the delimiter when used with {@link String#split}
     *
     * @param delimiters A list of delimiters used to split the string
     */
    private String buildWordSplitRegex(List<String> delimiters) {
        StringBuilder builder = new StringBuilder();
        delimiters.forEach(builder::append);
        String wordDelimitersString = builder.toString();

        return format(keepDelimiterRegex, wordDelimitersString, wordDelimitersString);
    }

}
