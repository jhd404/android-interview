package com.stablekernel.interview.ui.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stablekernel.interview.R;
import com.stablekernel.interview.api.model.Profile;

/*
    After you have shown ProfileFragment, your app will crash.  Need to accomplish a few tasks here.
    - The instructions may or may not be complete and exact.
    - The methods you use to complete the exercise are not as important as letting us
      know the processes you are using to accomplish the task.  Talk through your
      approach to finding a solution.
    - You can use any resources you deem necessary.
    - You need to let us know what resources you are using:  screenshare your documentation
      searches, talk through using an offline reference (book, etc.), talk though the it if
      you are familiar with the methods you need to use, etc.

    1. Implement the newInstance method.
        - Use the newInstance method to create any instance of ProfileFragment.
    2. Complete the implementation of onCreateView and use the fragment_profile.xml layout to do so.
    3. This fragment requires an instance of Profile to function.  See that it gets here.
    4. Set the ActionBar title
 */


public final class ProfileFragment extends Fragment {

    public static final String TAG = ProfileFragment.class.getSimpleName();

    public static final String ARGUMENT_PROFILE = "com.stablekernel.interview.ARGUMENT_PROFILE";

    private TextView nameTextView;
    private TextView progressTextView;
    private RecyclerView skillsRecyclerView;

    public static ProfileFragment newInstance(Profile profile) {
        ProfileFragment profileFragment = new ProfileFragment();

        Bundle arguments = new Bundle();
        arguments.putParcelable(ARGUMENT_PROFILE, profile);
        profileFragment.setArguments(arguments);

        return profileFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // TODO: inflate the layout fragment_profile.xml and return the resulting view
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated() called with: view = [" + view + "], savedInstanceState = [" + savedInstanceState + "]");
        super.onViewCreated(view, savedInstanceState);

        Profile profile = null;

        Bundle arguments = getArguments();

        if (arguments != null) {
            profile = arguments.getParcelable(ARGUMENT_PROFILE);
        }

        nameTextView = (TextView) view.findViewById(R.id.profile_name_textView);
        progressTextView = (TextView) view.findViewById(R.id.profile_progress_textView);
        skillsRecyclerView = (RecyclerView) view.findViewById(R.id.profile_skills_recyclerView);
        skillsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        nameTextView.setText(profile.getName());
        progressTextView.setText(Double.toString(profile.getProgress()));

        skillsRecyclerView.setAdapter(new SkillRecyclerViewAdapter(profile.getSkills()));

        // TODO: set the title to something fun
    }
}
