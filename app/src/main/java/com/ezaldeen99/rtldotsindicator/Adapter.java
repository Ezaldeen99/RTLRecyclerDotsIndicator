package com.ezaldeen99.rtldotsindicator;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Random;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    Context context;

    public Adapter(@NonNull Context context) {
        super();
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflater = LayoutInflater.from(context).inflate(R.layout.main_recycler, parent, false);
        return new ViewHolder(inflater);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Generate random background color
        Random rnd = new Random();
        int red = rnd.nextInt(256);
        int green = rnd.nextInt(256);
        int blue = rnd.nextInt(256);
        int color = Color.argb(255, red, green, blue);
        holder.item.setBackgroundColor(color);
        holder.pageNumber.setText(String.format("%d", position));
        holder.pageNumber.setTextColor(ContrastColor(red, green, blue));

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View item;
        TextView pageNumber;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView;
            pageNumber = itemView.findViewById(R.id.page_number);
        }
    }

    public int ContrastColor(int red, int green, int blue) {
        // Calculate the perceptive luminance (aka luma) - human eye favors green color...
        double luma = ((0.299 * red) + (0.587 * green) + (0.114 * blue)) / 255;

        // Return black for bright colors, white for dark colors
        return luma > 0.5 ? Color.parseColor("#000000") : Color.parseColor("#FFFFFF");
    }


}
