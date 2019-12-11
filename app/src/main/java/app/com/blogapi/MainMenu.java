package app.com.blogapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import app.com.blogapi.adaptadores.AdaptadorEntrada;
import app.com.blogapi.entidades.Entradas;
import app.com.blogapi.entidades.Login;
import app.com.blogapi.entidades.User;
import app.com.blogapi.servicio.BlogApiServices;
import app.com.blogapi.servicio.PostService;
import app.com.blogapi.servicio.SecurityService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainMenu extends AppCompatActivity {

    public String authToken, email, pass;
    private RecyclerView rvEntradasLista;
    private List<Entradas> entradasLista;
    private  AdaptadorEntrada adaptadorEntrada;
    public static final String EXTRA_ID ="app.com.blogapi.EXTRA_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        final  PostService postService = BlogApiServices
                .getInstance().getPostService();

        final SecurityService securityService = BlogApiServices
                .getInstance().getSecurityService();

        rvEntradasLista = findViewById(R.id.rvListaPost);

        /* Obteniendo las variables almacenadas en el shared preferences */
        SharedPreferences pref = getApplicationContext().getSharedPreferences("BlogApiPref", MODE_PRIVATE);

        authToken = pref.getString("token", null);
        email = pref.getString("email", null);
        pass = pref.getString("password", null);
        rvEntradasLista.setLayoutManager(new LinearLayoutManager(this));




        Call<List<Entradas>> call = postService.getEstrada("Bearer "+authToken);
        call.enqueue(new Callback<List<Entradas>>() {
            @Override
            public void onResponse(Call<List<Entradas>> call, final Response<List<Entradas>> response) {
                if (response.isSuccessful()) {

                    entradasLista = response.body();

                    Collections.reverse(entradasLista);
                    adaptadorEntrada = new AdaptadorEntrada(entradasLista);


                    adaptadorEntrada.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            int id = entradasLista.get(rvEntradasLista.getChildAdapterPosition(v)).getId();
                            viewPost(id);

                        }
                    });
                    rvEntradasLista.setAdapter(adaptadorEntrada);
                }else{
                    Toast.makeText(MainMenu.this, "Error: "+response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Entradas>> call, Throwable t) {
                Toast.makeText(MainMenu.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.item2:
                FormularioCrearNuevoPost();
                        return true;

            case R.id.item3:
                SharedPreferences pref = getApplicationContext().getSharedPreferences("BlogApiPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();

                /* Guardar mi token el en shared preference */
                editor.putString("token", null);
                editor.apply();
                salir();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void  FormularioCrearNuevoPost(){
        Intent intent = new Intent(this, FormPost.class);
        startActivity(intent);
    }

    public  void  salir(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void viewPost(int id){
        Intent intent = new Intent(this,VistaPost.class);
        intent.putExtra(EXTRA_ID,String.valueOf(id));
        startActivity(intent);
    }
}
