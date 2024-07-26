package com.pro.resume.craft.fragments.profiles;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pro.resume.craft.R;
import com.pro.resume.craft.databinding.ProfileItemViewBinding;
import com.pro.resume.craft.models.DTOProfile;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder> {

    private Context context;
    private List<DTOProfile> profileList;

    private OnOptionsMenuClick onOptionsMenuClick;


    public ProfileAdapter(Context context, List<DTOProfile> profileList, OnOptionsMenuClick optionsMenuClick) {
        this.context = context;
        this.profileList = profileList;
        this.onOptionsMenuClick = optionsMenuClick;
    }

    @NonNull
    @Override
    public ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProfileItemViewBinding binding = ProfileItemViewBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ProfileViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileViewHolder holder, int position) {
        DTOProfile profile = profileList.get(position);
        holder.bind(profile);
    }

    @Override
    public int getItemCount() {
        return profileList.size();
    }

    public class ProfileViewHolder extends RecyclerView.ViewHolder {

        private ProfileItemViewBinding binding;

        public ProfileViewHolder(@NonNull ProfileItemViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(DTOProfile profile) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Call bottom sheet here
                    onOptionsMenuClick.onItemOptionsClick(profile);
//                    showBottomSheet(profile.getProfileId()); // Pass profile ID to bottom sheet
                }
            });
            binding.profileName.setText(profile.getFirstName() + " "+ profile.getLastName());
            Glide.with(context)
                    .load(profile.getProfilePhotoUrl())
                    .centerCrop()
                    .placeholder(R.drawable.icon_profile_fill) // Optional placeholder image while loading
                    .into(binding.profile);
        }
    }

    public interface OnOptionsMenuClick{
        public void onItemOptionsClick(DTOProfile profile);
    }
}
