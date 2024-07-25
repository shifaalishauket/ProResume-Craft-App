package com.pro.resume.craft.fragments.cvdata.remove.background;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.pro.resume.craft.R;

import java.util.List;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ColorViewHolder> {

    private List<Integer> colorList;
    private Context context;
    private OnItemClickListener onItemClickListener;

    // Interface for click events
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    // Constructor
    public ColorAdapter(Context context, List<Integer> colorList, OnItemClickListener listener) {
        this.context = context;
        this.colorList = colorList;
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public ColorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.color_palette_item_view, parent, false);
        return new ColorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ColorViewHolder holder, int position) {
        int color = colorList.get(position);
        // Set the background tint of the View
        holder.colorView.setBackgroundTintList(ColorStateList.valueOf(color));
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return colorList.size();
    }

    public static class ColorViewHolder extends RecyclerView.ViewHolder {
        View colorView;

        public ColorViewHolder(@NonNull View itemView) {
            super(itemView);
            colorView = itemView.findViewById(R.id.color);
        }
    }
}
