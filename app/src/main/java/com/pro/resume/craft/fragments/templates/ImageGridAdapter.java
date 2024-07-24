package com.pro.resume.craft.fragments.templates;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pro.resume.craft.databinding.CvItemViewBinding;

public class ImageGridAdapter extends RecyclerView.Adapter<ImageGridAdapter.ImageViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private final int[] imageResources;
    private final OnItemClickListener onItemClickListener;

    public ImageGridAdapter(int[] imageResources, OnItemClickListener onItemClickListener) {
        this.imageResources = imageResources;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CvItemViewBinding binding = CvItemViewBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ImageViewHolder(binding, onItemClickListener);
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

        public ImageViewHolder(CvItemViewBinding binding, OnItemClickListener onItemClickListener) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(getAdapterPosition());
                }
            });
        }

        public void bind(int imageResId) {
            binding.cvPreview.setImageResource(imageResId);
        }
    }
}
