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
    final String  API_URL="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Button previousBtn=findViewById(R.id.previousBtn);
        Button nextBtn=findViewById(R.id.nextBtn);
        Button submitBtn=findViewById(R.id.submitBtn);
        ViewPager2 viewPager2=findViewById(R.id.viewPage);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager2.getCurrentItem()<Question.questionArrayList.size()) {
                    viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1, true);
                }
            }
        });
        previousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager2.getCurrentItem()>0) {
                    viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1, true);
                }
            }
        });
        QuizAdapter quizAdapter=new QuizAdapter(QuizActivity.this,Question.questionArrayList);
        viewPager2.setAdapter(quizAdapter);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent();
                intent1.setClass(QuizActivity.this,ScoreActivity.class);
                startActivity(intent1);

            }
        });

    }
}