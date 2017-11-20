package com.stablekernel.interview.ui.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.stablekernel.interview.R;
import com.stablekernel.interview.api.InterviewWebService;
import com.stablekernel.interview.api.model.LoginCredentials;
import com.stablekernel.interview.api.model.Profile;
import com.stablekernel.interview.api.model.TokenResponse;
import com.stablekernel.interview.InterviewApplication;
import com.stablekernel.interview.ui.profile.ProfileActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.HTTP;

/*
    Use the following instructions to complete the implementation of LoginActivity.
    - The instructions may or may not be complete and exact.
    - The methods you use to complete the exercise are not as important as letting us
      know the processes you are using to accomplish the task.  Talk through your
      approach to finding a solution.
    - You can use any resources you deem necessary.
    - You need to let us know what resources you are using:  screenshare your documentation
      searches, talk through using an offline reference (book, etc.), talk though the it if
      you are familiar with the methods you need to use, etc.

    1. Create XML layout file for this activity.
        - The layout should contain at least
            * A text field where the user can enter a username.
            * A text field where the user can enter a password.
            * A button to allow the user to indicate they wish to login.
            * A checkbox to allow the user to indicate they wish to save their username for
              future use.
    2. The values of all input fields should be persisted when the user rotates their screen.
    3. Define and implement the method isInputValid()
        - The method should take the username and password as input.
        - The method should return a boolean value which indicates whether input is valid.
        - Valid username input is defined as a string of non-zero length containing 1 or
          more non-whitespace characters.
        - Valid password input is defined as a string of non-zero length containing 1 or
          more non-whitespace characters.
    4. When the user clicks the login button a number of things should happen in the
       appropriate sequence:
        - User input is validated.
        - User input is used to send a login request via the InterviewWebService.
        - The response from the login attempt is appropriately handled.
            * Focus on the happy path (success case) first.  If there is time, revisit any
              other cases.
        - If the login is successful, finish this Activity and start the ProfileActivity.
    5. Fix any errors you encounter along the way.

    For the purposes of testing your implementation use the username "user" and the
    password "pw" as valid login credentials.
 */

public final class LoginActivity extends AppCompatActivity {

    public static final String TAG = LoginActivity.class.getSimpleName();

    private InterviewWebService interviewWebService;

    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        interviewWebService = ((InterviewApplication) getApplication()).getInterviewWebService();

        mUsernameEditText = (EditText) findViewById(R.id.username_field);
        mPasswordEditText = (EditText) findViewById(R.id.password_field);

        mLoginButton = (Button) findViewById(R.id.login_button);
        mLoginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onLogin(mUsernameEditText.getText().toString(), mPasswordEditText.getText().toString());
            }
        });
    }

    private void onLogin(String username, String password) {
        Log.d(TAG, "onLogin() called with username = [" + username + "], password = [" + password + "]");

        if (!isInputValid(username, password)) {
            Log.d(TAG, "Invalid input");
            return;
        }

        LoginCredentials loginCredentials = new LoginCredentials(username, password);

        interviewWebService.login(loginCredentials)
                .enqueue(new Callback<TokenResponse>() {
                             @Override
                             public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                                 Log.d(TAG, "onResponse() called with: call = [" + call + "], response = [" + response + "]");
                                 if (response.code() == 200) {
                                     List<String> skills = new ArrayList<>();
                                     skills.add("skill 1");
                                     skills.add("skill 2");
                                     Profile profile = new Profile("Justin", .55, skills);
                                     ProfileActivity.start(LoginActivity.this, profile);
                                 }
                             }

                             @Override
                             public void onFailure(Call<TokenResponse> call, Throwable t) {
                                 Log.d(TAG, "onFailure() called with: call = [" + call + "], t = [" + t + "]");
                             }
                         }
                );
    }

    boolean isInputValid(String username, String password) {
        return username.matches("[\\S]+") && password.matches("[\\S]+");
    }
}

