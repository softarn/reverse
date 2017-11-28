package com.hojvall.reverse.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.hojvall.reverse.model.ReversedSentence;
import java.util.List;
import org.junit.Test;

public class ReversedSentencesRepositoryTest {

    private final ReversedSentencesRepository repository = new ReversedSentencesRepository();

    private final ReversedSentence sentence = new ReversedSentence("original", "reversed");

    @Test
    public void findAll_shouldReturnSavedObjects() {
        repository.save(sentence);

        List<ReversedSentence> reversedSentences = repository.findAll();

        assertThat(reversedSentences).contains(sentence);
    }

    @Test
    public void save_shouldNotStoreMoreThan5Sentences() {
        saveSentences(6);

        List<ReversedSentence> reversedSentences = repository.findAll();

        assertThat(reversedSentences).hasSize(5);
    }

    @Test
    public void save_shouldUseFIFOQueue() {
        saveSentences(4);

        ReversedSentence sentence2 = new ReversedSentence("original2", "reversed2");
        repository.save(sentence2);

        List<ReversedSentence> reversedSentences = repository.findAll();

        assertThat(reversedSentences).hasSize(5);
        assertThat(reversedSentences.get(0)).isEqualTo(sentence2);
        assertThat(reversedSentences.get(4)).isEqualTo(sentence);
    }

    private void saveSentences(int numberOfSentences) {
        for (int i = 0; i < numberOfSentences; i++) {
            repository.save(sentence);
        }
    }

}