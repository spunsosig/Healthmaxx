package com.example.healthmaxx.ui.addFood;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Movie;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthmaxx.DB.DBHandler;
import com.example.healthmaxx.Models.Food;
import com.example.healthmaxx.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddFoodAdapter extends RecyclerView.Adapter<AddFoodAdapter.ViewHolder> {
    private List<Food> foods;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView foodName;

        public ViewHolder(View itemView) {
            super(itemView);
            foodName = (TextView) itemView.findViewById(R.id.itemText);
            itemView.setOnClickListener(this);
            foodName.setOnClickListener(this);
        }

        public TextView getFoodName(){
            return foodName;
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == foodName.getId()){
                Log.d("SELECTED", foodName.getText().toString() + " " + getAdapterPosition());
                Food selectedFood = foods.get(getAdapterPosition());

//                FoodDetailsDialog dialog = new FoodDetailsDialog(itemView.getContext());
//                FragmentManager fragmentManager = ((AppCompatActivity) itemView.getContext()).getSupportFragmentManager();
//                dialog.show(fragmentManager, "Food details");

//                Dialog dialog = new Dialog(itemView.getContext());
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                dialog.setContentView(R.layout.dialog_food_details);
//                dialog.setCancelable(true);
//
//                Spinner mealTimeSpinner = (Spinner) dialog.findViewById(R.id.spinner);
//
//                List<String> mealTimes = Arrays.asList("Breakfast", "Lunch", "Dinner", "Snacks");
//                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(itemView.getContext(), R.layout.spinner_item, mealTimes);
//                arrayAdapter.setDropDownViewResource(R.layout.spinner_item);
//
//                mealTimeSpinner.setAdapter(arrayAdapter);
//
//                EditText servingSizeView = (EditText) dialog.findViewById(R.id.servingSize);
//                TextView foodTitle = (TextView) dialog.findViewById(R.id.foodName);
//
//                foodTitle.setText(selectedFood.getDescription());
//
//                dialog.set
//
//                dialog.show();

                AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());

                // Set the custom layout for the dialog
                LayoutInflater inflater = LayoutInflater.from(itemView.getContext());
                View dialogView = inflater.inflate(R.layout.dialog_food_details, null);
                builder.setView(dialogView);

                // Find views in the dialog layout
                Spinner mealTimeSpinner = dialogView.findViewById(R.id.spinner);
                EditText servingSizeView = dialogView.findViewById(R.id.servingSize);
                TextView foodTitle = dialogView.findViewById(R.id.foodName);

                // Set values to views
                foodTitle.setText(selectedFood.getDescription());

                // Create and set up the adapter for the spinner
                List<String> mealTimes = Arrays.asList("Breakfast", "Lunch", "Dinner", "Snacks");
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(itemView.getContext(), R.layout.spinner_item, mealTimes);
                arrayAdapter.setDropDownViewResource(R.layout.spinner_item);
                mealTimeSpinner.setAdapter(arrayAdapter);

                // Set positive button
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int fdcId = selectedFood.getFdcId();
                        float servingSize = Float.parseFloat(String.valueOf(servingSizeView.getText()));
                        DBHandler dbHandler = new DBHandler(itemView.getContext());
                        dbHandler.addItem(1, fdcId, servingSize);
                        dialog.dismiss(); // Dismiss the dialog
                    }
                });

                // Set negative button
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                // Create and show the dialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
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
