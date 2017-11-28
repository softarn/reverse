package com.hojvall.reverse.repository;

import com.hojvall.reverse.model.ReversedSentence;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import org.springframework.stereotype.Repository;

/**
 * Keeps an in memory history of the last reversed sentences.<br/>
 * <br/>
 * Underlying data structure is a {@link LinkedList} and is acting like a FIFO queue where new elements are added to
 * the beginning and old are removed from end.
 */
@Repository
public class ReversedSentencesRepository {

    private static final int HISTORY_SIZE = 5;

    private List<ReversedSentence> lastReversedSentences = new LinkedList<>();

    public void save(ReversedSentence reversedSentence) {
        lastReversedSentences.add(0, reversedSentence);

        cleanQueue();
    }

    public List<ReversedSentence> findAll() {
        return new ArrayList<>(lastReversedSentences);
    }

    private void cleanQueue() {
        while (lastReversedSentences.size() > HISTORY_SIZE) {
            lastReversedSentences.remove(HISTORY_SIZE);
        }
    }

}
