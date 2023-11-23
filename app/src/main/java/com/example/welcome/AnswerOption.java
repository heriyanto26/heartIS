package com.example.welcome;

public class AnswerOption {

    private String answer;
    private int score;

    public AnswerOption(String answer, int score) {
        this.answer = answer;
        this.score = score;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

}
