package dev.boxz.kingofkaraoke;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;

import okhttp3.OkHttpClient;

public class QuizActivity extends AppCompatActivity {
    final String  API_URL="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Intent intent=new Intent(this,UpdateActivity.class);
        startActivity(intent);
        Button previousBtn=findViewById(R.id.previousBtn);
        Button nextBtn=findViewById(R.id.nextBtn);
        Button submitBtn=findViewById(R.id.submitBtn);
        ViewPager2 viewPager2=findViewById(R.id.viewPage);
        QuizAdapter quizAdapter=new QuizAdapter(QuizActivity.this,Question.questionArrayList);
        viewPager2.setAdapter(quizAdapter);

    }
}