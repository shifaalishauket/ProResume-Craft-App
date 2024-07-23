package com.pro.resume.craft.fragments.cvdata.languages;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pro.resume.craft.databinding.DataItemViewBinding;
import com.pro.resume.craft.models.DTOlanguages;

import java.util.List;

public class LanguagesAdapter extends RecyclerView.Adapter<LanguagesAdapter.ExperienceViewHolder> {

    private List<DTOlanguages> experienceList;
    private final OnExperienceClickListener clickListener;

    public LanguagesAdapter(OnExperienceClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void submitList(List<DTOlanguages> experienceList) {
        this.experienceList = experienceList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LanguagesAdapter.ExperienceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DataItemViewBinding binding = DataItemViewBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new LanguagesAdapter.ExperienceViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull LanguagesAdapter.ExperienceViewHolder holder, int position) {
        DTOlanguages hobbies = experienceList.get(position);
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

        public void bind(DTOlanguages experience, OnExperienceClickListener clickListener) {
            binding.title.setText(experience.getLanguageName());
            binding.subtitle.setText(experience.getLanguageLevel());
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
        void onExperienceClick(DTOlanguages experience);
    }
}