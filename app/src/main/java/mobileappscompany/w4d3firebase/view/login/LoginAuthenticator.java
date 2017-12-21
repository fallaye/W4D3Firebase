package mobileappscompany.w4d3firebase.view.login;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by fallaye on 12/20/17.
 */

public class LoginAuthenticator {

    FirebaseAuth mAuth;
    public static final String TAG = "LoginAuthenticator";
    LoginActivity loginActivity;

    OnLoginInteraction onLoginInteraction;
    FirebaseUser user;

    public LoginAuthenticator(FirebaseAuth auth, LoginActivity activity) {
        this.mAuth = auth;
        this.loginActivity = activity;
    }

   public void validateUser(String email, String password, LoginPresenter loginPresenter){



       mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(loginActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            user = mAuth.getCurrentUser();
                            onLoginInteraction.onUserValidation(user);
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            //Toast.makeText(loginActivity, "Authentication failed.",
                                    //Toast.LENGTH_SHORT).show();
                            onLoginInteraction.onUserValidation(user);
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }

   public void  createUser(String email, String password, LoginPresenter loginPresenter){

       mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(loginActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            user = mAuth.getCurrentUser();

                            onLoginInteraction.onUserCreation(user);
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure  " + task.getException().toString());
                            //Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    //Toast.LENGTH_SHORT).show();
                            onLoginInteraction.onUserCreation(user);
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }

    public void initInteractor(LoginPresenter loginPresenter) {
       onLoginInteraction = loginPresenter;
    }

    public void checkUser() {
        user = mAuth.getCurrentUser();
        if(user != null){
            onLoginInteraction.onSessionValid(true);
        }else {
            onLoginInteraction.onSessionValid(false);
        }
    }


    interface OnLoginInteraction{

       void onUserCreation(FirebaseUser user);
       void onUserValidation(FirebaseUser user);
       void onSessionValid(boolean isValid);

    }

}
