package code.name.monkey.lab6lab7.api;

import java.util.List;
import java.util.Map;

import code.name.monkey.lab6lab7.model.Company;
import code.name.monkey.lab6lab7.model.CompanyResponse;
import code.name.monkey.lab6lab7.model.Fruit;
import code.name.monkey.lab6lab7.model.FruitResponse;
import code.name.monkey.lab6lab7.model.UserResponse;
import code.name.monkey.lab6lab7.model.User;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiRequest {
    @POST("auth/login")
    Call<UserResponse> login(@Body User userLogin);

    @POST("auth/register")
    Call<UserResponse> register(@Body User userRegister);

    @Multipart
    @POST("fruit")
    Call<FruitResponse> addFruit(
            @Part("name") RequestBody name,
            @Part("quantity") RequestBody quantity,
            @Part("price") RequestBody price,
            @Part("description") RequestBody description,
            @Part("idUser") RequestBody idUser,
            @Part("idCompany") RequestBody idCompany,
            @Part List<MultipartBody.Part> images
    );

    @GET("fruit/getAllFruitByPage/{id}")
    Call<List<Fruit>> getAllFruitByPage(
            @Header("x-access-token") String token,
            @Path("id") String userId,
            @Query("page") int page,
            @Query("limit") int limit
    );

    @GET("fruit/getAllFruitQueryMap/{idUser}")
    Call<List<Fruit>> getAllFruitQueryMap(
            @Header("x-access-token") String token,
            @Path("idUser") String idUser,
            @QueryMap Map<String, String> options
    );

    @POST("company")
    Call<CompanyResponse> addCompany(@Body Company company);

    @GET("company")
    Call<List<Company>> getAllCompanies();

    @GET("companySearch")
    Call<List<Company>> searchCompanyByName(@Query("name") String name);

    @DELETE("company/{id}")
    Call<CompanyResponse> deleteCompany(@Path("id") String id);
}
