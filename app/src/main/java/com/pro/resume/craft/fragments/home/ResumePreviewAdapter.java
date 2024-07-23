package com.pro.resume.craft.fragments.home;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pro.resume.craft.databinding.CvItemViewBinding;
import com.pro.resume.craft.models.DTOSavedResumes;
import com.pro.resume.craft.utils.BitmapTypeConverter;

import java.util.List;


public class ResumePreviewAdapter extends RecyclerView.Adapter<ResumePreviewAdapter.ImageViewHolder> {

    private List<DTOSavedResumes> savedResumes;

    private final OnExperienceClickListener clickListener;

    public ResumePreviewAdapter(OnExperienceClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void submitList(List<DTOSavedResumes> experienceList) {
        this.savedResumes = experienceList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ResumePreviewAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CvItemViewBinding binding = CvItemViewBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ResumePreviewAdapter.ImageViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ResumePreviewAdapter.ImageViewHolder holder, int position) {
        DTOSavedResumes dtoSavedResumes = savedResumes.get(position);
        Bitmap bitmap = BitmapTypeConverter.toBitmap(savedResumes.get(position).imageData);
        holder.bind(bitmap);

        holder.binding.cvPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onExperienceClick(dtoSavedResumes);
            }
        });
    }

    @Override
    public int getItemCount() {
        return  savedResumes != null ? savedResumes.size() : 0;
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {
        private final CvItemViewBinding binding;

        public ImageViewHolder(CvItemViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Bitmap imageResId) {
            Glide.with(binding.getRoot().getContext()).load(imageResId).into(binding.cvPreview);
        }
    }

    public interface OnExperienceClickListener {
        void onExperienceClick(DTOSavedResumes dtoSavedResumes);
    }
}
