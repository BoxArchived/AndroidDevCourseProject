package dev.boxz.kingofkaraoke;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.auth0.android.Auth0;
import com.auth0.android.authentication.AuthenticationAPIClient;
import com.auth0.android.authentication.AuthenticationException;
import com.auth0.android.callback.Callback;
import com.auth0.android.provider.WebAuthProvider;
import com.auth0.android.result.Credentials;
import com.auth0.android.result.UserProfile;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private Auth0 auth0;
    private static User user;
    Button loginBtn;
    Button logoutBtn;
    Button startBtn;
    TextView textView;
    public static int version;
    public static SharedPreferences sharedPreferences;

    final String VERSION_API="https://cisc3002api.boxz.dev/version";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth0=new Auth0(getString(R.string.com_auth0_client_id),getString(R.string.com_auth0_domain));
        File file=new File(getFilesDir(),Question.FILE_NAME);
        if (file.exists()){
            try {
                BufferedReader bufferedReader=new BufferedReader(new FileReader(file));
                String inString;
                StringBuilder sb = new StringBuilder();
                while ((inString = bufferedReader.readLine()) != null) {
                    sb.append(inString);
                }
                JSONArray array=new JSONArray(sb.toString());
                Question.questionArrayList=new ArrayList<>();
                for (int i = 0; i < array.length(); i++) {
                    Question.questionArrayList.add(new Question(array.getJSONObject(i)));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            ArrayList<Question> questionArrayList=new ArrayList<>();
            Random random=new Random();
            while (questionArrayList.size()<=Math.min(8,Question.questionArrayList.size())){
                int  result=random.nextInt(Question.questionArrayList.size());
                if (!questionArrayList.contains(Question.questionArrayList.get(result))){
                    questionArrayList.add(Question.questionArrayList.get(result));
                }
            }
            Question.questionArrayList=questionArrayList;
            Question.generateOption();
        }

        user=new User();
        startBtn=findViewById(R.id.startQuizBtn);
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder().url(VERSION_API).build();
        File versionFile=new File(getFilesDir(),"version");
        if (!versionFile.exists()){
            try {
                versionFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            version=-1;
            startBtn.setEnabled(false);
        }
        else {
            try {
                BufferedReader bufferedReader=new BufferedReader(new FileReader(versionFile));
                String inString;
                StringBuilder sb = new StringBuilder();
                while ((inString = bufferedReader.readLine()) != null) {
                    sb.append(inString);
                }
                version=Integer.parseInt(sb.toString());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String data=response.body().string();
                JSONObject jsonObject= null;
                try {
                    jsonObject = new JSONObject(data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    if (jsonObject.getInt("version")>version){
                        version=jsonObject.getInt("version");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent=new Intent(getApplicationContext(),UpdateActivity.class);
                                startActivity(intent);
                            }
                        });
                    }
                    else {
                        File file=new File(getFilesDir(),Question.FILE_NAME);
                        try {
                            BufferedReader bufferedReader=new BufferedReader(new FileReader(file));
                            String inString;
                            StringBuilder sb = new StringBuilder();
                            while ((inString = bufferedReader.readLine()) != null) {
                                sb.append(inString);
                            }
                            JSONArray array=new JSONArray(sb.toString());
                            Question.questionArrayList=new ArrayList<>();
                            for (int i = 0; i < array.length(); i++) {
                                Question.questionArrayList.add(new Question(array.getJSONObject(i)));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        ArrayList<Question> questionArrayList=new ArrayList<>();
                        Random random=new Random();
                        while (questionArrayList.size()<=Math.min(8,Question.questionArrayList.size())){
                            int  result=random.nextInt(Question.questionArrayList.size());
                            if (!questionArrayList.contains(Question.questionArrayList.get(result))){
                                questionArrayList.add(Question.questionArrayList.get(result));
                            }
                        }
                        Question.questionArrayList=questionArrayList;
                        Question.generateOption();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        loginBtn=findViewById(R.id.loginBtn);
        logoutBtn=findViewById(R.id.logoutBtn);
        textView=findViewById(R.id.infoTextView);
        loginBtn.setEnabled(true);
        logoutBtn.setEnabled(false);
        startBtn.setEnabled(false);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();

            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();

            }
        });

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Question.questionArrayList.size()==0){
                    Toast.makeText(getApplicationContext(),"Need to update",Toast.LENGTH_LONG);
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), QuizActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void login(){
        WebAuthProvider.login(auth0)
                .withScheme("demo")
                .withAudience(String.format("https://%s/userinfo", getString(R.string.com_auth0_domain)))
                .start(this, new Callback<Credentials, AuthenticationException>() {
                    @Override
                    public void onSuccess(Credentials credentials) {
                        if (user==null){
                            user=new User();
                        }
                        user.setAccessToken(credentials.getAccessToken());
                        user.setIdToken(credentials.getIdToken());
                        AuthenticationAPIClient authenticationAPIClient=new AuthenticationAPIClient(auth0);
                        authenticationAPIClient.userInfo(user.getAccessToken())
                                .start(new Callback<UserProfile, AuthenticationException>() {
                                    @Override
                                    public void onSuccess(UserProfile userProfile) {
                                        user.setEmail(userProfile.getEmail());
                                        user.setUsername(userProfile.getName());
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (user!=null){
                                                    loginBtn.setEnabled(false);
                                                    logoutBtn.setEnabled(true);
                                                    startBtn.setEnabled(true);
                                                    textView.setText(user.getUsername()+", you can start your quiz now!");
                                                }
                                            }
                                        });
                                    }

                                    @Override
                                    public void onFailure(AuthenticationException e) {

                                    }
                                });


                    }

                    @Override
                    public void onFailure(AuthenticationException e) {
                        Toast.makeText(MainActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    protected void onResume() {
        super.onResume();

        Question.generateOption();
    }

    private void logout() {
        WebAuthProvider.logout(auth0)
                .withScheme("demo")
                .start(this, new Callback<Void, AuthenticationException>() {
                    @Override
                    public void onSuccess(@Nullable Void payload) {
                        user=null;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                loginBtn.setEnabled(true);
                                logoutBtn.setEnabled(false);
                                startBtn.setEnabled(false);
                                textView.setText("Logout successfully");
                                for (int i = 0; i < Question.userAnswer.size(); i++) {
                                    Question.userAnswer.set(i,4);
                                }
                            }
                        });
                    }

                    @Override
                    public void onFailure(@NonNull AuthenticationException error) {

                    }
                });

    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        MainActivity.user = user;
    }
}