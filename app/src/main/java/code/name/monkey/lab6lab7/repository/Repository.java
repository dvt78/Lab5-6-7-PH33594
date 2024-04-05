package code.name.monkey.lab6lab7.repository;

import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import code.name.monkey.lab6lab7.api.ApiRequest;
import code.name.monkey.lab6lab7.model.Company;
import code.name.monkey.lab6lab7.model.CompanyResponse;
import code.name.monkey.lab6lab7.model.Fruit;
import code.name.monkey.lab6lab7.model.FruitResponse;
import code.name.monkey.lab6lab7.model.UserResponse;
import code.name.monkey.lab6lab7.model.User;
import code.name.monkey.lab6lab7.retrofit.RetrofitRequest;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    private ApiRequest apiRequest;

    public Repository() {
        this.apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
    }

    public void login(User user, Consumer<UserResponse> consumer) {
        apiRequest.login(user).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    consumer.accept(response.body());
                } else {
                    Log.e("CALL_ERROR login", "login");
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.e("CALL_ERROR login", t.getMessage());
            }
        });
    }

    public void register(User user, Consumer<UserResponse> consumer) {
        apiRequest.register(user).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    consumer.accept(response.body());
                } else {
                    Log.e("CALL_ERROR register", "register");
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.e("CALL_ERROR register", t.getMessage());
            }
        });
    }

    public void addFruit(String name, String quantity, String price, String description, String idUser, String idCompany, List<String> imagePaths, Consumer<FruitResponse> consumer) {
        RequestBody nameBody = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody quantityBody = RequestBody.create(MediaType.parse("text/plain"), quantity);
        RequestBody priceBody = RequestBody.create(MediaType.parse("text/plain"), price);
        RequestBody descriptionBody = RequestBody.create(MediaType.parse("text/plain"), description);
        RequestBody idUserBody = RequestBody.create(MediaType.parse("text/plain"), idUser);
        RequestBody idCompanyBody = RequestBody.create(MediaType.parse("text/plain"), idCompany);
        List<MultipartBody.Part> parts = new ArrayList<>();
        for (String imagePath : imagePaths) {
            File file = new File(imagePath);
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("images", file.getName(), requestFile);
            parts.add(part);
        }
        Call<FruitResponse> call = apiRequest.addFruit(nameBody, quantityBody, priceBody, descriptionBody, idUserBody, idCompanyBody, parts);
        call.enqueue(new Callback<FruitResponse>() {
            @Override
            public void onResponse(Call<FruitResponse> call, Response<FruitResponse> response) {
                if (response.isSuccessful()) {
                    consumer.accept(response.body());
                } else {
                    Log.e("CALL_ERROR addFruit", "addFruit");
                }
            }

            @Override
            public void onFailure(Call<FruitResponse> call, Throwable t) {
                Log.e("CALL_ERROR addFruit", t.getMessage());
            }
        });
    }

    public void loadPageFruit(String token, String idUser, int page, Consumer<List<Fruit>> consumer) {
        apiRequest.getAllFruitByPage(token, idUser, page, 5).enqueue(new Callback<List<Fruit>>() {
            @Override
            public void onResponse(Call<List<Fruit>> call, Response<List<Fruit>> response) {
                if (response.isSuccessful()) {
                    consumer.accept(response.body());
                } else {
                    Log.e("CALL_ERROR loadPageFruit", "loadPageFruit");
                }
            }

            @Override
            public void onFailure(Call<List<Fruit>> call, Throwable t) {
                Log.e("CALL_ERROR loadPageFruit", t.getMessage());
            }
        });
    }

    public void getAllFruitQueryMap(String token,String idUser, String idCompany, int price, String name, Consumer<List<Fruit>> consumer) {
        Map<String, String> options = new HashMap<>();
        options.put("idCompany", idCompany);
        options.put("price", String.valueOf(price));
        options.put("name", name);
        apiRequest.getAllFruitQueryMap(token,idUser, options).enqueue(new Callback<List<Fruit>>() {
            @Override
            public void onResponse(Call<List<Fruit>> call, Response<List<Fruit>> response) {
                if (response.isSuccessful()) {
                    consumer.accept(response.body());
                } else {
                    Log.e("CALL_ERROR loadPageFruit", "loadPageFruit");
                }
            }

            @Override
            public void onFailure(Call<List<Fruit>> call, Throwable t) {
                Log.e("CALL_ERROR loadPageFruit", t.getMessage());
            }
        });
    }

    public void addCompany(String name, Consumer<CompanyResponse> consumer) {
        Call<CompanyResponse> call = apiRequest.addCompany(new Company(name));
        call.enqueue(new Callback<CompanyResponse>() {
            @Override
            public void onResponse(Call<CompanyResponse> call, Response<CompanyResponse> response) {
                if (response.isSuccessful()) {
                    consumer.accept(response.body());
                } else {
                    Log.e("CALL_ERROR addCompany", "addCompany");
                }
            }

            @Override
            public void onFailure(Call<CompanyResponse> call, Throwable t) {
                Log.e("CALL_ERROR addCompany", t.getMessage());
            }
        });
    }

    public void getAllCompanies(Consumer<List<Company>> consumer) {
        Call<List<Company>> call = apiRequest.getAllCompanies();
        call.enqueue(new Callback<List<Company>>() {
            @Override
            public void onResponse(Call<List<Company>> call, Response<List<Company>> response) {
                if (response.isSuccessful()) {
                    consumer.accept(response.body());
                } else {
                    Log.e("CALL_ERROR getAllCompanies", "getAllCompanies");
                }
            }

            @Override
            public void onFailure(Call<List<Company>> call, Throwable t) {
                Log.e("CALL_ERROR getAllCompanies", t.getMessage());
            }
        });
    }

    public void searchCompanyByName(String name, Consumer<List<Company>> consumer) {
        Call<List<Company>> call = apiRequest.searchCompanyByName(name);
        call.enqueue(new Callback<List<Company>>() {
            @Override
            public void onResponse(Call<List<Company>> call, Response<List<Company>> response) {
                if (response.isSuccessful()) {
                    consumer.accept(response.body());
                } else {
                    Log.e("CALL_ERROR searchByName", "searchByName");
                }
            }

            @Override
            public void onFailure(Call<List<Company>> call, Throwable t) {
                Log.e("CALL_ERROR searchByName", t.getMessage());
            }
        });
    }

    public void deleteCompanyById(String id, Consumer<CompanyResponse> consumer) {
        Call<CompanyResponse> call = apiRequest.deleteCompany(id);
        call.enqueue(new Callback<CompanyResponse>() {
            @Override
            public void onResponse(Call<CompanyResponse> call, Response<CompanyResponse> response) {
                if (response.isSuccessful()) {
                    consumer.accept(response.body());
                } else {
                    Log.e("CALL_ERROR deleteCompanyById", "deleteCompanyById");
                }
            }

            @Override
            public void onFailure(Call<CompanyResponse> call, Throwable t) {
                Log.e("CALL_ERROR deleteCompanyById", t.getMessage());
            }
        });
    }

}
