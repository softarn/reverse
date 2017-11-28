package com.hojvall.reverse.controller;

import static java.util.Arrays.asList;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.hojvall.reverse.model.ReversedSentence;
import com.hojvall.reverse.repository.ReversedSentencesRepository;
import com.hojvall.reverse.service.ReverserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ReverseController.class, secure = false)
public class ReverseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReverserService reverserService;

    @MockBean
    private ReversedSentencesRepository sentencesRepository;

    @Test
    public void reverseSentence_shouldCallReverser_whenRequestIsValid() throws Exception {
        String requestBody = "{\"sentence\":\"abc dfg\"}";

        mockMvc.perform(reverseRequest().content(requestBody));

        verify(reverserService).reverseWords(anyString());
    }

    @Test
    public void reverseSentence_shouldSaveReversedSentence_whenRequestIsValid() throws Exception {
        String requestBody = "{\"sentence\":\"abc dfg\"}";

        mockMvc.perform(reverseRequest().content(requestBody));

        verify(sentencesRepository).save(any(ReversedSentence.class));
    }

    @Test
    public void reverseSentence_shouldReturn200_whenRequestIsValid() throws Exception {
        String requestBody = "{\"sentence\":\"abc\"}";

        when(reverserService.reverseWords(eq("abc"))).thenReturn("cba");

        mockMvc
            .perform(reverseRequest().content(requestBody))
            .andExpect(status().isOk())
            .andExpect(content().json("{\"original\":\"abc\",\"reversed\":\"cba\"}"));
    }

    @Test
    public void reverseSentence_shouldReturnBadRequest_whenSentenceIsTooLong() throws Exception {
        String requestBody = "{\"sentence\":\"abc dfg" + buildLongString() + "\"}";

        mockMvc
            .perform(reverseRequest().content(requestBody))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void reverseSentence_shouldReturnBadRequest_whenSentenceIsNotIncluded() throws Exception {
        String requestBody = "{}";

        mockMvc
            .perform(reverseRequest().content(requestBody))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void reverseHistory_shouldReturnReverseHistory() throws Exception {
        when(sentencesRepository.findAll()).thenReturn(asList(new ReversedSentence("abc", "cba")));

        mockMvc
            .perform(reverseHistoryRequest())
            .andExpect(status().isOk())
            .andExpect(content().json("[{\"original\":\"abc\",\"reversed\":\"cba\"}]"));
    }

    private MockHttpServletRequestBuilder reverseRequest() {
        return MockMvcRequestBuilders
            .post("/reverse")
            .accept(APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON);
    }

    private MockHttpServletRequestBuilder reverseHistoryRequest() {
        return MockMvcRequestBuilders
            .get("/reverse-history")
            .accept(APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON);
    }

    private String buildLongString() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < 300; i++) {
            builder.append("a");
        }

        return builder.toString();
    }

}