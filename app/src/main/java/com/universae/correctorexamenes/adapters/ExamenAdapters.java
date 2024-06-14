package com.universae.correctorexamenes.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.universae.correctorexamenes.R;

import java.util.ArrayList;


public class ExamenAdapters extends RecyclerView.Adapter<ExamenAdapters.MyViewHolder> {

    private ArrayList<String> respuestasExamen;
    private ArrayAdapter<String> adapter;


    public ExamenAdapters(ArrayList<String> respuesta, ArrayAdapter<String> adapter) {

        this.respuestasExamen = respuesta;
        this.adapter = adapter;
    }


    public void setRespuestasExamen(ArrayList<String> respuestasExamen, ArrayAdapter<String> adapter) {
        this.respuestasExamen = respuestasExamen;
        this.adapter = adapter;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View filaWallet = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rellenar_rv, viewGroup, false);
        return new MyViewHolder(filaWallet);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        // Obtener la de nuestra lista gracias al índice i
        String respuesta = respuestasExamen.get(i);

        int letraRespuesta = 0;

        if (respuesta.equals("A")) {
            letraRespuesta = 0;
        } else if (respuesta.equals("B")) {
            letraRespuesta = 1;
        } else if (respuesta.equals("C")) {
            letraRespuesta = 2;
        } else if (respuesta.equals("D")) {
            letraRespuesta = 3;
        } else if (respuesta.equals("Null")) {
            letraRespuesta = 4;
        } else if (respuesta.equals("Empty")) {
            letraRespuesta = 5;

        }

        //Spinner
        myViewHolder.spinner.setAdapter(adapter);
        myViewHolder.spinner.setSelection(letraRespuesta);


        // Obtener los datos de la lista
        String numeroPregunta = String.valueOf(i + 1);

        // Y poner a los TextView los datos con setText
                myViewHolder.tVNumeroPregunta.setText(numeroPregunta);
        //        String[] hola = {respuesta.toString()};
        //        hola[i] = adapter.getItem(i);
        //        System.out.println(Arrays.toString(hola));

    }


    @Override
    public int getItemCount() {
        return respuestasExamen.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tVNumeroPregunta;

        Spinner spinner;


        MyViewHolder(View itemView) {
            super(itemView);
            this.tVNumeroPregunta = itemView.findViewById(R.id.tVNumeroPregunta);

            this.spinner = itemView.findViewById(R.id.spinnerExamen);

        }

    }

}
