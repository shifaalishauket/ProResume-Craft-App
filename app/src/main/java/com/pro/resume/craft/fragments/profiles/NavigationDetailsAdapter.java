package com.pro.resume.craft.fragments.profiles;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pro.resume.craft.databinding.ProfileInfoItemViewBinding;
import com.pro.resume.craft.models.DummyModelDetails;

import java.util.List;

public class NavigationDetailsAdapter extends RecyclerView.Adapter<NavigationDetailsAdapter.ViewHolder> {

    private List<DummyModelDetails> dummyModelList;
    private OnItemClickListener onItemClickListener;

    public NavigationDetailsAdapter(List<DummyModelDetails> dummyModelList, OnItemClickListener onItemClickListener) {
        this.dummyModelList = dummyModelList;
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(DummyModelDetails dummyModel);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ProfileInfoItemViewBinding binding = ProfileInfoItemViewBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DummyModelDetails dummyModel = dummyModelList.get(position);
        holder.bind(dummyModel, onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return dummyModelList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private ProfileInfoItemViewBinding binding;

        public ViewHolder(ProfileInfoItemViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(final DummyModelDetails dummyModel, final OnItemClickListener listener) {
            binding.title.setText(dummyModel.getName());
            binding.icon.setImageResource(dummyModel.getImage());

            binding.objective.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(dummyModel);
                }
            });
        }
    }
}
