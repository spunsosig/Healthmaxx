package com.example.healthmaxx.ui.addFood;

import android.content.Context;
import android.graphics.Movie;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthmaxx.Models.Food;
import com.example.healthmaxx.R;

import java.util.ArrayList;
import java.util.List;

public class AddFoodAdapter extends RecyclerView.Adapter<AddFoodAdapter.ViewHolder> {
    private List<Food> foods;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView foodName;

        public ViewHolder(View itemView) {
            super(itemView);
            foodName = (TextView) itemView.findViewById(R.id.itemText);
        }

        public TextView getFoodName(){
            return foodName;
        }
    }

    public AddFoodAdapter(Context context, List<Food> foods) {
        this.context = context;
        this.foods = foods;
    }

    @NonNull
    @Override
    public AddFoodAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.text_row_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddFoodAdapter.ViewHolder holder, int position) {
        holder.getFoodName().setText(foods.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }
}
