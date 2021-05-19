package dev.boxz.kingofkaraoke;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;

import okhttp3.OkHttpClient;

public class QuizActivity extends AppCompatActivity {
    public static CountDownTimer countDownTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        ViewPager2 viewPager2=findViewById(R.id.viewPage);

        QuizAdapter quizAdapter=new QuizAdapter(QuizActivity.this,Question.questionArrayList);
        viewPager2.setAdapter(quizAdapter);


    }

    @Override
    protected void onResume() {
        ViewPager2 viewPager2=findViewById(R.id.viewPage);
        QuizAdapter quizAdapter=new QuizAdapter(QuizActivity.this,Question.questionArrayList);
        viewPager2.setAdapter(quizAdapter);
        countDownTimer=new CountDownTimer(300000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                TextView textView=findViewById(R.id.timerTextView);
                textView.setText(millisUntilFinished/1000+"s/300s");
            }

            @Override
            public void onFinish() {
                Intent intent1=new Intent();
                intent1.setClass(getApplicationContext(),ScoreActivity.class);
                startActivity(intent1);
            }
        };
        countDownTimer.start();
        super.onResume();

    }

    @Override
    protected void onPause() {
        countDownTimer.cancel();
        countDownTimer=null;
        super.onPause();
    }
}