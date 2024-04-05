package code.name.monkey.lab6lab7.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.function.Consumer;

import code.name.monkey.lab6lab7.model.FruitResponse;
import code.name.monkey.lab6lab7.repository.Repository;

public class AddFruitViewModel extends AndroidViewModel {
    private Repository repository;
    private MutableLiveData<FruitResponse> fruitResponseMutableLiveData = new MutableLiveData<>();

    public AddFruitViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository();
    }

    public void addFruit(String name, String quantity, String price, String description, String idUser, String idCompany, List<String> imagePaths) {
        repository.addFruit(name, quantity, price, description, idUser, idCompany, imagePaths, new Consumer<FruitResponse>() {
            @Override
            public void accept(FruitResponse fruitResponse) {
                fruitResponseMutableLiveData.postValue(fruitResponse);
            }
        });
    }

    public MutableLiveData<FruitResponse> getFruitResponseMutableLiveData() {
        return fruitResponseMutableLiveData;
    }
}
