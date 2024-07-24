package com.pro.resume.craft.fragments.cvdata.qualification;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pro.resume.craft.databinding.DataItemViewBinding;
import com.pro.resume.craft.models.DTOEducation;

import java.util.List;

public class EducationAdapter extends RecyclerView.Adapter<EducationAdapter.ExperienceViewHolder> {

    private List<DTOEducation> experienceList;
    private final OnExperienceClickListener clickListener;

    public EducationAdapter(OnExperienceClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void submitList(List<DTOEducation> experienceList) {
        this.experienceList = experienceList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ExperienceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DataItemViewBinding binding = DataItemViewBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ExperienceViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ExperienceViewHolder holder, int position) {
        DTOEducation experience = experienceList.get(position);
        holder.bind(experience, clickListener);
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

        public void bind(DTOEducation experience, OnExperienceClickListener clickListener) {
            binding.title.setText(experience.getDegree());
            binding.subtitle.setText(experience.getUniversity());
            binding.date.setText(experience.getStartDate() + " - " + experience.getEndDate());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onExperienceClick(experience);
                }
            });
        }
    }

    public interface OnExperienceClickListener {
        void onExperienceClick(DTOEducation experience);
    }
}
