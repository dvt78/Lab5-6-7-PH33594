package code.name.monkey.lab6lab7.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.function.Consumer;

import code.name.monkey.lab6lab7.model.User;
import code.name.monkey.lab6lab7.model.UserResponse;
import code.name.monkey.lab6lab7.repository.Repository;

public class RegisterViewModel extends AndroidViewModel {
    private Repository repository;
    private MutableLiveData<UserResponse> registerResponseMutableLiveData = new MutableLiveData<>();

    public RegisterViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository();
    }

    public void register(User user) {
        repository.register(user, new Consumer<UserResponse>() {
            @Override
            public void accept(UserResponse userResponse) {
                registerResponseMutableLiveData.postValue(userResponse);
            }
        });
    }

    public MutableLiveData<UserResponse> getRegisterResponseMutableLiveData() {
        return registerResponseMutableLiveData;
    }
}
