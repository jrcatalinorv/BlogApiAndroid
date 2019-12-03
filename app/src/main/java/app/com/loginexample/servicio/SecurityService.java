package app.com.loginexample.servicio;

import android.util.Log;

import java.util.List;

import app.com.loginexample.entidades.Login;
import app.com.loginexample.entidades.User;
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
    public Call<User> login(@Body Login login);

    @DELETE("logout")
    public Call<User> logoff(@Header("Authorization") String token);

    @POST("register")
    public Call<User> register (@Body Login login);
}
