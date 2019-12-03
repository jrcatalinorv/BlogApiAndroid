package app.com.loginexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import app.com.loginexample.servicio.BlogApiServices;
import app.com.loginexample.servicio.PostService;

public class FormPost extends AppCompatActivity {

    private EditText tituloPublicacion, contenidoPublicacion, tagsPublicacion;
    private Button cancelar, guardar;


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

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelarPost();
            }
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



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





}
