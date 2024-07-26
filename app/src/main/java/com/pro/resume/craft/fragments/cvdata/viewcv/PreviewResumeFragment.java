package com.pro.resume.craft.fragments.cvdata.viewcv;

import static androidx.core.content.ContextCompat.getSystemService;

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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pro.resume.craft.R;
import com.pro.resume.craft.database.AppDatabase;
import com.pro.resume.craft.models.DTOEducation;
import com.pro.resume.craft.models.DTOExperience;
import com.pro.resume.craft.models.DTOHobbies;
import com.pro.resume.craft.models.DTOObjective;
import com.pro.resume.craft.models.DTOPersonalInfo;
import com.pro.resume.craft.models.DTOReference;
import com.pro.resume.craft.models.DTOSavedResumes;
import com.pro.resume.craft.models.DTOSkills;
import com.pro.resume.craft.models.DTOTemplate;
import com.pro.resume.craft.models.DTOlanguages;
import com.pro.resume.craft.utils.BitmapTypeConverter;
import com.pro.resume.craft.utils.SharedPreferencesHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PreviewResumeFragment extends Fragment {
    @Inject
    AppDatabase appDatabase;

    String uKey = "";

    // Declare all views
    private TextView firstname;
    private TextView lastname;
    private TextView designation;
    private TextView objective;
    private TextView website;
    private TextView phone;
    private TextView address;
    private TextView email;
    private ImageView profile_image;
    private LinearLayout linearLayout;
    private RelativeLayout relativeLayout;
    private ConstraintLayout scrollView;
    private Bitmap bitmap;
    private RecyclerView rvskill;
    private RecyclerView rveducation;
    private RecyclerView rvexperience;
    private RecyclerView rvlanguages;
    private RecyclerView rvrefference;
    private RecyclerView rvhobbies;


    DTOTemplate dtoTemplate;


    //adapters

    private RvSkillAdapter rvSkillAdapter;
    private RvLanguageAdapter languagesAdapter;

    private RvHobbiesAdapter rvHobbiesAdapter;

    private RvReferenceAdapter rvReferenceAdapter;

    private RvExperienceAdapter rvExperienceAdapter;

    private RvEducationAdapter rvEducationAdapter;



    //lists

    ArrayList<DTOSkills> skills = new ArrayList<>();
    ArrayList<DTOlanguages> languages = new ArrayList<>();

    ArrayList<DTOHobbies> hobbies = new ArrayList<>();

    ArrayList<DTOReference> references = new ArrayList<>();

    ArrayList<DTOExperience> experiences = new ArrayList<>();

    ArrayList<DTOEducation> educations = new ArrayList<>();

    private static final int REQUEST_CODE_STORAGE_PERMISSION = 1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        uKey = SharedPreferencesHelper.getString(requireContext(), "currentProfileId","");

        dtoTemplate = appDatabase.userDao().getTemplateByEmail(uKey);
        int layoutId = 0;

        if (dtoTemplate == null){
            layoutId = R.layout.template_1;
            dtoTemplate = new DTOTemplate(0,layoutId,uKey);
        }else{
           layoutId = dtoTemplate.getTempletename();
        }


        return inflater.inflate(layoutId, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DTOPersonalInfo dtoPersonalInfo = appDatabase.userDao().getPersonalInfo(uKey);

        DTOObjective dtoObjective = appDatabase.userDao().getObjectiveByEmail(uKey);

        scrollView = view.findViewById(R.id.mainlayout);

        ImageView imageView = view.findViewById(R.id.add);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(requireActivity(),
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_CODE_STORAGE_PERMISSION);
                } else {
                    if (dtoPersonalInfo !=null){
                        imageView.setVisibility(View.GONE);
                        createPdf(dtoPersonalInfo.getFirstName() + System.currentTimeMillis());
                    }else{
                        Toast.makeText(requireActivity(), "Please add personal info first!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        setBasicInfo(view, dtoPersonalInfo);
        setObjective(view);
        setSkills(view);
        setLanguages(view);
        setHobbies(view);
        setReference(view);
        setEducations(view);
        setExperiences(view);

    }

    private Bitmap getBitmapFromView(View view, int height, int width) {
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
        File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "Resume");
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // File path for the PDF
        String targetPdf = directory.getAbsolutePath() + "/" + filename + ".pdf";
        File filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));
            byte[] bitmapData = BitmapTypeConverter.fromBitmap(bitmap);
            DTOSavedResumes dtoSavedResumes = new DTOSavedResumes(0, uKey, bitmapData, targetPdf);
            appDatabase.userDao().insertResume(dtoSavedResumes);
            Toast.makeText(requireContext(), "PDF saved at " + targetPdf, Toast.LENGTH_SHORT).show();
            NavHostFragment.findNavController(PreviewResumeFragment.this).popBackStack();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(requireContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        document.close();
    }


    void setBasicInfo(View v, DTOPersonalInfo dtoPersonalInfo){
        try {
        firstname = v.findViewById(R.id.firstName);
        if (dtoTemplate.getTempletename() != R.layout.template_7){
            lastname = v.findViewById(R.id.lastName);

            firstname.setText(dtoPersonalInfo.getFirstName());
            lastname.setText(dtoPersonalInfo.getLastName());
        }else{
            firstname.setText(dtoPersonalInfo.getFirstName() + " " + dtoPersonalInfo.getLastName());
        }

        if (dtoTemplate.getTempletename() != R.layout.template_4 && dtoTemplate.getTempletename() != R.layout.template_5){
            profile_image = v.findViewById(R.id.profile);

            Glide.with(requireContext()).load(dtoPersonalInfo.getProfilePhotoUrl()).into(profile_image);
        }
        designation = v.findViewById(R.id.profession);
        address = v.findViewById(R.id.address);
        phone = v.findViewById(R.id.phone);
        email = v.findViewById(R.id.email);

        designation.setText(dtoPersonalInfo.getProfession());
        address.setText(dtoPersonalInfo.getAddress());
        phone.setText(dtoPersonalInfo.getPhonenumber());
        email.setText(dtoPersonalInfo.getEmail());

        }catch (NullPointerException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    void setObjective(View v){
        DTOObjective dtoObjective = appDatabase.userDao().getObjectiveByEmail(uKey);
        if (dtoObjective != null){
            objective = v.findViewById(R.id.objective);
            objective.setText(dtoObjective.getObjective());
        }
    }

    void setSkills(View v){
        rvskill = v.findViewById(R.id.skillsRV);
        skills = (ArrayList<DTOSkills>) appDatabase.userDao().getAllSkills(uKey);
        rvskill.setLayoutManager(new LinearLayoutManager(requireContext()));

        rvSkillAdapter = new RvSkillAdapter(skills,dtoTemplate.getTempletename(),requireContext());
        rvskill.setAdapter(rvSkillAdapter);

    }

    void setLanguages(View v){
        rvlanguages = v.findViewById(R.id.languagesRV);
        languages = (ArrayList<DTOlanguages>) appDatabase.userDao().getAllLanguages(uKey);

        rvlanguages.setLayoutManager(new LinearLayoutManager(requireContext()));

        languagesAdapter = new RvLanguageAdapter(languages,dtoTemplate.getTempletename(),requireContext());
        rvlanguages.setAdapter(languagesAdapter);

    }

    void setHobbies(View v){
        rvhobbies = v.findViewById(R.id.hobbiesRV);
        hobbies = (ArrayList<DTOHobbies>) appDatabase.userDao().getAllHobbies(uKey);
        if (dtoTemplate.getTempletename() == R.layout.template_6 || dtoTemplate.getTempletename() == R.layout.template_7 ){
            rvhobbies.setLayoutManager(new LinearLayoutManager(requireContext()));
        } else  {
            rvhobbies.setLayoutManager(new GridLayoutManager(requireContext(),2));

        }

        rvHobbiesAdapter = new RvHobbiesAdapter(hobbies,dtoTemplate.getTempletename(),requireContext());
        rvhobbies.setAdapter(rvHobbiesAdapter);
    }

    void setReference(View v){
        rvrefference = v.findViewById(R.id.referenceRV);
        references = (ArrayList<DTOReference>) appDatabase.userDao().getAllReference(uKey);
        rvrefference.setLayoutManager(new GridLayoutManager(requireContext(),2));
        rvReferenceAdapter = new RvReferenceAdapter(references,dtoTemplate.getTempletename(),requireContext());
        rvrefference.setAdapter(rvReferenceAdapter);
    }

    void setEducations(View v){
        rveducation = v.findViewById(R.id.educationRV);
        educations = (ArrayList<DTOEducation>) appDatabase.userDao().getAllEducations(uKey);
        rveducation.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvEducationAdapter = new RvEducationAdapter(educations,dtoTemplate.getTempletename(),requireContext());
        rveducation.setAdapter(rvEducationAdapter);
    }

    void setExperiences(View v){
        rvexperience = v.findViewById(R.id.experienceRV);
        experiences = (ArrayList<DTOExperience>) appDatabase.userDao().getAllExperience(uKey);
        rvexperience.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvExperienceAdapter = new RvExperienceAdapter(experiences,dtoTemplate.getTempletename(),requireContext());
        rvexperience.setAdapter(rvExperienceAdapter);

    }




}