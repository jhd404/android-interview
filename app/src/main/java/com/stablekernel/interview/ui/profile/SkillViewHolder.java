package com.stablekernel.interview.ui.profile;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.stablekernel.interview.R;

public final class SkillViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final TextView skillNameTextView;
    private final CheckBox checkBox;

    public SkillViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_skill, parent, false));
        skillNameTextView = (TextView) itemView.findViewById(R.id.skill_name_textView);
        checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);
        itemView.setOnClickListener(this);
    }

    public void bind(String skill) {
        skillNameTextView.setText(skill);
    }

    @Override
    public void onClick(View v) {
        checkBox.toggle();
    }
}
