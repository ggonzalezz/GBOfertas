package gb.gerson.gb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.muddzdev.styleabletoast.StyleableToast;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextInputEditText gmail, password;
    private Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Agregando titulo centrado en el actionBar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.txt_titulo);
        mAuth = FirebaseAuth.getInstance();

        gmail = findViewById(R.id.gmail);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // creamos dos variables que conviertan las entradas a un tostring
                //trim(() para eliminar espacios al inicio y al final
                String userE = gmail.getText().toString().trim();
                String passE = password.getText().toString().trim();
                // luego creamos una condicion diciendo si viene en blanco manda un mensaje que diga coloca algo
                if (TextUtils.isEmpty(userE)){
                    StyleableToast.makeText(LoginActivity.this, "Enter a user", Toast.LENGTH_SHORT, R.style.mytoast).show();
                }
                // las misma condicional para el campo passE
                else if (TextUtils.isEmpty(passE)){
                    StyleableToast.makeText(LoginActivity.this, "Enter a password", Toast.LENGTH_SHORT, R.style.mytoast).show();

                }else
                {
                    // utilizamos la variable que declaramos al principio
                    // le decimos que queremos loguearnos por correo y contrase;a
                    mAuth.signInWithEmailAndPassword(userE,passE)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    // si la tarea es diferente de exito pues mandar un mensaje que algo esta
                                    // incorrecto
                                    StyleableToast.makeText(LoginActivity.this, "Connecting..", Toast.LENGTH_SHORT, R.style.mytoast).show();
                                    if (!task.isSuccessful()){
                                        StyleableToast.makeText(LoginActivity.this, "Incorrect email or password", Toast.LENGTH_SHORT, R.style.mytoast).show();
                                    }else {
                                        // si sale bien lanzarse de  una actividad hacia otra
                                        Intent intent = new Intent(LoginActivity.this,MainMenuActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                }
            }
        });
    }
}
