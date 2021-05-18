package dev.boxz.kingofkaraoke;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ScoreActivity extends AppCompatActivity {
    final String API_URL="http://cisc3002api.boxz.dev/getrank";
    final String API_URL_SUBMIT="http://cisc3002api.boxz.dev/submitrank";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        User user=MainActivity.getUser();
        ImageView imageView=findViewById(R.id.conView);
        TextView rankTextView=findViewById(R.id.rankTextView);
        rankTextView.setText("Connecting the server");
        RecyclerView recyclerView=findViewById(R.id.recyclerView);
        int sum=0;
        for (int i = 0; i < Question.isCorrectList.size(); i++) {
            if (Question.isCorrectList.get(i)){
                sum++;
            }
        }
        if (sum==Question.questionArrayList.size()){
            imageView.setImageResource(R.drawable.congiatulations);
        }
        else {
            imageView.setVisibility(View.GONE);
        }
        ArrayList<User> users=new ArrayList<>();
        OkHttpClient post=new OkHttpClient();
        RequestBody requestBody= new FormBody.Builder()
                .add("username",user.getUsername())
                .add("email",user.getEmail())
                .add("score",String.valueOf(user.getScore()))
                .build();
        Request submit=new Request.Builder()
                .url(API_URL_SUBMIT)
                .post(requestBody)
                .build();
        post.newCall(submit).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                OkHttpClient client=new OkHttpClient();
                Request request=new Request.Builder().url(API_URL).build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        String data=response.body().string();
                        JSONArray jsonArray = null;
                        try {
                            jsonArray=new JSONArray(data);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject=jsonArray.getJSONObject(i);
                                User newUser=new User(jsonObject);
                                users.add(newUser);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        for (int i = 0; i < users.size()-1; i++) {
                            for (int j = i+1; j < users.size(); j++) {
                                if (users.get(j).getScore()>users.get(i).getScore()){
                                    User t=users.get(j);
                                    users.set(j,users.get(i));
                                    users.set(i,t);
                                }
                            }
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                recyclerView.setAdapter(new RankAdapter(users,user));
                                recyclerView.setNestedScrollingEnabled(false);
                                rankTextView.setText("Rank");
                            }
                        });


                    }
                });

            }
        });



    }
}