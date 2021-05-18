package dev.boxz.kingofkaraoke;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.InputStream;
import java.util.ArrayList;

import okhttp3.OkHttpClient;

public class QuizActivity extends AppCompatActivity {

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
        super.onResume();

    }
}