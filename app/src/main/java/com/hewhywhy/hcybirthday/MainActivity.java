package com.hewhywhy.hcybirthday;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import android.view.Gravity;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;


public class MainActivity extends AppCompatActivity {

    private ListView msgListView;
    private MsgAdapter adapter;
    private Button button1;
    private Button button2;
    private Button start_button;
    private VideoView videoView;
    private int currentButtonIndex;
    private List<Msg> msgList = new ArrayList<Msg>();
    private Dialogue[] mDialogueBank = new Dialogue[] {
            new Dialogue(R.string.diaGreeting,
                    new ResponseQuestion(R.string.greetingResponseA, R.string.quesB),
                    new ResponseQuestion(R.string.greetingResponseB, R.string.quesB)),
            new Dialogue(R.string.quesB,
                    new ResponseQuestion(R.string.quesBresponseA, R.string.quesC),
                    new ResponseQuestion(R.string.quesBresponseB, R.string.quesC)),
            new Dialogue(R.string.quesC,
                    new ResponseQuestion(R.string.quesCResponseA, R.string.quesD),
                    new ResponseQuestion(R.string.quesCResponseB, R.string.quesE)),
            new Dialogue(R.string.quesD,
                    new ResponseQuestion(R.string.quesDResponseA, R.string.quesF),
                    new ResponseQuestion(R.string.quesDResponseB, R.string.quesG)),
            new Dialogue(R.string.quesE,
                    new ResponseQuestion(R.string.quesEResponseA, R.string.quesFinal),
                    new ResponseQuestion(R.string.quesEResponseB, R.string.quesFinal)),
            new Dialogue(R.string.quesG,
                    new ResponseQuestion(R.string.quesGResponseA, R.string.quesFinal),
                    new ResponseQuestion(R.string.quesGResponseB, R.string.quesFinal)),
            new Dialogue(R.string.quesF,
                    new ResponseQuestion(R.string.quesFResponseA, R.string.quesFinal),
                    new ResponseQuestion(R.string.quesFResponseB, R.string.quesFinal)),
            new Dialogue(R.string.quesFinal,
                    new ResponseQuestion(R.string.quesFinalResA, R.string.finalAAns),
                    new ResponseQuestion(R.string.quesFinalResB, R.string.finalBAns)),
//            new Dialogue(R.string.question_mideast, false),
//            new Dialogue(R.string.question_africa, false),
//            new Dialogue(R.string.question_americas, true),
//            new Dialogue(R.string.question_asia, true),
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set content view style
        setContentView(R.layout.activity_main);
        // Adapter handle logic of sender/receiver
        adapter = new MsgAdapter(MainActivity.this, R.layout.msg_item, msgList);
        // Msg list for conversion
        msgListView = findViewById(R.id.msg_list_view);
        // Set adapter for msd list
        msgListView.setAdapter(adapter);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        start_button = findViewById(R.id.start_button);
        // initiate a video view
        videoView =  (VideoView) findViewById(R.id.videoView);

        videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.vid);

        MediaController mediaController = new MediaController(this);

        mediaController.setAnchorView(videoView);

        videoView.setMediaController(mediaController);
        start_button.setVisibility(View.GONE);
        videoView.setVisibility(View.GONE);

        // Header
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(R.layout.actionbar_title);
            TextView textView = (TextView) actionBar.getCustomView().findViewById(R.id.display_title);
            textView.setText("华晨宇");
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowCustomEnabled(true);
        }
        // Receiver send out the first greeting
        initMsgs();

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Since we are using content to find the ID
                // the content has to be unique
                String content = button1.getText().toString();
                if(content.equals("nothing")){
                    Msg msg1 = new Msg(content, Msg.TYPE_SEND);
                    msgList.add(msg1);
                    adapter.notifyDataSetChanged();
                    msgListView.setSelection(msgList.size());
                }else{
                    currentButtonIndex = GetCurrentButtonContentId(content);
                    String receiverQuestionContent = GetString(GetReciverQuestion(currentButtonIndex));
                    int receiverQuestionContentId = GetReciverQuestion(currentButtonIndex);
                    if (!"".equals(content)) {
                        Msg msg1 = new Msg(content, Msg.TYPE_SEND);
                        msgList.add(msg1);
                        final Handler handler = new Handler(Looper.getMainLooper());
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //Do something after 100ms
                            }
                        }, 100);
                        Msg msg2 = new Msg(receiverQuestionContent, Msg.TYPE_RECEIVED);
                        msgList.add(msg2);
                        adapter.notifyDataSetChanged();
                        msgListView.setSelection(msgList.size());
                        SetButtonContent(receiverQuestionContentId);
                    }
                }
            }
        });


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Since we are using content to find the ID
                // the content has to be unique
                String content = button2.getText().toString();
                if(content.equals("nothing")){
                    Msg msg1 = new Msg(content, Msg.TYPE_SEND);
                    msgList.add(msg1);
                    adapter.notifyDataSetChanged();
                    msgListView.setSelection(msgList.size());
                }
                else {
                    currentButtonIndex = GetCurrentButtonContentId(content);
                    String receiverQuestionContent = GetString(GetReciverQuestion(currentButtonIndex));
                    int receiverQuestionContentId = GetReciverQuestion(currentButtonIndex);
                    if (!"".equals(content)) {
                        Msg msg1 = new Msg(content, Msg.TYPE_SEND);
                        msgList.add(msg1);
                        Msg msg2 = new Msg(receiverQuestionContent, Msg.TYPE_RECEIVED);
                        msgList.add(msg2);
                        adapter.notifyDataSetChanged();
                        msgListView.setSelection(msgList.size());
                        SetButtonContent(receiverQuestionContentId);
                    }
                }
            }
        });


    }

    private void TurnOnStartButton(){
        videoView.setVisibility(View.VISIBLE);
        start_button.setVisibility(View.VISIBLE);
        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoView.start();
            }
        });
    }

    private void initMsgs() {
        Msg msg1 = new Msg(GetString(R.string.diaGreeting), Msg.TYPE_RECEIVED);
        msgList.add(msg1);
        SetButtonContent(R.string.diaGreeting);
    }

    private String GetString(int input){
        return getResources().getString(input);
    }
    private int GetCurrentButtonContentId(String input){
        int result = 0;
        for(int i = 0; i < mDialogueBank.length; i++){
            if(GetString(mDialogueBank[i].getResponseQuestionA().getResponse()) == input){
                result = mDialogueBank[i].getResponseQuestionA().getResponse();
                break;
            }
            else if(GetString(mDialogueBank[i].getResponseQuestionB().getResponse()) == input){
                result = mDialogueBank[i].getResponseQuestionB().getResponse();
                break;
            }
        }
        return result;
    }

    private int GetReciverQuestion(int input){
        int result = -1;
        for(int i = 0; i < mDialogueBank.length; i++){
            if(mDialogueBank[i].getResponseQuestionA().getResponse() == input){
                result = mDialogueBank[i].getResponseQuestionA().getReciverQuestion();
                break;
            }else if (mDialogueBank[i].getResponseQuestionB().getResponse() == input){
                result = mDialogueBank[i].getResponseQuestionB().getReciverQuestion();
                break;
            }
        }
        return result;
    }


    // input is question Id
    // in this class, mDialogueBank[i].getQuestion
    private String GetResponseA(int input){
        String result = null;
        for(int i = 0; i < mDialogueBank.length; i++){
            if(mDialogueBank[i].getQuestion() == input){
                result = GetString(mDialogueBank[i].getResponseQuestionA().getResponse());
                break;
            }
        }
        return result;
    }
    private String GetReciverQuestionA(int input){
        String result = null;
        for(int i = 0; i < mDialogueBank.length; i++){
            if(mDialogueBank[i].getQuestion() == input){
                result = GetString(mDialogueBank[i].getResponseQuestionA().getReciverQuestion());
                break;
            }
        }
        return result;
    }

    private String GetResponseB(int input){
        String result = null;
        for(int i = 0; i < mDialogueBank.length; i++){
            if(mDialogueBank[i].getQuestion() == input){
                result = GetString(mDialogueBank[i].getResponseQuestionB().getResponse());
                break;
            }
        }
        return result;
    }
    private String GetReciverQuestionB(int input){
        String result = null;
        for(int i = 0; i < mDialogueBank.length; i++){
            if(mDialogueBank[i].getQuestion() == input){
                result = GetString(mDialogueBank[i].getResponseQuestionB().getReciverQuestion());
                break;
            }
        }
        return result;
    }

    private void SetButtonContent(int input){
        String responseA = GetResponseA(input);
        String responseB = GetResponseB(input);
        if (responseA == null || responseB == null){
            button1.setVisibility(View.GONE);
            button2.setVisibility(View.GONE);
            TurnOnStartButton();
//            button1.setText("nothing");
//            button2.setText("nothing");
        }else{
            button1.setText(responseA);
            button2.setText(responseB);
        }
    }
}

