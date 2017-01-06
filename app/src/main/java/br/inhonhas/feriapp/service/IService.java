package br.inhonhas.feriapp.service;

import java.util.Map;

import br.inhonhas.feriapp.Event;
import br.inhonhas.feriapp.Login;
import br.inhonhas.feriapp.User;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * Created by Ana Coimbra on 05/01/2017.
 */

public interface IService {

    @POST("authentication/sign_in")
    Call<User> login(@Body Login login);

    @Multipart
    @POST("users")
    Call<User> registerUser(@PartMap Map<String, ResponseBody> bodyMap, @Part MultipartBody.Part photo);

    @POST("events")
    Call<Event> registerEvet(@Body Event event);

    @GET("events")
    Call<Event[]> getEvents();
}
