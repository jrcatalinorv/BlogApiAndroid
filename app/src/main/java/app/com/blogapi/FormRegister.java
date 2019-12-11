package app.com.blogapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

public class FormRegister extends AppCompatActivity {

    private EditText nombre, correo, origClave, confClave;
    private Button registar;
    private Boolean result;
    private String strPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_register);

        final SecurityService securityService = BlogApiServices
                .getInstance().getSecurityService();

        /* Guardar los datos en el SharedPreference */
        SharedPreferences pref = getApplicationContext().getSharedPreferences("BlogApiPref", MODE_PRIVATE);
        final SharedPreferences.Editor editor = pref.edit();

        nombre = findViewById(R.id.rgNombre);
        correo = findViewById(R.id.rgEmail);
        origClave = findViewById(R.id.rgPassword);
        confClave = findViewById(R.id.rgPassword2);
        registar = findViewById(R.id.rgBtn);

        registar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strPassword = confClave.getText().toString();
                result = origClave.getText().toString().equals(strPassword);
                if (result) {
                    Login login = new Login();
                    login.setName(nombre.getText().toString());
                    login.setEmail(correo.getText().toString());
                    login.setPassword(origClave.getText().toString());

                    Call<User> cuser = securityService.register(login);
                    cuser.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if (response.isSuccessful()) {
                                User user = response.body();

                                /* Guardar mi token el en shared preference y otros datos del usuarios */
                                editor.putInt("id", user.getId());
                                editor.putString("token", user.getToken());
                                editor.putString("name", user.getName());
                                editor.putString("email", user.getEmail());
                                editor.putString("password",strPassword);
                                editor.apply();

                                acceder();
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Toast.makeText(FormRegister.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{
                    Toast.makeText(FormRegister.this, "Las contrasenas no coinciden", Toast.LENGTH_SHORT).show();
                    origClave.setText("");
                    confClave.setText("");
                }

            }
        });
    }

    public void acceder(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

}
