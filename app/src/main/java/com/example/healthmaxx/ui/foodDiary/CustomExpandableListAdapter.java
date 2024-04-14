package com.example.healthmaxx.ui.foodDiary;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.healthmaxx.Models.Food;
import com.example.healthmaxx.Models.LabelNutrients;
import com.example.healthmaxx.R;
import com.example.healthmaxx.api.RequestFood;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> expandableListTitle;
    private HashMap<String, List<Food>> expandableListDetail;

    public CustomExpandableListAdapter(Context context, List<String> expandableListTitle,
                                       HashMap<String, List<Food>> expandableListDetail) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;

    }

    @Override
    public int getGroupCount() {
        return expandableListTitle.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        // Handle the "Add Food" item case
        if (groupPosition >= expandableListTitle.size()) {
            return 0;
        }

        String groupTitle = expandableListTitle.get(groupPosition);
        List<Food> foods = expandableListDetail.get(groupTitle);
        return foods != null ? foods.size() : 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        if (groupPosition < expandableListTitle.size()) {
            // Return regular group title
            return expandableListTitle.get(groupPosition);
        }
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        // Retrieve the list of meals for the current group

        List<Food> foods = expandableListDetail.get(expandableListTitle.get(groupPosition));
        if (foods == null){
            Log.e("NULL_LIST", "FOODS LIST IS NULL");
            return null;
        }

        if (foods.get(childPosition).getDescription() == null){
            Log.e("NULL_LIST", "FOODS IN POSITION IS NULL");

        }

        // Check if the child position is within the bounds of the meals list
        if (childPosition < foods.size()) {
            // Return the meal at the specified child position
            Log.d("CHILD", "Food is " + foods.get(childPosition).getDescription());
            return foods.get(childPosition);
        } else {
            // Otherwise, return null (no child item)
            return null;
        }
    }


    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
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

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_child, null);
        }

//        Log.d("CHILD", "food is "+ food.getDescription());

        TextView mealNameTextView = convertView.findViewById(R.id.mealName);
        mealNameTextView.setText(food.getDescription());

        TextView calorieTextView = convertView.findViewById(R.id.mealCalories);
        LabelNutrients nutrients = food.getLabelNutrients();
//        float calories = nutrients.getCalories();
//        calorieTextView.setText(String.valueOf(calories));

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
