package com.hojvall.reverse.model;

public class ReversedSentence {

    private String original;
    private String reversed;

    public ReversedSentence(String original, String reversed) {
        this.original = original;
        this.reversed = reversed;
    }

    public String getOriginal() {
        return original;
    }

    public String getReversed() {
        return reversed;
    }
}
