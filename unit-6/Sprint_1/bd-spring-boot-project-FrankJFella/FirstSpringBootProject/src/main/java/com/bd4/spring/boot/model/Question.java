package com.bd4.spring.boot.model;

import java.util.Objects;

public class Question {

    String theQuestion;  // hold the question to send teh server

    // A POJO should have a default ctor, standard getters/setters,
    //                      equals(), hashCode, toString()

    public Question() {}   // Default ctor

    // Standard getters/setters
    public String getTheQuestion() {
        return theQuestion;
    }
    public void setTheQuestion(String theQuestion) {
        this.theQuestion = theQuestion;
    }

    // Standard overrides
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Question)) return false;
        Question question = (Question) o;
        return getTheQuestion().equals(question.getTheQuestion());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTheQuestion());
    }

    @Override
    public String toString() {
        return "Question{" +
                "theQuestion='" + theQuestion + '\'' +
                '}';
    }
}
