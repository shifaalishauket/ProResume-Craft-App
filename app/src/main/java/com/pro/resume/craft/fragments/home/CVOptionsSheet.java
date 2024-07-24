package com.pro.resume.craft.fragments.home;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.pro.resume.craft.R;
import com.pro.resume.craft.database.AppDatabase;
import com.pro.resume.craft.models.DTOSavedResumes;

import java.io.File;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CVOptionsSheet  extends BottomSheetDialogFragment {

    @Inject
    AppDatabase appDatabase;
    private DTOSavedResumes cvData = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cv_options_sheet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.previewCV).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(CVOptionsSheet.this).navigate(R.id.previewResumeFragment);
                dismiss();
            }
        });

        view.findViewById(R.id.deleteCV).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (appDatabase != null) {
                    appDatabase.userDao().deleteResumeById(cvData.id);
                    deleteFile(cvData);
                    dismiss();
                } else {
                    Toast.makeText(requireActivity(), "Database not initialized", Toast.LENGTH_SHORT).show();
                }

            }
        });

        view.findViewById(R.id.shareCV).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareFile(cvData.getPath());
                dismiss();
            }
        });
    }

    public void setCVData(DTOSavedResumes cvData) {
        this.cvData = cvData;
    }

    private void shareFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            Toast.makeText(requireContext(), "File not found", Toast.LENGTH_SHORT).show();
            return;
        }

        Uri fileUri = FileProvider.getUriForFile(requireContext(), requireContext().getPackageName() + ".provider", file);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("application/pdf");
        shareIntent.putExtra(Intent.EXTRA_STREAM, fileUri);
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        try {
            startActivity(Intent.createChooser(shareIntent, "Share File"));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(requireContext(), "No application available to share file", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteFile(DTOSavedResumes cvData) {
        File file = new File(cvData.getPath());

        if (file.exists()) {
            if (file.delete()) {
                Toast.makeText(requireContext(), "File deleted successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(requireContext(), "File deletion failed", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(requireContext(), "File not found", Toast.LENGTH_SHORT).show();
        }
    }

}
