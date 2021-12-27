package com.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class UserChatActivity extends AppCompatActivity {
    ListView chat_container;
    EditText message;
    private ArrayList<String> Messages;
    private ArrayAdapter adapter;
    public UserChatActivity()
    {
        this.Messages = new ArrayList<>();
        this.adapter = null;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_chat);


        Intent i = getIntent();
        this.getSupportActionBar().setTitle(i.getStringExtra("name").toString());
        this.Messages.add(i.getStringExtra("message"));

        chat_container = findViewById(R.id.chat_container);
        chat_container.setDivider(null);
        message = findViewById(R.id.message);
        this.setAdapter();
    }

    public void setAdapter() {
        this.adapter = new ArrayAdapter(UserChatActivity.this, R.layout.message_list, Messages);
        chat_container.setAdapter(this.adapter);
    }

    public void send_message(View view) {
        String msg = message.getText().toString().trim();
        if(msg.equals(""))
            return;
        this.Messages.add(msg);
        this.setAdapter();
        Vibrator v = (Vibrator) view.getContext().getSystemService(Context.VIBRATOR_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(500);
        }
        message.setText("");
    }
}