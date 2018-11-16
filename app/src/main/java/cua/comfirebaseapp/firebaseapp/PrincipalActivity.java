package cua.comfirebaseapp.firebaseapp;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PrincipalActivity extends AppCompatActivity {
    RecyclerView rvNotas;
    List<Notas> notasList;
    NotasAdapter notasAdapter;
    DatabaseReference databaseReference;
    ValueEventListener valueEventListener;
    ProgressBar pbCargando;
    FloatingActionButton fabAgregarNota;
    AlertDialog alert;

    EditText id,nombre,cantidad;
    Button guardarNota;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        rvNotas = findViewById(R.id.rvNotas);
        pbCargando = findViewById(R.id.pbCargando);
        fabAgregarNota = findViewById(R.id.fabAgregarNota);
        databaseReference = FirebaseDatabase.getInstance().getReference("Items");
        createData();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvNotas.setLayoutManager(linearLayoutManager);

        fabAgregarNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(PrincipalActivity.this);
                LayoutInflater inflater = PrincipalActivity.this.getLayoutInflater();
                View view = inflater.inflate(R.layout.agregar_nota,null);
                builder.setView(view);
                builder.setTitle("Agregar Nota");
                id = view.findViewById(R.id.id);
                nombre = view.findViewById(R.id.nombre);
                cantidad = view.findViewById(R.id.cantidad);
                guardarNota = view.findViewById(R.id.guardarNota);

                guardarNota.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String pId = id.getText().toString();
                        String pNombre = nombre.getText().toString();
                        int pCantidad = Integer.parseInt(cantidad.getText().toString());
                        Notas nota =  new Notas(pId,pNombre,pCantidad);
                        databaseReference.child("").push().setValue(nota);
                        alert.dismiss();
                    }
                });

                builder.create();
                alert = builder.show();
            }
        });
    }

    public void createData(){
        notasList = new ArrayList<>();
        valueEventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                notasList.clear();
                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                        Notas nota = snapshot.getValue(Notas.class);
                        nota.id = snapshot.getKey();
                        notasList.add(nota);
                    }
                }
                notasAdapter = new NotasAdapter(getApplicationContext(),notasList);
                rvNotas.setAdapter(notasAdapter);
                pbCargando.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("ERROR", databaseError.toString());
            }
        });
    }
}
