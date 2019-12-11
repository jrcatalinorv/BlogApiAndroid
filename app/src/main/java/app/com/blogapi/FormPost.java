package app.com.blogapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import app.com.blogapi.entidades.Entradas;
import app.com.blogapi.servicio.BlogApiServices;
import app.com.blogapi.servicio.PostService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormPost extends AppCompatActivity {

    private EditText tituloPublicacion, contenidoPublicacion, tagsPublicacion;
    private Button cancelar, guardar;
    private String authToken, userName,userEmail;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_post);


        final PostService postService = BlogApiServices
                .getInstance().getPostService();


        tituloPublicacion = findViewById(R.id.ptTitle);
        contenidoPublicacion = findViewById(R.id.etBody);
        tagsPublicacion = findViewById(R.id.etTags);
        cancelar= findViewById(R.id.fpCancel);
        guardar = findViewById(R.id.fpSave);

        /* Obteniendo las variables almacenadas en el shared preferences */
        SharedPreferences pref = getApplicationContext().getSharedPreferences("BlogApiPref", MODE_PRIVATE);
        authToken = pref.getString("token",null);
        userName = pref.getString("name",null);
        userEmail = pref.getString("email",null);
        userId = pref.getInt("id",0);


        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelarPost();
            }
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String body = contenidoPublicacion.getText().toString();
                String [] tagsList = strToArrayList(tagsPublicacion.getText().toString());
                String title = tituloPublicacion.getText().toString();

                Entradas entradas = new Entradas(userId,body,tagsList,title,userEmail,userName);
                Call<Entradas> call = postService.entradaNueva("Bearer "+authToken, entradas);

                call.enqueue(new Callback<Entradas>() {
                    @Override
                    public void onResponse(Call<Entradas> call, Response<Entradas> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(FormPost.this, "El Post fue creado con exito!!!"+response.code(), Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(FormPost.this, "Error: "+response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Entradas> call, Throwable t) {
                        Toast.makeText(FormPost.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }


    public void cancelarPost(){

        /* Limpiar los campos */
        tituloPublicacion.setText("");
        contenidoPublicacion.setText("");
        tagsPublicacion.setText("");

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }


    /*
    * Convertir los tags recibidos en arreglo
    * */
  public static String[] strToArrayList(String strValues){
      String str[] = strValues.split(",");
      return str;
  }



}
