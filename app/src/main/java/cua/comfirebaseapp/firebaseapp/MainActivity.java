package cua.comfirebaseapp.firebaseapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    EditText email,password;
    Button login,registrar;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        registrar = findViewById(R.id.registrar);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userE = email.getText().toString().trim();
                String passwordE = password.getText().toString().trim();
                if (TextUtils.isEmpty(userE)){
                    Toast.makeText(getApplicationContext(),"Debe ingresar el correo",Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(passwordE)){
                    Toast.makeText(getApplicationContext(),"Debe ingresar la clave",Toast.LENGTH_LONG).show();
                    return;
                }
                auth.signInWithEmailAndPassword(userE,passwordE).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Correo o Clave invalidos",Toast.LENGTH_SHORT).show();
                        }else{
                            Intent intent = new Intent(getApplicationContext(),PrincipalActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }
        });

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),RegistrarActivity.class);
                startActivity(intent);
            }
        });
    }
}
