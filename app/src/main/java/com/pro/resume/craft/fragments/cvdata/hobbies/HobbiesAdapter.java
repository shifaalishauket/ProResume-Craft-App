package com.pro.resume.craft.fragments.cvdata.hobbies;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pro.resume.craft.databinding.DataItemViewBinding;
import com.pro.resume.craft.models.DTOExperience;
import com.pro.resume.craft.models.DTOHobbies;

import java.util.List;

public class HobbiesAdapter extends RecyclerView.Adapter<HobbiesAdapter.ExperienceViewHolder> {

    private List<DTOHobbies> experienceList;
    private final OnExperienceClickListener clickListener;

    public HobbiesAdapter(OnExperienceClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void submitList(List<DTOHobbies> experienceList) {
        this.experienceList = experienceList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HobbiesAdapter.ExperienceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DataItemViewBinding binding = DataItemViewBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new HobbiesAdapter.ExperienceViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HobbiesAdapter.ExperienceViewHolder holder, int position) {
        DTOHobbies hobbies = experienceList.get(position);
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

        public void bind(DTOHobbies experience, OnExperienceClickListener clickListener) {
            binding.title.setText(experience.getHobbyName());
            binding.subtitle.setVisibility(View.GONE);
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
        void onExperienceClick(DTOHobbies experience);
    }
}
