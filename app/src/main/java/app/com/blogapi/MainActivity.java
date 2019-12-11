package app.com.blogapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import app.com.blogapi.entidades.Login;
import app.com.blogapi.entidades.User;
import app.com.blogapi.servicio.BlogApiServices;
import app.com.blogapi.servicio.SecurityService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private EditText email, password;
    private Button login, register;
    private String passwordSTR, validToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SecurityService securityService = BlogApiServices
                .getInstance().getSecurityService();

        /*
        *
        * */
        SharedPreferences prefNew = getApplicationContext().getSharedPreferences("BlogApiPref", MODE_PRIVATE);
        validToken = prefNew.getString("token", null);
        if (validToken != null) {
          acceder();
        }

        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);
        login = findViewById(R.id.btnLogin);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("BlogApiPref", MODE_PRIVATE);
        final SharedPreferences.Editor editor = pref.edit();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Login login = new Login();
                login.setEmail(email.getText().toString());
                login.setPassword(password.getText().toString());
                passwordSTR = password.getText().toString();

                Call<User> cuser = securityService.login(login);
                cuser.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {

                        if (response.code()== 201){
                            User user = response.body();

                            /* Guardar mi token el en shared preference y otros datos del usuarios */
                            editor.putInt("id", user.getId());
                            editor.putString("token", user.getToken());
                            editor.putString("name", user.getName());
                            editor.putString("email", user.getEmail());
                            editor.putString("password",passwordSTR );
                            editor.apply();

                            acceder();
                        }else{
                            Toast.makeText(MainActivity.this, "Fallo la autenticacion", Toast.LENGTH_SHORT).show();
                        }

                    }
                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }

    public void acceder(){
        Intent intent = new Intent(this,MainMenu.class);
        startActivity(intent);
    }
}
