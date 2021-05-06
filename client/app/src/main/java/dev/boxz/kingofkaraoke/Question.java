package dev.boxz.kingofkaraoke;

import java.util.ArrayList;

public class Question {
    private String question;
    private ArrayList<String> options;
    private String coverURL;
    private Integer correctAnswer;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }

    public String getCoverURL() {
        return coverURL;
    }

    public void setCoverURL(String coverURL) {
        this.coverURL = coverURL;
    }

    public Integer getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(Integer correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public Question(String question, ArrayList<String> options, String coverURL, Integer correctAnswer) {
        this.question = question;
        this.options = options;
        this.coverURL = coverURL;
        this.correctAnswer = correctAnswer;
    }
}
