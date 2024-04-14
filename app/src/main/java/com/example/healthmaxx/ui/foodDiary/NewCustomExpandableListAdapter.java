package com.example.healthmaxx.ui.foodDiary;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.healthmaxx.DB.DBHandler;
import com.example.healthmaxx.Models.Food;
import com.example.healthmaxx.Models.UserManager;
import com.example.healthmaxx.R;
import com.example.healthmaxx.api.RequestFood;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class NewCustomExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private HashMap<String, List<Food>> expandableListData;
    private List<String> expandableListTitle;

    public NewCustomExpandableListAdapter(Context context, List<String> expandableListTitle, HashMap<String, List<Food>> expandableListData){
        this.context = context;
        this.expandableListData = expandableListData;
        this.expandableListTitle = expandableListTitle;
    }

    @Override
    public int getGroupCount() {
        return expandableListData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        String groupKey = getGroup(groupPosition).toString();
        return Objects.requireNonNull(expandableListData.get(groupKey)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return expandableListData.keySet().toArray()[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        String groupKey = expandableListTitle.get(groupPosition);
        List<Food> foods = expandableListData.get(groupKey);

        if (foods == null) {
            Log.e("NULL_LIST", "List of foods is null for group: " + groupKey);
            return null;
        }

        if (childPosition >= 0 && childPosition < foods.size()) {
            Food food = foods.get(childPosition);
            if (food == null) {
                Log.e("NULL_ITEM", "Food item is null at position " + childPosition + " for group: " + groupKey);
            } else {
                String description = food.getDescription();
                if (description == null) {
                    Log.e("NULL_DESCRIPTION", "Description is null for food item at position " + childPosition + " for group: " + groupKey);
                }
            }
            return food;
        } else {
            Log.e("OUT_OF_BOUNDS", "Child position is out of bounds for group: " + groupKey);
            return null;
        }
    }

//    @Override
//    public Object getChild(int groupPosition, int childPosition) {
//
//        String groupKey = expandableListTitle.get(groupPosition);
//        List<Food> foods = expandableListData.get(groupKey);
//
//        Retrofit retrofit = com.example.cinemaapp2.api.ApiClient.getClient();
//        RequestFood requestFood = retrofit.create(RequestFood.class);
//
//        DBHandler db = new DBHandler(context);
//
////        List<Integer> fdcIds = db.getFoodDiaryFdcIds(UserManager.getInstance().getCurrentUser());
//
//        Log.d("FoodPos", food)
//
//        String api_key = this.context.getResources().getString(R.string.api_key);
//
//        int id = 0;
//
//        Call<Food> findFoodById = requestFood.findFoodById(api_key, id);
//
//        findFoodById.enqueue(new Callback<Food>() {
//            @Override
//            public void onResponse(Call<Food> call, Response<Food> response) {
//                Food food = response.body();
//                if (food.getDescription() != null){
//                    Log.d("FOODITEM", food.getDescription());
//                } else {
//                    Log.e("FOODITEM", "FOOD DESCRIPTION IS NULL");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Food> call, Throwable t) {
//                Log.e("API", "FAILED TO FETCH FOODS ", t);
//            }
//        });
//
//        return null;
//    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String mealTime = (String) getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_group, null);
        }

        TextView textView = convertView.findViewById(R.id.mealTime);
        textView.setText(mealTime);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Food food = (Food) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_child, parent, false);
        }

        TextView mealNameTextView = convertView.findViewById(R.id.mealName);
        if (food != null){
            mealNameTextView.setText(food.getDescription());
            Log.d("foodChild", food.getDescription());
            TextView calorieTextView = convertView.findViewById(R.id.mealCalories);
//            Log.d("foodChild")
//            calorieTextView.setText(food.getFoodNutrientByName("Energy").getNumber());
        } else {
            Log.d("foodChild", "foodChild is null");
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
