package com.pro.resume.craft.fragments.cvdata.reference;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pro.resume.craft.databinding.DataItemViewBinding;
import com.pro.resume.craft.models.DTOReference;

import java.util.List;

public class ReferenceAdapter extends RecyclerView.Adapter<ReferenceAdapter.ExperienceViewHolder> {

    private List<DTOReference> experienceList;
    private final OnExperienceClickListener clickListener;

    public ReferenceAdapter(OnExperienceClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void submitList(List<DTOReference> experienceList) {
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
        DTOReference experience = experienceList.get(position);
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

        public void bind(DTOReference experience, OnExperienceClickListener clickListener) {
            binding.title.setText(experience.getReffName());
            binding.subtitle.setText(experience.getReffDesignation());
            binding.date.setText(experience.getReffCompanyName());
            binding.options.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onExperienceClick(experience);
                }
            });
        }
    }

    public interface OnExperienceClickListener {
        void onExperienceClick(DTOReference experience);
    }
}
