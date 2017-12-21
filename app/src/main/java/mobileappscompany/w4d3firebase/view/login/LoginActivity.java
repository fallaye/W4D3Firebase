package mobileappscompany.w4d3firebase.view.login;

/**
 * Created by fallaye on 12/20/17.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.auth.FirebaseAuth;

import java.io.InputStream;

import mobileappscompany.w4d3firebase.R;
import mobileappscompany.w4d3firebase.view.movie.MovieActivity;

public class LoginActivity extends AppCompatActivity implements LoginContract.View{

    public static final String TAG = "LoginActivity";

    EditText etEmail, etPassword;
    Button btnLogin, btnSignup;

    String email, password;
    FirebaseAuth firebaseAuth;
    LoginPresenter presenter;
    LoginAuthenticator loginAuthenticator;


    private TextView info;
    private LoginButton loginButton;

    private CallbackManager callbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bindViews();

        firebaseAuth = FirebaseAuth.getInstance();

        loginAuthenticator = new LoginAuthenticator(firebaseAuth, this);
        presenter = new LoginPresenter(loginAuthenticator);
        presenter.attachView(this);

        presenter.checkSession();


        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        info = (TextView)findViewById(R.id.info);
        loginButton = (LoginButton)findViewById(R.id.login_button);


        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                info.setText(
                        "User ID: "
                                + loginResult.getAccessToken().getUserId()
                                + "\n" +
                                "Auth Token: "
                                + loginResult.getAccessToken().getToken()
                );

            }

            @Override
            public void onCancel() {
                info.setText("Login attempt canceled.");

            }

            @Override
            public void onError(FacebookException e) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void bindViews() {
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignup);
    }


    public void onFirebaseSignUp(View view) {
        getCredentials();

        Log.d(TAG, "onFirebaseSignUP");

        presenter.createUser(email, password);
    }

    private void getCredentials() {
        email = etEmail.getText().toString();
        password = etPassword.getText().toString();
    }

    public void onFirebaseSignIn(View view) {
        getCredentials();
        presenter.validateUser(email, password);
    }


    @Override
    public void showError(String error) {

    }

    @Override
    public void onUserCreation(boolean isCreated) {
        Log.d(TAG, "onUserCreation: " + isCreated);

        if(isCreated){
            showToast("User Created");
        }else {
            showToast("User Creation failed");
        }
    }

    @Override
    public void onUserValidation(boolean isValid) {

        Log.d(TAG, "onUserValidation: " + isValid);
        if(isValid){
            showToast("Signed In");
        }else {
            showToast("Singed In failed");
        }

    }

    @Override
    public void isSessionValid(boolean isValid) {
        if(isValid){
            Intent intent = new Intent(getApplicationContext(), MovieActivity.class);

            startActivity(intent);
        }

    }

    public  void showToast(String message){
        Toast.makeText(this, message,  Toast.LENGTH_LONG).show();
    }
}
