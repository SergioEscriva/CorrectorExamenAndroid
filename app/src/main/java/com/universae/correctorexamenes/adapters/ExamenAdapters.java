package com.universae.correctorexamenes.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.universae.correctorexamenes.R;
import com.universae.correctorexamenes.fragments.ExamenFragment;


public class ExamenAdapters extends RecyclerView.Adapter<ExamenAdapters.MyViewHolder> {

    private String[] respuestasExamen;
    private ArrayAdapter<String> adapter;

    private String letra;


    public ExamenAdapters(String[] respuesta, ArrayAdapter<String> adapter) {

        this.respuestasExamen = respuesta;
        this.adapter = adapter;
    }


    public void setRespuestasExamen(String[] respuestasExamen, ArrayAdapter<String> adapter) {
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
        String respuesta = respuestasExamen[i];


        int letraRespuesta = 0;
        letra = "";

        if (respuesta.contains("A")) {
            letraRespuesta = 0;
            letra = "A";
        } else if (respuesta.contains("B")) {
            letraRespuesta = 1;
            letra = "B";
        } else if (respuesta.contains("C")) {
            letraRespuesta = 2;
            letra = "C";
        } else if (respuesta.contains("D")) {
            letraRespuesta = 3;
            letra = "D";
        } else if (respuesta.contains("Null")) {
            letraRespuesta = 4;
            letra = "Null";
        } else if (respuesta.contains("Empty")) {
            letraRespuesta = 5;
            letra = "Empty";

        }

        //Spinner
        myViewHolder.spinner.setAdapter(adapter);
        myViewHolder.spinner.setSelection(letraRespuesta);

        // Obtener los datos de la lista
        String numeroPregunta = String.valueOf(i + 1);

        // Y poner a los TextView los datos con setText
        myViewHolder.tVNumeroPregunta.setText(numeroPregunta);
        myViewHolder.tVNumeroPregunta.setTextColor(Color.WHITE);
        myViewHolder.spinner.setBackgroundColor(Color.WHITE);

        myViewHolder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                respuestasExamen[Integer.parseInt(numeroPregunta) - 1] = item;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ExamenFragment examenFragment = new ExamenFragment();
        examenFragment.setLista(respuestasExamen);


    }


    @Override
    public int getItemCount() {
        int tamano = respuestasExamen.length;
        return tamano;
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
