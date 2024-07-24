package com.pro.resume.craft.fragments.cvdata.coverletter;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.pro.resume.craft.R;
import com.pro.resume.craft.database.AppDatabase;
import com.pro.resume.craft.models.DTOCoverLetter;
import com.pro.resume.craft.models.DTOProfile;
import com.pro.resume.craft.models.DTOSavedResumes;
import com.pro.resume.craft.utils.BitmapTypeConverter;
import com.pro.resume.craft.utils.SharedPreferencesHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CoverLetterPreviewFragment extends Fragment {
    @Inject
    AppDatabase appDatabase;
    private ConstraintLayout scrollView;
    String email;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cover_letter_preview, container, false);
    }

    private static final int REQUEST_CODE_STORAGE_PERMISSION = 1;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        ImageView imageView = view.findViewById(R.id.profile);
        TextView firstName = view.findViewById(R.id.firstName);
        TextView lastName = view.findViewById(R.id.lastName);
        TextView profession = view.findViewById(R.id.profession);
        scrollView = view.findViewById(R.id.parentLayout);
        email = SharedPreferencesHelper.getString(requireContext(), "currentProfileId","");
        DTOProfile profile1 = appDatabase.userDao().findByEmail(email);
        firstName.setText(profile1.getFirstName());
        lastName.setText(profile1.getLastName());
        profession.setText(profile1.getEmail());
        Glide.with(requireActivity())
                .load(profile1.getProfilePhotoUrl())
                .centerCrop()
                .placeholder(R.drawable.icon_profile_fill) // Optional placeholder image while loading
                .into(imageView);

        if (bundle != null) {
            String value = bundle.getString("letter");
            // Use the value as needed
            TextView textView = view.findViewById(R.id.aiCover);
            textView.setText(value);
        }

        view.findViewById(R.id.export).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(requireActivity(),
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_CODE_STORAGE_PERMISSION);
                } else {
                    if (profile1 !=null && bundle!=null){
                        view.findViewById(R.id.export).setVisibility(View.GONE);
                        createPdf("Cover" + profile1.getLastName() + System.currentTimeMillis(),bundle.getString("letter"));
                    }else{
                        Toast.makeText(requireActivity(), "Please add personal info first!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    private Bitmap getBitmapFromView(View view, int height, int width) {
        if (view == null) {
            throw new IllegalArgumentException("View cannot be null");
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            bgDrawable.draw(canvas);
        } else {
            canvas.drawColor(Color.WHITE);
        }

        view.draw(canvas);
        return bitmap;
    }


    private void createPdf(String filename, String result) {
        WindowManager wm = (WindowManager) requireContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        PdfDocument document = new PdfDocument();
        Bitmap bitmap = getBitmapFromView(scrollView, height, width);
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(bitmap.getWidth(), bitmap.getHeight(), 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);
        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();
        canvas.drawPaint(paint);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);
        }

        paint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        document.finishPage(page);

        // Directory to save the PDF
        File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "CoverLetters");
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // File path for the PDF
        String targetPdf = directory.getAbsolutePath() + "/" + filename + ".pdf";
        File filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));
            byte[] bitmapData = BitmapTypeConverter.fromBitmap(bitmap);
            DTOCoverLetter dtoSavedResumes = new DTOCoverLetter(0, email, bitmapData, targetPdf,result);
            appDatabase.userDao().insertCoverLetter(dtoSavedResumes);
            Toast.makeText(requireContext(), "PDF saved at " + targetPdf, Toast.LENGTH_SHORT).show();
            NavHostFragment.findNavController(CoverLetterPreviewFragment.this).popBackStack();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(requireContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        document.close();
    }

}
