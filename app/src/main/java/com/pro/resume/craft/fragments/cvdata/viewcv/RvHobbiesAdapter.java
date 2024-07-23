package com.pro.resume.craft.fragments.cvdata.viewcv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pro.resume.craft.R;
import com.pro.resume.craft.models.DTOHobbies;

import java.util.ArrayList;

public class RvHobbiesAdapter extends RecyclerView.Adapter<RvHobbiesAdapter.ViewHolder> {
    private ArrayList<DTOHobbies> mlist;
    private int tempname;
    private Context context;
    private View view;

    public RvHobbiesAdapter(ArrayList<DTOHobbies> mlist, int tempname, Context context) {
        this.mlist = mlist;
        this.tempname = tempname;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (tempname == R.layout.template_9 || tempname == R.layout.template_3|| tempname == R.layout.template_6){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_only_item_view_white,parent,false);

        }else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_only_item_view,parent,false);

        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.hobbyname.setText(mlist.get(position).getHobbyName());
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView hobbyname;

        public ViewHolder(View view) {
            super(view);
            hobbyname = view.findViewById(R.id.title);
        }
    }
}



