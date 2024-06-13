package com.universae.correctorexamenes.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.universae.correctorexamenes.ArreglosBD;
import com.universae.correctorexamenes.R;
import com.universae.correctorexamenes.adapters.ExamenAdapters;

import java.util.ArrayList;


public class PlantillaFragment extends Fragment {

    private String identificacion, codigo;
    private EditText etIdentificacion, etCodigo;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            identificacion = getArguments().getString("identificacion");
            codigo = getArguments().getString("codigo");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_plantilla, container, false);


        etCodigo = rootView.findViewById(R.id.eTCodigo);

        // Set the values from the arguments
        etIdentificacion.setText(identificacion);
        etCodigo.setText(codigo);


        if (identificacion != null || codigo != null) {

            // Get the activity context
            Context context = getActivity();
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, new String[]{"A", "B", "C", "D", "Null", "Empty"});

            ArreglosBD arreglosBD = new ArreglosBD();
            ArrayList<String> listaExamen = arreglosBD.existePlantillaEnDB(getContext(), codigo);

            // 1. get a reference to recyclerView
            RecyclerView rvExamen = rootView.findViewById(R.id.RVExamen);
            // 2. set layoutManger
            rvExamen.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            // 3. create an adapter
            ExamenAdapters mAdapter = new ExamenAdapters(listaExamen, adapter);
            // 4. set adapter
            rvExamen.setAdapter(mAdapter);
            // 5. set item animator to DefaultAnimator
            rvExamen.setItemAnimator(new DefaultItemAnimator());
        }

        return rootView;
    }

}