package dev.boxz.kingofkaraoke;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {

    private Auth0 auth0;
    private static User user;
    Button loginBtn;
    Button logoutBtn;
    Button startBtn;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth0=new Auth0(getString(R.string.com_auth0_client_id),getString(R.string.com_auth0_domain));
        user=new User();

        loginBtn=findViewById(R.id.loginBtn);
        logoutBtn=findViewById(R.id.logoutBtn);
        startBtn=findViewById(R.id.startQuizBtn);
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
                Intent intent=new Intent(getApplicationContext(),QuizActivity.class);
                startActivity(intent);
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
                            }
                        });
                    }

                    @Override
                    public void onFailure(@NonNull AuthenticationException error) {

                    }
                });

    }
}