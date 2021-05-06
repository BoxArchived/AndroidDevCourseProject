package dev.boxz.kingofkaraoke;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Main extends AppCompatActivity {

    static final String SERVER_URL="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences=getPreferences(Context.MODE_PRIVATE);


//        Integer version=sharedPreferences.getInt("version",-1);
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder().url(SERVER_URL+"/version").build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Toast.makeText(getApplicationContext(),"Can not connect the server!",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String data=response.body().string();
                Gson gson=new Gson();
                Type type=new TypeToken<Version>(){}.getType();
                Version v=gson.fromJson(data,type);
                if (v.getVersion()>sharedPreferences.getInt("version",-1)){
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putInt("version",v.getVersion());

                }
            }
        });
    }
}