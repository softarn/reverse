package com.hojvall.reverse.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.hojvall.reverse.config.ApplicationConfiguration;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReverserServiceTest {

    private static final List<String> testDelimiters = new ArrayList<>();
    private static final ApplicationConfiguration config = mock(ApplicationConfiguration.class);

    private final ReverserService reverserService = new ReverserService(config);

    @BeforeClass
    public static void beforeClass() {
        testDelimiters.add("\\\\s");
        when(config.getWordDelimiters()).then(invocation -> new ArrayList<>(testDelimiters));
    }

    @Test
    public void reverse_shouldReverseOneWordSentence() {
        String actual = reverserService.reverseWords("abc");

        assertThat(actual).isEqualTo("cba");
    }

    @Test
    public void reverse_shouldReverseMultiWordSentence() {
        String actual = reverserService.reverseWords("abc def ghi");

        assertThat(actual).isEqualTo("cba fed ihg");
    }

    @Test
    public void reverse_shouldReverseCommaSeparatedSentence() {
        String actual = reverserService.reverseWords("abc,def,ghi");

        assertThat(actual).isEqualTo("cba,fed,ihg");
    }


    @Test
    public void reverse_shouldReverseWithDifferentDelimiters() {
        String actual = reverserService.reverseWords("abc,def ghi");

        assertThat(actual).isEqualTo("cba,fed ihg");
    }

    @Test
    public void reverse_shouldHandleEmptyString() {
        String actual = reverserService.reverseWords("");

        assertThat(actual).isEqualTo("");
    }

}