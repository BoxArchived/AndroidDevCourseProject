package dev.boxz.kingofkaraoke;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.auth0.android.Auth0;
import com.auth0.android.authentication.AuthenticationException;
import com.auth0.android.callback.Callback;
import com.auth0.android.provider.WebAuthProvider;
import com.auth0.android.result.Credentials;

import org.jetbrains.annotations.NotNull;

public class Login extends AppCompatActivity {
    private Auth0 auth0;

    public static final String EXTRA_CLEAR_CREDENTIALS = "com.auth0.CLEAR_CREDENTIALS";
    public static final String EXTRA_ACCESS_TOKEN = "com.auth0.ACCESS_TOKEN";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth0=new Auth0(this);
        Button login=findViewById(R.id.loginButton);
        Login context=this;
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebAuthProvider.login(auth0)
                        .withScheme(getString(R.string.com_auth0_scheme))
                        .withAudience(String.format("https://%s/userinfo", getString(R.string.com_auth0_domain)))
                        .start(context, new Callback<Credentials, AuthenticationException>() {
                            @Override
                            public void onSuccess(Credentials credentials) {
                                Intent intent = new Intent(Login.this, Main.class);
                                intent.putExtra(EXTRA_ACCESS_TOKEN, credentials.getAccessToken());
                                startActivity(intent);
                                finish();
                            }

                            @Override
                            public void onFailure(@NotNull AuthenticationException e) {
                                Toast.makeText(Login.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        Button logout=findViewById(R.id.logoutButton);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebAuthProvider.logout(auth0)
                        .withScheme(getString(R.string.com_auth0_scheme))
                        .start(getApplicationContext(), new Callback<Void, AuthenticationException>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                            }

                            @Override
                            public void onFailure(@NotNull AuthenticationException e) {
                                Toast.makeText(Login.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}