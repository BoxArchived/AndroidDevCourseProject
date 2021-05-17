package dev.boxz.kingofkaraoke;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        User user=MainActivity.getUser();
        TextView scoreTextView=findViewById(R.id.scoreTextView);
        ImageView imageView=findViewById(R.id.conView);
        TextView rankTextView=findViewById(R.id.rankTextView);
        RecyclerView recyclerView=findViewById(R.id.recyclerView);
        int sum=0;
        for (int i = 0; i < Question.isCorrectList.size(); i++) {
            if (Question.isCorrectList.get(i)){
                sum++;
            }
        }
        String msg="User: "+ user.getUsername()+"\n"+
                "Email: "+user.getEmail()+"\n"+
                "Score: "+ sum+"/ "+Question.questionArrayList.size();
        scoreTextView.setText(msg);
        sum=Question.questionArrayList.size();
        if (sum==Question.questionArrayList.size()){
            imageView.setImageResource(R.drawable.congiatulations);
        }

    }
}