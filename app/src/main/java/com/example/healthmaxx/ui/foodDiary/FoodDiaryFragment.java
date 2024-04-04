package com.example.healthmaxx.ui.foodDiary;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.PixelCopy;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.healthmaxx.Models.Food;
import com.example.healthmaxx.Models.FoodResponse;
import com.example.healthmaxx.R;
import com.example.healthmaxx.api.RequestFood;
import com.example.healthmaxx.databinding.FragmentFoodDiaryBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FoodDiaryFragment extends Fragment implements View.OnClickListener{

    private FragmentFoodDiaryBinding binding;
    private ExpandableListView expandableListView;
    private List<String> expandableListTitle;
    private HashMap<String, List<Food>> expandableListData;
    private ExpandableListAdapter adapter;
    private FoodDiaryViewModel viewModel;
    private Button addBtn;


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

        Retrofit retrofit = com.example.cinemaapp2.api.ApiClient.getClient();
        RequestFood requestFood = retrofit.create(RequestFood.class);

        String api_key = getString(R.string.api_key);
        Log.d("API", "Key = " + api_key);
        Call<FoodResponse> foodSearch = requestFood.getNutrition(api_key, "Chicken tikka and 200g rice and 200g cucumber");

        // Log the URL being sent
        String url = foodSearch.request().url().toString();
        Log.d("API", "Request URL: " + url);

//        foodSearch.enqueue(new Callback<FoodResponse>() {
//            @Override
//            public void onResponse(Call<FoodResponse> call, Response<FoodResponse> response) {
//                if (response.isSuccessful()){
//                    List<Food> foods = response.body().getItems();
//
//                    for (Food food : foods){
//                        Log.d("Food", "Name: " + food.getName() + ", Calories: " + food.getCalories());
//                    }
//                } else {
//                    try {
//                        Log.e("API", "Error: " + response.errorBody().string());
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<FoodResponse> call, Throwable t) {
//                Log.d("API", "API failed to connect", t);
//            }
//        });

        addBtn = binding.addFoodBtn;
        addBtn.setOnClickListener(this);

        expandableListView = binding.expandableListView; // expandable list view
        expandableListData = viewModel.getExpandableListData(); // The dataset
        expandableListTitle = new ArrayList<String>(expandableListData.keySet()); // headers e.g. breakfast

        adapter = new CustomExpandableListAdapter(this.getContext(), expandableListTitle, expandableListData);
        expandableListView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == addBtn.getId()){
            Toast.makeText(this.getContext(), "Add food button clicked", Toast.LENGTH_LONG).show();
            NavController navController = Navigation.findNavController(this.getActivity(), R.id.nav_host_fragment_activity_main);
            navController.navigate(R.id.addFoodFragment);
        }
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