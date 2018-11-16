package cua.comfirebaseapp.firebaseapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class NotasAdapter extends RecyclerView.Adapter<NotasAdapter.ViewHolder> {
    List<Notas> notasList;
    Context context;
    DatabaseReference databaseReference;
    public NotasAdapter(Context context, List<Notas> notasList) {
        this.context = context;
        this.notasList = notasList;
        databaseReference = FirebaseDatabase.getInstance().getReference("Items");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nota,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.count.setText(notasList.get(position).getCount() + "");
        holder.nombre.setText(notasList.get(position).getNombre());
        holder.imgBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child(notasList.get(position).getId()).removeValue();
            }
        });
    }

    @Override
    public int getItemCount() {
        return notasList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CardView cvNota;
        TextView count,nombre;
        ImageView imgBorrar;
        public ViewHolder(View v) {
            super(v);
            cvNota = v.findViewById(R.id.cvNota);
            count = v.findViewById(R.id.count);
            nombre = v.findViewById(R.id.nombre);
            imgBorrar = v.findViewById(R.id.imgBorrar);
        }
    }
}
