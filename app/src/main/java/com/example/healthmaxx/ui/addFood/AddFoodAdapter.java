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
import com.example.healthmaxx.Models.LabelNutrients;
import com.example.healthmaxx.Models.User;
import com.example.healthmaxx.Models.UserManager;
import com.example.healthmaxx.R;
import com.example.healthmaxx.api.RequestFood;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddFoodAdapter extends RecyclerView.Adapter<AddFoodAdapter.ViewHolder> {
    private List<Food> foods;
    private Context context;
    private User currentUser;
    RequestFood requestFood;
    List<Food> foodWithCalories;
    Food food;
    List<Integer> foodIds;
    int foodId;
    Double calories;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView foodName;

        public ViewHolder(View itemView) {
            super(itemView);
            foodName = (TextView) itemView.findViewById(R.id.itemText);
            itemView.setOnClickListener(this);
            foodName.setOnClickListener(this);
        }

        public TextView getFoodName() {
            return foodName;
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == foodName.getId()) {
                Log.d("SELECTED", foodName.getText().toString() + " " + getAdapterPosition());
                Food selectedFood = foods.get(getAdapterPosition());

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
                foodTitle.setText(selectedFood.getDescription() + " : ");

                if (selectedFood.getLabelNutrients() != null && selectedFood.getLabelNutrients().getCalories() != null) {
                    foodTitle.setText(selectedFood.getDescription() + String.valueOf(selectedFood.getLabelNutrients().getCalories().getValue()));
                }

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
                        String mealtime = (String) mealTimeSpinner.getSelectedItem();
                        float servingSize = Float.parseFloat(String.valueOf(servingSizeView.getText()));
                        DBHandler dbHandler = new DBHandler(itemView.getContext());

                        User user = UserManager.getInstance().getCurrentUser();

                        Retrofit retrofit = com.example.cinemaapp2.api.ApiClient.getClient();
                        requestFood = retrofit.create(RequestFood.class);

                        Call<Food> foodCall = requestFood.findFoodById(context.getResources().getString(R.string.api_key), foodId);

                        foodCall.enqueue(new Callback<Food>() {
                            @Override
                            public void onResponse(Call<Food> call, Response<Food> response) {
                                Log.d("FOOD ADAPTER", "API SUCCESS 2 ");
                                if (response.body().getLabelNutrients() != null && response.body().getLabelNutrients().getCalories() != null) {
                                    Food food = response.body();
                                    calories = food.getLabelNutrients().getCalories().getValue();

                                } else {
                                    calories = null;
                                }
                                dbHandler.addItem(user.getUserId(), fdcId, servingSize, mealtime, selectedFood.getDescription(), calories);
                                dialog.dismiss(); // Dismiss the dialog
                            }

                            @Override
                            public void onFailure(Call<Food> call, Throwable t) {
                                Log.e("FOOD ADAPTER", "API FAILUIRE 2 ", t);
                                dbHandler.addItem(user.getUserId(), fdcId, servingSize, mealtime, selectedFood.getDescription(), calories);
                                dialog.dismiss(); // Dismiss the dialog
                            }
                        });
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

        foodId = foods.get(position).getFdcId();

        Retrofit retrofit = com.example.cinemaapp2.api.ApiClient.getClient();
        requestFood = retrofit.create(RequestFood.class);

        Call<Food> foodCall = requestFood.findFoodById(context.getResources().getString(R.string.api_key), foodId);
        foodCall.enqueue(new Callback<Food>() {
            @Override
            public void onResponse(Call<Food> call, Response<Food> response) {
                food = response.body();
                LabelNutrients labelNutrients = food.getLabelNutrients();

                Log.d("FOODADAPTERAPI", food.getDescription());
                if (labelNutrients != null && labelNutrients.getCalories() != null) {
                    Log.d("FOODADAPTERAPI", String.valueOf(labelNutrients.getCalories().getValue()));

                    holder.getFoodName().setText(food.getDescription() + " : " + food.getLabelNutrients().getCalories().getValue() + "kcal");
                } else {
                    Log.d("FOODADAPTERAPI", "label nutrients null for : " + food.getDescription());

                    holder.getFoodName().setText(food.getDescription());

                }

            }

            @Override
            public void onFailure(Call<Food> call, Throwable t) {
                Log.e("FOODADAPTER", "api failure", t);
            }
        });


//        if (food.getLabelNutrients() != null && food.getLabelNutrients().getCalories() != null){
//            Log.d("calories ", food.getDescription() + " : " + food.getLabelNutrients().getCalories().getValue());
//            holder.getFoodName().setText(food.getDescription() + " : " + food.getLabelNutrients().getCalories().getValue() + "kcal");
//        } else {
//            holder.getFoodName().setText(food.getDescription() + " kcal not found ");
//        }
    }


    @Override
    public int getItemCount() {
        return foods.size();
    }
}
