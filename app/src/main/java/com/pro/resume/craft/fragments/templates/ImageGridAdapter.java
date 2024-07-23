package com.pro.resume.craft.fragments.templates;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pro.resume.craft.databinding.CvItemViewBinding;


public class ImageGridAdapter extends RecyclerView.Adapter<ImageGridAdapter.ImageViewHolder> {

    private final int[] imageResources;

    public ImageGridAdapter(int[] imageResources) {
        this.imageResources = imageResources;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CvItemViewBinding binding = CvItemViewBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ImageViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        holder.bind(imageResources[position]);
    }

    @Override
    public int getItemCount() {
        return imageResources.length;
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {
        private final CvItemViewBinding binding;

        public ImageViewHolder(CvItemViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(int imageResId) {
            binding.cvPreview.setImageResource(imageResId);
        }
    }
}