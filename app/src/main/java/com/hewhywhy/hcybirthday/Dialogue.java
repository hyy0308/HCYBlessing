package com.hewhywhy.hcybirthday;

public class Dialogue {
    private int mQuestion;
    private ResponseQuestion mResponseQuestionA;
    private ResponseQuestion mResponseQuestionB;
    public Dialogue(int question, ResponseQuestion ResponseQuestionA, ResponseQuestion ResponseQuestionB) {
        mQuestion = question;
        mResponseQuestionA = ResponseQuestionA;
        mResponseQuestionB = ResponseQuestionB;
    }
    public int getQuestion() {
        return mQuestion;
    }
    public ResponseQuestion getResponseQuestionA() {
        return mResponseQuestionA;
    }
    public ResponseQuestion getResponseQuestionB() {
        return mResponseQuestionB;
    }
}
