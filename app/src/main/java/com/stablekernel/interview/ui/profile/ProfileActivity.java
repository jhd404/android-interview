package com.stablekernel.interview.ui.profile;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.stablekernel.interview.R;
import com.stablekernel.interview.api.model.Profile;
import com.stablekernel.interview.service.ProfileCallback;
import com.stablekernel.interview.service.ProfileService;

/*
    After you've finished implementing LoginActivity, you have some tasks here.
    - The instructions may or may not be complete and exact.
    - The methods you use to complete the exercise are not as important as letting us
      know the processes you are using to accomplish the task.  Talk through your
      approach to finding a solution.
    - You can use any resources you deem necessary.
    - You need to let us know what resources you are using:  screenshare your documentation
      searches, talk through using an offline reference (book, etc.), talk though the it if
      you are familiar with the methods you need to use, etc.

    1. Use ProfileFragment.
 */

public final class ProfileActivity extends AppCompatActivity {
    public static final String TAG = ProfileActivity.class.getSimpleName();

    private static final String EXTRA_PROFILE = "com.stablekernel.interview.EXTRA_PROFILE";

    ProfileService profileService;
    boolean mBound = false;

    public static void start(Context context) {
        Intent profileIntent = new Intent(context, ProfileActivity.class);
        context.startActivity(profileIntent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_profile);

        ActionBar ab = getSupportActionBar();
        ab.setTitle(R.string.actionbar_profile);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
        Intent intent = new Intent(this, ProfileService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
        unbindService(mConnection);
        mBound = false;
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected");
            ProfileService.ProfileBinder binder = (ProfileService.ProfileBinder) service;
            profileService = binder.getService();
            mBound = true;

            FragmentManager fm = getSupportFragmentManager();
            Fragment fragment = fm.findFragmentById(R.id.fragment_container);

            if (fragment == null) {
                fragment = ProfileFragment.newInstance();
                fm.beginTransaction()
                        .add(R.id.fragment_container, fragment)
                        .commit();
            }
            Log.d(TAG, "Bound to service");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
        }
    };



}
