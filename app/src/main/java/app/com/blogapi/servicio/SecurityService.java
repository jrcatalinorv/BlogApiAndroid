package app.com.blogapi.servicio;

import android.util.Log;

import java.util.List;

import app.com.blogapi.entidades.Login;
import app.com.blogapi.entidades.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface SecurityService {
    /*
    * @GET("users/{user}/repos")
    * Call<List<Repo>> listRepos(@Path("user") String user);
    * */

    @POST("login")
    Call<User> login(@Body Login login);

    @DELETE("logout")
    Call<User> logoff(@Header("Authorization") String token);

    @POST("register")
    Call<User> register (@Body Login login);
}
