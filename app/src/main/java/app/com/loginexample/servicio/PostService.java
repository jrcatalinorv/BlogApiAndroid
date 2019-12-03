package app.com.loginexample.servicio;

import java.util.List;

import app.com.loginexample.entidades.Entradas;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PostService {
    @GET("post")
    Call<List<Entradas>> getEstrada(@Header("Authorization") String token);

    @GET("post/{id}")
    Call<List<Entradas>> getEstradaId(@Header("Authorization") String token, @Path("id") int id);

    @PUT("post/{id}/like")
    Call<List<Entradas>> likepost(@Header("Authorization") String token, @Path("id") int id);

    @DELETE("post/{id}/like")
    Call<List<Entradas>> delLikepost(@Header("Authorization") String token, @Path("id") int id);

    @POST("post/{id}/comment")
    Call<List<Entradas>> comentarPost(@Header("Authorization") String token, @Path("id") int id);
}