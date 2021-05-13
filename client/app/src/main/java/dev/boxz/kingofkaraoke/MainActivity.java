package dev.boxz.kingofkaraoke;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth0=new Auth0(getString(R.string.com_auth0_client_id),getString(R.string.com_auth0_domain));
        user=new User();

        Button loginBtn=findViewById(R.id.loginBtn);
        Button logoutBtn=findViewById(R.id.logoutBtn);
        loginBtn.setEnabled(true);
        logoutBtn.setEnabled(false);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
                if (user!=null){
                    loginBtn.setEnabled(false);
                    logoutBtn.setEnabled(true);

                }
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
                if (user==null){
                    loginBtn.setEnabled(true);
                    logoutBtn.setEnabled(false);
                }
            }
        });
    }

    private void login(){
        Toast.makeText(MainActivity.this, "Error: ", Toast.LENGTH_SHORT).show();
        WebAuthProvider.login(auth0)
                .withScheme("demo")
                .withAudience(String.format("https://%s/userinfo", getString(R.string.com_auth0_domain)))
                .start(this, new Callback<Credentials, AuthenticationException>() {
                    @Override
                    public void onSuccess(Credentials credentials) {
                        user.setAccessToken(credentials.getAccessToken());
                        user.setIdToken(credentials.getIdToken());
                        AuthenticationAPIClient authenticationAPIClient=new AuthenticationAPIClient(auth0);
                        authenticationAPIClient.userInfo(user.getAccessToken())
                                .start(new Callback<UserProfile, AuthenticationException>() {
                                    @Override
                                    public void onSuccess(UserProfile userProfile) {
                                        user.setEmail(userProfile.getEmail());
                                        user.setUsername(userProfile.getName());
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
                    }

                    @Override
                    public void onFailure(@NonNull AuthenticationException error) {

                    }
                });
    }
}