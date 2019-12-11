package app.com.blogapi.servicio;

import java.util.List;

import app.com.blogapi.entidades.Comments;
import app.com.blogapi.entidades.Entradas;
import retrofit2.Call;
import retrofit2.http.Body;
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
    Call <Entradas> getEstradaId(@Header("Authorization") String token, @Path("id") int id);

    @PUT("post/{id}/like")
    Call<Entradas> likepost(@Header("Authorization") String token, @Path("id") int id);

    @DELETE("post/{id}/like")
    Call<Entradas> delLikepost(@Header("Authorization") String token, @Path("id") int id);

    @GET("post/{id}/comment")
    Call<List<Comments>> comentariosPost(@Header("Authorization") String token, @Path("id") int id);

    @POST("post")
    Call <Entradas> entradaNueva(@Header("Authorization") String token, @Body Entradas entradas);
}