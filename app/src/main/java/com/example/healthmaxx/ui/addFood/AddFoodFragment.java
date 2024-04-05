package com.example.healthmaxx.ui.addFood;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.healthmaxx.Models.Food;
import com.example.healthmaxx.Models.FoodResponse;
import com.example.healthmaxx.R;
import com.example.healthmaxx.api.RequestFood;
import com.example.healthmaxx.databinding.FragmentAddFoodBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddFoodFragment extends Fragment implements View.OnClickListener {

    private FragmentAddFoodBinding binding;
    private AddFoodViewModel mViewModel;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private Button submitBtn;
    private RequestFood requestFood;
    private String api_key; // Move initialization to onCreateView() or onActivityCreated()
    private String query;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //required for back button to work
        setHasOptionsMenu(true);
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

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentAddFoodBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        searchView = binding.searchView;
        submitBtn = binding.submitBtn;
        submitBtn.setOnClickListener(this);

        recyclerView = binding.recyclerView;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        // Initialize api_key here
        api_key = requireActivity().getResources().getString(R.string.api_key);

        Retrofit retrofit = com.example.cinemaapp2.api.ApiClient.getClient();
        requestFood = retrofit.create(RequestFood.class);

        return root;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AddFoodViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.submitBtn) {
            query = searchView.getQuery().toString();
            Call<FoodResponse> foodSearchCall = requestFood.searchFood(api_key, query);

            String url = foodSearchCall.request().url().toString();
            Log.d("API", "Request URL: " + url);

            foodSearchCall.enqueue(new Callback<FoodResponse>() {
                @Override
                public void onResponse(Call<FoodResponse> call, Response<FoodResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null && response.body().getFoods() != null){
                            List<Food> foods = response.body().getFoods();
                            AddFoodAdapter addFoodAdapter = new AddFoodAdapter(requireContext(), foods);
                            recyclerView.setAdapter(addFoodAdapter);
                        } else {
                            Log.e("API", "Item is null");
                            Log.e("API", response.body().toString());
                            Toast.makeText(AddFoodFragment.this.getContext(), "No item called: " + query, Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Log.e("API", "No response from API");
                    }
                }

                @Override
                public void onFailure(Call<FoodResponse> call, Throwable t) {
                    Log.d("API", "Connection unsuccessful");

                }
            });
        }
    }
}