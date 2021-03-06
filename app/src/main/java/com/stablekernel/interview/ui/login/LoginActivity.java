package com.stablekernel.interview.ui.login;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.stablekernel.interview.service.LoginCallback;
import com.stablekernel.interview.service.ProfileService;
import com.stablekernel.interview.R;
import com.stablekernel.interview.api.model.LoginCredentials;
import com.stablekernel.interview.ui.profile.ProfileActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    @BindView(R.id.username_field) EditText mUsernameEditText;
    @BindView(R.id.password_field) EditText mPasswordEditText;
    @BindView(R.id.login_button) Button mLoginButton;

    ProfileService profileService;
    boolean mBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        ActionBar ab = getSupportActionBar();
        ab.setTitle(R.string.actionbar_login);

        mLoginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onLogin(mUsernameEditText.getText().toString(), mPasswordEditText.getText().toString());
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, ProfileService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(mConnection);
        mBound = false;
    }

    private void onLogin(String username, String password) {
        Log.d(TAG, "onLogin() called with username = [" + username + "], password = [" + password + "]");

        if (!isInputValid(username, password)) {
            Log.d(TAG, "Invalid input");
            return;
        }

        LoginCredentials loginCredentials = new LoginCredentials(username, password);
        profileService.login(loginCredentials, mLoginCallback);
    }

    boolean isInputValid(String username, String password) {
        return username.matches("[\\S]+") && password.matches("[\\S]+");
    }

    private void invalidCredentialsToast() {
        Toast toast = Toast.makeText(LoginActivity.this,
                R.string.invalid_credentials_toast,
                Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 200);
        toast.show();
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            ProfileService.ProfileBinder binder = (ProfileService.ProfileBinder) service;
            profileService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
        }
    };

    private LoginCallback mLoginCallback = new LoginCallback() {
        @Override
        public void onSuccess(boolean credentialsAreValid) {
            if (credentialsAreValid) {
                Log.d(TAG, "VALID CREDS!");
                // Start Profile Activity
                ProfileActivity.start(LoginActivity.this);
            } else {
                invalidCredentialsToast();
            }
        }

        @Override
        public void onError(Throwable throwable) {

        }
    };

}

