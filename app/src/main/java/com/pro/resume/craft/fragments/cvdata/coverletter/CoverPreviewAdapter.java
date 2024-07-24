package com.pro.resume.craft.fragments.cvdata.coverletter;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pro.resume.craft.databinding.CvItemViewBinding;
import com.pro.resume.craft.models.DTOCoverLetter;
import com.pro.resume.craft.utils.BitmapTypeConverter;

import java.util.List;


public class CoverPreviewAdapter extends RecyclerView.Adapter<CoverPreviewAdapter.ImageViewHolder> {

    private List<DTOCoverLetter> coverLetters;

    private final OnCoverClickListener clickListener;

    public CoverPreviewAdapter(OnCoverClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void submitList(List<DTOCoverLetter> experienceList) {
        this.coverLetters = experienceList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CoverPreviewAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CvItemViewBinding binding = CvItemViewBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new CoverPreviewAdapter.ImageViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CoverPreviewAdapter.ImageViewHolder holder, int position) {
        DTOCoverLetter dtoSavedResumes = coverLetters.get(position);
        Bitmap bitmap = BitmapTypeConverter.toBitmap(coverLetters.get(position).imageData);
        holder.bind(bitmap);

        holder.binding.cvPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onCoverClick(dtoSavedResumes);
            }
        });
    }

    @Override
    public int getItemCount() {
        return  coverLetters != null ? coverLetters.size() : 0;
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

    public interface OnCoverClickListener {
        void onCoverClick(DTOCoverLetter dtoSavedResumes);
    }
}
