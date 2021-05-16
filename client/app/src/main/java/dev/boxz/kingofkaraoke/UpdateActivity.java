package dev.boxz.kingofkaraoke;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UpdateActivity extends AppCompatActivity {

    String API_URL="";
    String FILE_URL="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        Button okBtn=findViewById(R.id.ok);
        Button noBtn=findViewById(R.id.no);
        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpClient client=new OkHttpClient();
                Request request=new Request.Builder().url(API_URL).build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        String data=response.body().string();
                        try {
                            JSONArray array=new JSONArray(data);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject jsonObject=array.getJSONObject(i);
                                Question question=new Question(jsonObject);
                                Question.questionArrayList.add(question);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        

                    }
                });
            }
        });

    }
}