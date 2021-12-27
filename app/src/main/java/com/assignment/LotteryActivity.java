package com.assignment;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class LotteryActivity extends AppCompatActivity {
    int rn1,rn2,rn3,rn4,rn5,rn6, un1,un2,un3,un4,un5,un6;
    Random r = new Random();
    TextView ans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottery);
        ans = (TextView)findViewById(R.id.ans);
    }
    public void check(View view) {

        rn1 = r.nextInt(9);
        un1 = Integer.parseInt(((EditText)findViewById(R.id.num1)).getText().toString());
        rn2 = r.nextInt(9);
        un2 = Integer.parseInt(((EditText)findViewById(R.id.num2)).getText().toString());
        rn3 = r.nextInt(9);
        un3 = Integer.parseInt(((EditText)findViewById(R.id.num3)).getText().toString());
        rn4 = r.nextInt(9);
        un4 = Integer.parseInt(((EditText)findViewById(R.id.num4)).getText().toString());
        rn5 = r.nextInt(9);
        un5 = Integer.parseInt(((EditText)findViewById(R.id.num5)).getText().toString());
        rn6 = r.nextInt(9);
        un6 = Integer.parseInt(((EditText)findViewById(R.id.num6)).getText().toString());

        char[] match = {'x','x','x','x','x','x'};

        if(rn1 == un1){
            match[0] = 'm';
        }
        if(rn2 == un2){
            match[1] = 'm';
        }
        if(rn3 == un3){
            match[2] = 'm';
        }
        if(rn4 == un4){
            match[3] = 'm';
        }
        if(rn5 == un5){
            match[4] = 'm';
        }
        if(rn6 == un6){
            match[5] = 'm';
        }
        ans.setText("Computer's number is\n"+rn1+"|"+rn2+"|"+rn3+"|"+rn4+"|"+rn5+"|"+rn6+"|"+"\n"+"User's number is\n"+un1+"|"+un2+"|"+un3+"|"+un4+"|"+un5+"|"+un6+"|"+"\n"+"match is\n"+match[0]+match[1]+match[2]+match[3]+match[4]+match[5]);
    }
}