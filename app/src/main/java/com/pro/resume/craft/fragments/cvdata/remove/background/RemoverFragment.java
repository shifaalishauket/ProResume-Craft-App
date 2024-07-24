package com.pro.resume.craft.fragments.cvdata.remove.background;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.pro.resume.craft.R;
import com.pro.resume.craft.database.AppDatabase;
import com.pro.resume.craft.databinding.FragmentProfileListBinding;
import com.pro.resume.craft.databinding.FragmentRemoverBinding;
import com.pro.resume.craft.models.DTOPersonalInfo;
import com.pro.resume.craft.models.DTOProfile;
import com.pro.resume.craft.utils.SharedPreferencesHelper;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import dev.eren.removebg.RemoveBg;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

@AndroidEntryPoint
public class RemoverFragment extends Fragment {
    @Inject
    AppDatabase appDatabase;
    private FragmentRemoverBinding binding;
    private Boolean isRemoved = false;
    private CoroutineScope coroutineScope = CoroutineScopeKt.CoroutineScope(Dispatchers.getMain());

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRemoverBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String email = SharedPreferencesHelper.getString(requireContext(), "currentProfileId","");
        DTOProfile profile = appDatabase.userDao().findByEmail(email);
        DTOPersonalInfo dtoPersonalInfo = appDatabase.userDao().getPersonalInfo(email);
        if (dtoPersonalInfo == null){
            if (profile != null){
                Glide.with(requireContext()).load(profile.getProfilePhotoUrl()).into(binding.photo);
            }
        }else{
            Glide.with(requireContext()).load(dtoPersonalInfo.getProfilePhotoUrl()).into(binding.photo);
        }
        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isRemoved){

                }else{
//                    isRemoved = true;
//                    RemoveBg remover = new RemoveBg(requireActivity());
//
//                    Flow<Bitmap> bitmapFlow = remover.clearBackground(getBitmapFromImageView(binding.photo));
//
//                    // Launch a coroutine to collect the flow
//                    coroutineScope.(Dispatchers.getMain(), CoroutineStart.DEFAULT, (coroutineScope1, continuation) -> {
//                        bitmapFlow.collect(new FlowCollector<Bitmap>() {
//                            @Nullable
//                            @Override
//                            public Object emit(Bitmap bitmap, @NonNull Continuation<? super Unit> continuation) {
//                                binding.photo.setImageBitmap(bitmap);
//                                return null;
//                            }
//                        });
//                        return null;
//                    });
                }
            }
        });
    }

    public Bitmap getBitmapFromImageView(ImageView imageView) {
        Drawable drawable = imageView.getDrawable();
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else {
            // Handle the case where the drawable is not a BitmapDrawable
            return null;
        }
    }

}