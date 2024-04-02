package com.example.healthmaxx.ui.foodDiary;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.example.healthmaxx.Models.Meal;
import com.example.healthmaxx.databinding.FragmentFoodDiaryBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FoodDiaryFragment extends Fragment implements View.OnClickListener{

    private FragmentFoodDiaryBinding binding;
    private ExpandableListView expandableListView;
    private List<String> expandableListTitle;
    private HashMap<String, List<Meal>> expandableListData;
    private ExpandableListAdapter adapter;
    private FoodDiaryViewModel viewModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        viewModel = new ViewModelProvider(this).get(FoodDiaryViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFoodDiaryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        expandableListView = binding.expandableListView; // expandable list view
        expandableListData = viewModel.getExpandableListData(); // The dataset
        expandableListTitle = new ArrayList<String>(expandableListData.keySet()); // headers e.g. breakfast

        adapter = new CustomExpandableListAdapter(this.getContext(), expandableListTitle, expandableListData);
        expandableListView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i("TAG","Back Button Pressed");
        switch (item.getItemId()) {
            case android.R.id.home:
                Log.i("TAG","home on backpressed");
                getActivity().onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}