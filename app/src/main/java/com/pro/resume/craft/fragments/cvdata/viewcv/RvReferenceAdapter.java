package com.pro.resume.craft.fragments.cvdata.viewcv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pro.resume.craft.R;
import com.pro.resume.craft.models.DTOReference;

import java.util.ArrayList;

public class RvReferenceAdapter extends RecyclerView.Adapter<RvReferenceAdapter.ViewHolder> {
    private ArrayList<DTOReference> mlist;
    private int tempname;
    private Context context;
    private View view;

    public RvReferenceAdapter(ArrayList<DTOReference> mlist, int tempname, Context context) {
        this.mlist = mlist;
        this.tempname = tempname;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (tempname == R.layout.template_1 || tempname == R.layout.template_4 || tempname == R.layout.template_5 || tempname == R.layout.template_6 || tempname == R.layout.template_7 || tempname == R.layout.template_8 || tempname == R.layout.template_9 || tempname == R.layout.template_10) {
            view = inflater.inflate(R.layout.template_1_ref_item, parent, false);
        } else if (tempname == R.layout.template_2) {
            view = inflater.inflate(R.layout.template_2_ref_item, parent, false);
        } else if (tempname == R.layout.template_3) {
            view = inflater.inflate(R.layout.template_3_ref_item, parent, false);
        } else if (tempname == R.layout.template_11) {
            view = inflater.inflate(R.layout.template_11_ref_item, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DTOReference currentItem = mlist.get(position);

        holder.reff_name.setText(currentItem.getReffName());
        holder.reff_desig.setText(currentItem.getReffDesignation());
        holder.reff_phone.setText(currentItem.getReffPhone());

    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView reff_name;
        TextView reff_desig;
        TextView reff_phone;

        public ViewHolder(View view) {
            super(view);
            reff_name = view.findViewById(R.id.title);


            reff_desig = view.findViewById(R.id.subTitle);

            reff_phone = view.findViewById(R.id.email);
        }
    }
}

