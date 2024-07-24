package com.pro.resume.craft.fragments.home;

import android.content.Context;
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
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.pro.resume.craft.R;
import com.pro.resume.craft.database.AppDatabase;
import com.pro.resume.craft.fragments.cvdata.viewcv.PreviewResumeFragment;
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
                createPdf("Cover" + profile1.getLastName() + System.currentTimeMillis());
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


    private void createPdf(String filename) {
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
            DTOSavedResumes dtoSavedResumes = new DTOSavedResumes(0, email, bitmapData, targetPdf);
            Toast.makeText(requireContext(), "PDF saved at " + targetPdf, Toast.LENGTH_SHORT).show();
            NavHostFragment.findNavController(CoverLetterPreviewFragment.this).popBackStack();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(requireContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        document.close();
    }

}
