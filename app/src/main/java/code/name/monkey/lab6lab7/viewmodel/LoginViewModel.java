package code.name.monkey.lab6lab7.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.function.Consumer;

import code.name.monkey.lab6lab7.model.UserResponse;
import code.name.monkey.lab6lab7.model.User;
import code.name.monkey.lab6lab7.repository.Repository;

public class LoginViewModel extends AndroidViewModel {
    private Repository repository;
    private MutableLiveData<UserResponse> loginResponseMutableLiveData = new MutableLiveData<>();

    public LoginViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository();
    }

    public void login(String username, String password) {
        repository.login(new User(username, password), new Consumer<UserResponse>() {
            @Override
            public void accept(UserResponse userResponse) {
                loginResponseMutableLiveData.postValue(userResponse);
            }
        });
    }

    public MutableLiveData<UserResponse> getLoginResponseMutableLiveData() {
        return loginResponseMutableLiveData;
    }
}
