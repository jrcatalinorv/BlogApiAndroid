package app.com.blogapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import app.com.blogapi.adaptadores.AdaptadorComentario;
import app.com.blogapi.entidades.Comments;
import app.com.blogapi.entidades.Entradas;
import app.com.blogapi.servicio.BlogApiServices;
import app.com.blogapi.servicio.PostService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VistaPost extends AppCompatActivity {

    private TextView title, body, comments, likes, views, tags, author;
    private String postId, authToken;
    private Switch btnLike;
    private Button btnComentar;
    private EditText etComentario;
    private List<Comments> commentsList;
    private AdaptadorComentario adaptadorComentario;
    private RecyclerView rvcomentariosLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_post);

        final PostService postService = BlogApiServices
                .getInstance().getPostService();

        Intent intent = getIntent();
        postId = intent.getStringExtra(MainMenu.EXTRA_ID);


        /*
        Obteniendo las variables almacenadas en el shared preferences
        *
        * */
        SharedPreferences pref = getApplicationContext().getSharedPreferences("BlogApiPref", MODE_PRIVATE);
        authToken = pref.getString("token", null);


        /*
        *
        * */
        title = findViewById(R.id.vistaPostTitle);
        body = findViewById(R.id.vistaPostBody);
        comments = findViewById(R.id.vistaPostComments);
        comments.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_comment, 0, 0, 0);
        likes = findViewById(R.id.vistaPostLikes);
        likes.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_like, 0, 0, 0);
        views = findViewById(R.id.vistaPostViews);
        views.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_views, 0, 0, 0);
        tags = findViewById(R.id.vistaPostTags);
        author = findViewById(R.id.vistaPostAuthor);
        btnLike = findViewById(R.id.vistaPostLikeBtn);
        btnComentar = findViewById(R.id.vpComentar);
        etComentario = findViewById(R.id.vpComentario);
        rvcomentariosLista = findViewById(R.id.rvComentarios);

        /*
        * Obtener los datos del Post Seleccionado
        * */
        Call <Entradas> call = postService.getEstradaId("Bearer "+authToken, Integer.parseInt(postId));
        call.enqueue(new Callback<Entradas>() {
            @Override
            public void onResponse(Call<Entradas> call, Response<Entradas> response) {
                if (response.isSuccessful()) {
                    title.setText(response.body().getTitle());
                    body.setText(response.body().getBody());
                    comments.setText(String.valueOf(response.body().getComments()));
                    views.setText(String.valueOf(response.body().getViews()));
                    likes.setText(String.valueOf(response.body().getLikes()));
                    author.setText(response.body().getUserName()+" ("+response.body().getUserEmail()+")");
                    tags.setText(convertArrayToString(response.body().getTags()));

                    if(response.body().getLiked()) { btnLike.setChecked(true); }
                }else{
                    Toast.makeText(VistaPost.this, "Error: "+response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Entradas> call, Throwable t) {
                Toast.makeText(VistaPost.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        /*
        * Mostar la lista de comentarios del post Seleccionado
        * */
        Call <List<Comments>> callComments  = postService.comentariosPost("Bearer "+authToken, Integer.parseInt(postId));
        callComments.enqueue(new Callback<List<Comments>>() {
            @Override
            public void onResponse(Call<List<Comments>> call, Response<List<Comments>> response) {
                if (response.isSuccessful()) {
                    commentsList = response.body();
                    adaptadorComentario = new AdaptadorComentario(commentsList);
                    rvcomentariosLista.setAdapter(adaptadorComentario);
                }else{
                    Toast.makeText(VistaPost.this, "Error Response Comentarios: "+response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Comments>> call, Throwable t) {
                Toast.makeText(VistaPost.this, "Error buscando comentarios: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        /*
        * Pulsar el boton de like
        * */
        btnLike.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Call <Entradas> callLiked = postService.likepost("Bearer "+authToken, Integer.parseInt(postId));
                    callLiked.enqueue(new Callback<Entradas>() {
                        @Override
                        public void onResponse(Call<Entradas> call, Response<Entradas> response) {
                            if (response.isSuccessful()) {
                                //
                            }else{
                                Toast.makeText(VistaPost.this, "Not Successful: "+response.code(), Toast.LENGTH_SHORT).show();
                                btnLike.setChecked(false);
                            }
                        }

                        @Override
                        public void onFailure(Call<Entradas> call, Throwable t) { }
                    });


                }else{
                    Call <Entradas> callDislike = postService.delLikepost("Bearer "+authToken, Integer.parseInt(postId));
                    callDislike.enqueue(new Callback<Entradas>() {
                        @Override
                        public void onResponse(Call<Entradas> call, Response<Entradas> response) {
                            if (response.isSuccessful()) {

                            }else{
                                Toast.makeText(VistaPost.this, "Error: "+response.code(), Toast.LENGTH_SHORT).show();
                                btnLike.setChecked(true);
                            }
                        }

                        @Override
                        public void onFailure(Call<Entradas> call, Throwable t) { }
                    });
                }
            }
        });


        /*
        * Agregar un comentario al post
        * */
        btnComentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    //
            }
        });

    }

    public static String convertArrayToString(String[] strArray) {
        return Arrays.toString(strArray);
    }

}