package com.universae.correctorexamenes.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.universae.correctorexamenes.R;
import java.util.ArrayList;


public class ExamenAdapters extends RecyclerView.Adapter<ExamenAdapters.MyViewHolder> {

    private ArrayList<String> respuestasExamen;



    public ExamenAdapters(ArrayList<String> respuesta) {

        this.respuestasExamen = respuesta;
    }


    public void setRespuestasExamen(ArrayList<String> respuestasExamen) {
        this.respuestasExamen = respuestasExamen;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View filaWallet = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rellenar_rv, viewGroup, false);
        return new MyViewHolder(filaWallet);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        System.out.println("i " + i);
        System.out.println("respuestas " + respuestasExamen.get(i) );
        System.out.println("tamaño " + respuestasExamen.size());
        // Obtener la de nuestra lista gracias al índice i
        String respuesta= respuestasExamen.get(i);

        // Obtener los datos de la lista
        String numeroPregunta = String.valueOf(i + 1);

        // Y poner a los TextView los datos con setText
        myViewHolder.tVNumeroPregunta.setText(numeroPregunta);
        myViewHolder.eTRespuesta.setText(String.valueOf(respuesta));

    }


    @Override
    public int getItemCount() {
        return respuestasExamen.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tVNumeroPregunta;
        EditText eTRespuesta;


        MyViewHolder(View itemView) {
            super(itemView);
            this.tVNumeroPregunta = itemView.findViewById(R.id.tVNumeroPregunta);
            this.eTRespuesta = itemView.findViewById(R.id.eTRespuesta);

        }
    }

}
