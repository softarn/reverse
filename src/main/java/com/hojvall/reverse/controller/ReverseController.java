package com.hojvall.reverse.controller;

import com.hojvall.reverse.controller.request.ReverseRequest;
import com.hojvall.reverse.model.ReversedSentence;
import com.hojvall.reverse.repository.ReversedSentencesRepository;
import com.hojvall.reverse.service.ReverserService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReverseController {

    private final ReverserService reverserService;
    private final ReversedSentencesRepository sentencesRepository;

    @Autowired
    public ReverseController(ReverserService reverserService, ReversedSentencesRepository sentencesRepository) {
        this.reverserService = reverserService;
        this.sentencesRepository = sentencesRepository;
    }

    @RequestMapping(name = "/reverse", method = RequestMethod.POST)
    public ReversedSentence reverseSentence(@Valid @RequestBody ReverseRequest request) {
        String reversed = reverserService.reverseWords(request.getSentence());
        ReversedSentence reversedSentence = new ReversedSentence(request.getSentence(), reversed);

        sentencesRepository.save(reversedSentence);

        return reversedSentence;
    }

    @RequestMapping(name = "/reverse-history", method = RequestMethod.GET)
    public List<ReversedSentence> reverseHistory() {
        return sentencesRepository.findAll();
    }

}
