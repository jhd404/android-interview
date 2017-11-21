package com.stablekernel.interview.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.stablekernel.interview.R;
import com.stablekernel.interview.api.model.Profile;

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

    public static void start(Context context, Profile profile) {
        Intent profileIntent = new Intent(context, ProfileActivity.class);
        profileIntent.putExtra(EXTRA_PROFILE, profile);
        context.startActivity(profileIntent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Profile profile = getIntent().getParcelableExtra(EXTRA_PROFILE);

        Log.d(TAG, "ProfileActivity onCreate() called with profile for [" + profile.getName() + "]");

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = ProfileFragment.newInstance(profile);
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}
