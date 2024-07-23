package com.pro.resume.craft.fragments.cvdata.viewcv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pro.resume.craft.R;
import com.pro.resume.craft.models.DTOExperience;

import java.util.ArrayList;

public class RvExperienceAdapter extends RecyclerView.Adapter<RvExperienceAdapter.ViewHolder> {
    private ArrayList<DTOExperience> mlist;
    private int tempname;
    private Context context;

    public RvExperienceAdapter(ArrayList<DTOExperience> mlist, int tempname, Context context) {
        this.mlist = mlist;
        this.tempname = tempname;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = null;
        if (tempname == R.layout.template_1 || tempname == R.layout.template_4|| tempname == R.layout.template_5 || tempname == R.layout.template_6 || tempname == R.layout.template_8 || tempname == R.layout.template_9 ){
            view = inflater.inflate(R.layout.template_1_edu_item, parent, false);
        } else if (tempname == R.layout.template_7) {
            view = inflater.inflate(R.layout.template_7_edu_item, parent, false);
        }else if (tempname == R.layout.template_10|| tempname == R.layout.template_2) {
            view = inflater.inflate(R.layout.template_2_edu_item, parent, false);
        }else if (tempname == R.layout.template_11){
            view = inflater.inflate(R.layout.template_11_edu_item, parent, false);
        } else if (tempname == R.layout.template_3) {
            view = inflater.inflate(R.layout.template_3_edu_item, parent, false);

        }

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DTOExperience currentItem = mlist.get(position);

        holder.companyname.setText(currentItem.getCompany());


        holder.designation.setVisibility(View.VISIBLE);
        holder.designation.setText(currentItem.getTitle());

        holder.date.setText(currentItem.getStartDate() + "-" + currentItem.getEndDate());
        holder.description.setText(currentItem.getDescription().trim());
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView companyname;
        TextView designation;
        TextView date;
        TextView description;

        public ViewHolder(View view) {
            super(view);
            companyname = view.findViewById(R.id.title);
            designation = view.findViewById(R.id.designation);
            date = view.findViewById(R.id.date);
            description = view.findViewById(R.id.subTitle);
        }
    }
}

