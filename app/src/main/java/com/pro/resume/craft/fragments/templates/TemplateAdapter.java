package com.pro.resume.craft.fragments.templates;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pro.resume.craft.databinding.ListItemTempBinding;

public class TemplateAdapter extends RecyclerView.Adapter<TemplateAdapter.TemplateViewHolder> {

    private final int[] imageResources;

    public TemplateAdapter(int[] imageResources) {
        this.imageResources = imageResources;
    }

    @NonNull
    @Override
    public TemplateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemTempBinding binding = ListItemTempBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new TemplateViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TemplateViewHolder holder, int position) {
        holder.bind(imageResources[position]);
    }

    @Override
    public int getItemCount() {
        return imageResources.length;
    }

    static class TemplateViewHolder extends RecyclerView.ViewHolder {
        private final ListItemTempBinding binding;

        public TemplateViewHolder(ListItemTempBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(int imageResId) {
            binding.tempImage.setImageResource(imageResId);
        }
    }
}