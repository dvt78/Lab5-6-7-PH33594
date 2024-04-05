package code.name.monkey.lab6lab7.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.function.Consumer;

import code.name.monkey.lab6lab7.model.Company;
import code.name.monkey.lab6lab7.model.CompanyResponse;
import code.name.monkey.lab6lab7.repository.Repository;

public class MainViewModelLab5 extends AndroidViewModel {
    private Repository repository;
    private MutableLiveData<List<Company>> companyListMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<CompanyResponse> companyResponseMutableLiveData = new MutableLiveData<>();

    public MainViewModelLab5(@NonNull Application application) {
        super(application);
        repository = new Repository();
    }

    public void getAllCompanies() {
        repository.getAllCompanies(new Consumer<List<Company>>() {
            @Override
            public void accept(List<Company> companies) {
                companyListMutableLiveData.postValue(companies);
            }
        });
    }

    public void addCompany(String name) {
        repository.addCompany(name, new Consumer<CompanyResponse>() {
            @Override
            public void accept(CompanyResponse companyResponse) {
                companyResponseMutableLiveData.postValue(companyResponse);
            }
        });
    }

    public void deleteCompany(String id) {
        repository.deleteCompanyById(id, new Consumer<CompanyResponse>() {
            @Override
            public void accept(CompanyResponse companyResponse) {
                companyResponseMutableLiveData.postValue(companyResponse);
            }
        });
    }

    public void searchCompanyByName(String name) {
        repository.searchCompanyByName(name, new Consumer<List<Company>>() {
            @Override
            public void accept(List<Company> companies) {
                companyListMutableLiveData.postValue(companies);
            }
        });
    }

    public MutableLiveData<List<Company>> getCompanyListMutableLiveData() {
        return companyListMutableLiveData;
    }

    public MutableLiveData<CompanyResponse> getCompanyResponseMutableLiveData() {
        return companyResponseMutableLiveData;
    }
}