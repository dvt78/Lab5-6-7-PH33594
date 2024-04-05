package code.name.monkey.lab6lab7.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.function.Consumer;

import code.name.monkey.lab6lab7.model.Fruit;
import code.name.monkey.lab6lab7.repository.Repository;

public class MainViewModel extends AndroidViewModel {
    private Repository repository;
    private MutableLiveData<List<Fruit>> listMutableLiveData = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository();
    }

    public void loadPageFruit(String token, String idUser, int page) {
        repository.loadPageFruit(token, idUser, page, new Consumer<List<Fruit>>() {
            @Override
            public void accept(List<Fruit> fruits) {
                listMutableLiveData.postValue(fruits);
            }
        });
    }

    public void getAllFruitQueryMap(String token,String idUser, int idCompany, String price, String name) {
        repository.getAllFruitQueryMap(token,idUser, String.valueOf(idCompany), Integer.parseInt(price), name, new Consumer<List<Fruit>>() {
            @Override
            public void accept(List<Fruit> fruits) {
                listMutableLiveData.postValue(fruits);
            }
        });
    }

    public MutableLiveData<List<Fruit>> getListMutableLiveData() {
        return listMutableLiveData;
    }
}
