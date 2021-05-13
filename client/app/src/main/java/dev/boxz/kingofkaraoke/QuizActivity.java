package dev.boxz.kingofkaraoke;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

public class QuizActivity extends AppCompatActivity {
    final String  API_URL="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Intent intent=new Intent(this,UpdateActivity.class);
        startActivity(intent);

    }
}