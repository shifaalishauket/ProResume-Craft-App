package com.pro.resume.craft.fragments.cvdata.skills;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pro.resume.craft.databinding.DataItemViewBinding;
import com.pro.resume.craft.models.DTOSkills;

import java.util.List;

public class SkillsAdapter extends RecyclerView.Adapter<SkillsAdapter.ExperienceViewHolder> {

    private List<DTOSkills> experienceList;
    private final OnExperienceClickListener clickListener;

    public SkillsAdapter(OnExperienceClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void submitList(List<DTOSkills> experienceList) {
        this.experienceList = experienceList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ExperienceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DataItemViewBinding binding = DataItemViewBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new SkillsAdapter.ExperienceViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ExperienceViewHolder holder, int position) {
        DTOSkills hobbies = experienceList.get(position);
        holder.bind(hobbies, clickListener);
    }

    @Override
    public int getItemCount() {
        return experienceList != null ? experienceList.size() : 0;
    }

    static class ExperienceViewHolder extends RecyclerView.ViewHolder {
        private final DataItemViewBinding binding;

        public ExperienceViewHolder(DataItemViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(DTOSkills experience, OnExperienceClickListener clickListener) {
            binding.title.setText(experience.getSkill());
            binding.subtitle.setText(experience.getLevel());
            binding.date.setVisibility(View.GONE);
            binding.options.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onExperienceClick(experience);
                }
            });
        }
    }

    public interface OnExperienceClickListener {
        void onExperienceClick(DTOSkills experience);
    }
}
