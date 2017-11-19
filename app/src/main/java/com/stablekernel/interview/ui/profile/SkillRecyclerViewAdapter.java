package com.stablekernel.interview.ui.profile;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

public final class SkillRecyclerViewAdapter extends RecyclerView.Adapter<SkillViewHolder> {

    private final List<String> skills;

    public SkillRecyclerViewAdapter(List<String> skills) {
        this.skills = skills;
    }

    @Override
    public SkillViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SkillViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(SkillViewHolder holder, int position) {
        String skill = skills.get(position);
        holder.bind(skill);
    }

    @Override
    public int getItemCount() {
        return skills.size();
    }
}
