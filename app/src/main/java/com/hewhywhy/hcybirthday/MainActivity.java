package com.hewhywhy.hcybirthday;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import android.view.Gravity;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private ListView msgListView;
    private MsgAdapter adapter;
    private Button button1;
    private Button button2;
    private int currentButtonIndex;
    private List<Msg> msgList = new ArrayList<Msg>();
    private Dialogue[] mDialogueBank = new Dialogue[] {
            new Dialogue(R.string.diaGreeting,
                    new ResponseQuestion(R.string.responseApple, R.string.responseAQuestion),
                    new ResponseQuestion(R.string.responseBanana, R.string.responseBQuestion)),
            new Dialogue(R.string.responseAQuestion,
                    new ResponseQuestion(R.string.AppleChoiceA, R.string.fiveappleQuestion),
                    new ResponseQuestion(R.string.AppleChoiceB, R.string.sixappleQuestion)),
            new Dialogue(R.string.responseBQuestion,
                    new ResponseQuestion(R.string.BananaChoiceA, R.string.fivebananaQuestion),
                    new ResponseQuestion(R.string.BananaChoiceB, R.string.sixbananaQuestion)),
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
//            button1.setText("nothing");
//            button2.setText("nothing");
        }else{
            button1.setText(responseA);
            button2.setText(responseB);
        }
    }
}

