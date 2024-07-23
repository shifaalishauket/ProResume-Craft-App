package com.pro.resume.craft.fragments.cvdata.viewcv;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.pro.resume.craft.R;
import com.pro.resume.craft.models.DTOSkills;

import java.util.ArrayList;

public class RvSkillAdapter extends RecyclerView.Adapter<RvSkillAdapter.ViewHolder> {

    private ArrayList<DTOSkills> mlist;
     public int tempname;
    private Context context;

    private View view;

    public RvSkillAdapter(ArrayList<DTOSkills> mlist, int tempname, Context context) {
        this.mlist = mlist;
        this.tempname = tempname;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (tempname == R.layout.template_1){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.template_1_lang_item, parent, false);
        } else if (tempname == R.layout.template_2) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.template_2_skill_item, parent, false);

        } else if (tempname == R.layout.template_3){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.template_3_skill_item, parent, false);

        } else if (tempname == R.layout.template_4) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.template_4_lang_item, parent, false);

        } else if (tempname == R.layout.template_5) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.template_4_lang_item, parent, false);

        } else if (tempname == R.layout.template_6) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.template_6_skill_item, parent, false);

        } else if (tempname == R.layout.template_7) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.template_7_skill_item, parent, false);

        } else if (tempname == R.layout.template_8) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.template_4_lang_item, parent, false);

        } else if (tempname == R.layout.template_9) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.template_9_skill_item, parent, false);

        } else if (tempname == R.layout.template_10) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.template_10_skill_item, parent, false);

        } else if (tempname == R.layout.template_11) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.template_11_skill_item, parent, false);

        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.skillname.setText(mlist.get(position).getSkill());

        if (tempname == R.layout.template_3 || tempname == R.layout.template_6 || tempname == R.layout.template_7 || tempname == R.layout.template_9 || tempname == R.layout.template_10 || tempname == R.layout.template_11 ){
            switch (mlist.get(position).getLevel()) {
                case "Beginner":
                    holder.progressbar.setProgress(33);
                    break;
                case "Intermediate":
                    holder.progressbar.setProgress(66);
                    break;
                case "Expert":
                    holder.progressbar.setProgress(100);
                    break;
            }
        }else{
            if (tempname == R.layout.template_1){
                int color = Color.parseColor("#3A3A3C");

                switch (mlist.get(position).getLevel()) {
                    case "Beginner":

                        holder.progress1.setBackgroundTintList(ColorStateList.valueOf(color));
                        holder.progress2.setBackgroundTintList(ContextCompat.getColorStateList(context,R.color.white));
                        holder.progress3.setBackgroundTintList(ContextCompat.getColorStateList(context,R.color.white));
                        break;
                    case "Intermediate":
                        holder.progress1.setBackgroundTintList(ColorStateList.valueOf(color));
                        holder.progress2.setBackgroundTintList(ColorStateList.valueOf(color));
                        holder.progress3.setBackgroundTintList(ContextCompat.getColorStateList(context,R.color.white));
                        break;
                    case "Expert":
                        holder.progress1.setBackgroundTintList(ColorStateList.valueOf(color));
                        holder.progress2.setBackgroundTintList(ColorStateList.valueOf(color));
                        holder.progress3.setBackgroundTintList(ColorStateList.valueOf(color));
                        break;
                }
            }else if (tempname == R.layout.template_2){
                int normal = Color.parseColor("#E0F0DC");
                int active = Color.parseColor("#40B649");
                switch (mlist.get(position).getLevel()) {
                    case "Beginner":

                        holder.progress1.setBackgroundTintList(ColorStateList.valueOf(active));
                        holder.progress2.setBackgroundTintList(ColorStateList.valueOf(normal));
                        holder.progress3.setBackgroundTintList(ColorStateList.valueOf(normal));
                        break;
                    case "Intermediate":
                        holder.progress1.setBackgroundTintList(ColorStateList.valueOf(active));
                        holder.progress2.setBackgroundTintList(ColorStateList.valueOf(active));
                        holder.progress3.setBackgroundTintList(ColorStateList.valueOf(normal));
                        break;
                    case "Expert":
                        holder.progress1.setBackgroundTintList(ColorStateList.valueOf(active));
                        holder.progress2.setBackgroundTintList(ColorStateList.valueOf(active));
                        holder.progress3.setBackgroundTintList(ColorStateList.valueOf(active));
                        break;
                }

            }else if (tempname == R.layout.template_8){


                int normal = Color.parseColor("#8C8888");
                int active = Color.parseColor("#3A3A3C");
                switch (mlist.get(position).getLevel()) {
                    case "Beginner":

                        holder.progress1.setBackgroundTintList(ColorStateList.valueOf(active));
                        holder.progress2.setBackgroundTintList(ColorStateList.valueOf(normal));
                        holder.progress3.setBackgroundTintList(ColorStateList.valueOf(normal));
                        break;
                    case "Intermediate":
                        holder.progress1.setBackgroundTintList(ColorStateList.valueOf(active));
                        holder.progress2.setBackgroundTintList(ColorStateList.valueOf(active));
                        holder.progress3.setBackgroundTintList(ColorStateList.valueOf(normal));
                        break;
                    case "Expert":
                        holder.progress1.setBackgroundTintList(ColorStateList.valueOf(active));
                        holder.progress2.setBackgroundTintList(ColorStateList.valueOf(active));
                        holder.progress3.setBackgroundTintList(ColorStateList.valueOf(active));
                        break;
                }

            }else{
                int normal = Color.parseColor("#BCBDC0");
                int active = Color.parseColor("#3A3A3C");
                switch (mlist.get(position).getLevel()) {
                    case "Beginner":

                        holder.progress1.setBackgroundTintList(ColorStateList.valueOf(active));
                        holder.progress2.setBackgroundTintList(ColorStateList.valueOf(normal));
                        holder.progress3.setBackgroundTintList(ColorStateList.valueOf(normal));
                        break;
                    case "Intermediate":
                        holder.progress1.setBackgroundTintList(ColorStateList.valueOf(active));
                        holder.progress2.setBackgroundTintList(ColorStateList.valueOf(active));
                        holder.progress3.setBackgroundTintList(ColorStateList.valueOf(normal));
                        break;
                    case "Expert":
                        holder.progress1.setBackgroundTintList(ColorStateList.valueOf(active));
                        holder.progress2.setBackgroundTintList(ColorStateList.valueOf(active));
                        holder.progress3.setBackgroundTintList(ColorStateList.valueOf(active));
                        break;
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
        TextView skillname;
        ProgressBar progressbar;//3,6,7,9,10,11

        View progress1, progress2, progress3;

        public ViewHolder(@NonNull View view) {
            super(view);
            skillname = view.findViewById(R.id.title);


            try {
                if (tempname == R.layout.template_3 || tempname == R.layout.template_6 || tempname == R.layout.template_7 || tempname == R.layout.template_9 || tempname == R.layout.template_10 || tempname == R.layout.template_11 ){
                    progressbar = view.findViewById(R.id.progress);
                }else{
                    progress1 = view.findViewById(R.id.progress_1);
                    progress2 = view.findViewById(R.id.progress_2);
                    progress3 = view.findViewById(R.id.progress_3);
                }
            } catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}
