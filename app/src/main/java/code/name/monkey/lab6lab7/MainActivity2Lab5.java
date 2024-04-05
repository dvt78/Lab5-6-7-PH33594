package code.name.monkey.lab6lab7;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import code.name.monkey.lab6lab7.adapter.CompanyAdapter;
import code.name.monkey.lab6lab7.databinding.ActivityMainActivity2Lab5Binding;
import code.name.monkey.lab6lab7.model.Company;
import code.name.monkey.lab6lab7.model.CompanyResponse;
import code.name.monkey.lab6lab7.viewmodel.MainViewModelLab5;

public class MainActivity2Lab5 extends AppCompatActivity {

    private ActivityMainActivity2Lab5Binding binding;
    private MainViewModelLab5 viewModel;
    private CompanyAdapter adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainActivity2Lab5Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(MainViewModelLab5.class);

        binding.list.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CompanyAdapter(new ArrayList<>(), position -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = LayoutInflater.from(this);
            View dialogView = inflater.inflate(R.layout.dialog_confirm_delete, null);
            builder.setView(dialogView);

            Button buttonCancel = dialogView.findViewById(R.id.buttonCancel);
            Button buttonConfirmDelete = dialogView.findViewById(R.id.buttonConfirmDelete);

            AlertDialog dialog = builder.create();
            dialog.show();

            buttonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            buttonConfirmDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Company company = adapter.getItem(position);
                    if (company != null) {
                        viewModel.deleteCompany(company.getId());
                    }
                    dialog.dismiss();
                }
            });

        });

        viewModel.getCompanyListMutableLiveData().observe(this, companies -> {
            adapter.setCompanyList(companies);
            binding.list.setAdapter(adapter);
        });

        viewModel.getCompanyResponseMutableLiveData().observe(this, new Observer<CompanyResponse>() {
            @Override
            public void onChanged(CompanyResponse companyResponse) {
               if(companyResponse.getMessage().isStatus()){
                   viewModel.getAllCompanies();
               }
            }
        });

        viewModel.getAllCompanies();

        binding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddCompanyDialog();
            }
        });

        binding.search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (binding.search.getText().toString().length() > 0) {
                    viewModel.searchCompanyByName(binding.search.getText().toString().trim());
                } else {
                    viewModel.getAllCompanies();
                }
            }
        });
    }


    @SuppressLint("MissingInflatedId")
    public void showAddCompanyDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_company, null);
        builder.setView(dialogView);

        EditText editTextCompanyName = dialogView.findViewById(R.id.editTextCompanyName);
        Button buttonAddCompany = dialogView.findViewById(R.id.buttonAddCompany);

        AlertDialog dialog = builder.create();
        dialog.show();

        buttonAddCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String companyName = editTextCompanyName.getText().toString().trim();
                if (!companyName.isEmpty()) {
                    viewModel.addCompany(companyName);
                    dialog.dismiss();
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter company name", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}