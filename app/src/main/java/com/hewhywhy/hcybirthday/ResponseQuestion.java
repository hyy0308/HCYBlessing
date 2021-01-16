package com.hewhywhy.hcybirthday;

public class ResponseQuestion {
    private int mResponse;
    private int mQuestion;
    public ResponseQuestion(int response, int question) {
        mQuestion = question;
        mResponse = response;
    }
    public int getReciverQuestion() {
        return mQuestion;
    }
    public int getResponse() {
        return mResponse;
    }
}
