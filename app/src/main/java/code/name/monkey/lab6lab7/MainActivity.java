package code.name.monkey.lab6lab7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import code.name.monkey.lab6lab7.activity.AddFruitActivity;
import code.name.monkey.lab6lab7.adapter.FruitAdapter;
import code.name.monkey.lab6lab7.databinding.ActivityLoginBinding;
import code.name.monkey.lab6lab7.databinding.ActivityMainBinding;
import code.name.monkey.lab6lab7.model.Fruit;
import code.name.monkey.lab6lab7.model.User;
import code.name.monkey.lab6lab7.model.UserClient;
import code.name.monkey.lab6lab7.viewmodel.MainViewModel;
import code.name.monkey.lab6lab7.viewmodel.RegisterViewModel;
import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainViewModel viewModel;
    private FruitAdapter fruitAdapter;
    private List<Fruit> fruitList = new ArrayList<>();
    private boolean isLoading = false;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        binding.fruits.setLayoutManager(layoutManager);
        fruitAdapter = new FruitAdapter(fruitList);
        binding.fruits.setAdapter(fruitAdapter);

        String[] companyNames = getResources().getStringArray(R.array.company_names);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, companyNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.company.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        binding.fruits.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                boolean canScrollDown = recyclerView.canScrollVertically(1);
                if (!canScrollDown) {
                    Toast.makeText(MainActivity.this, "load page " + page, Toast.LENGTH_SHORT).show();
                    viewModel.loadPageFruit(UserClient.getInstance().getAccessToken(), UserClient.getInstance().get_id(), page++);
                }
            }
        });

        binding.find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fruitList.clear();
                viewModel.getAllFruitQueryMap(UserClient.getInstance().getAccessToken(), UserClient.getInstance().get_id(), binding.company.getSelectedItemPosition() + 1, binding.price.getText().toString(), binding.search.getText().toString());
            }
        });

        viewModel.getListMutableLiveData().observe(this, new Observer<List<Fruit>>() {
            @Override
            public void onChanged(List<Fruit> fruits) {
                List<Fruit> newFruitList = fruits;
                fruitList.addAll(newFruitList);
                fruitAdapter.updateData(newFruitList);
            }
        });

        binding.add.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AddFruitActivity.class));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.loadPageFruit(UserClient.getInstance().getAccessToken(), UserClient.getInstance().get_id(), page++);

    }
}