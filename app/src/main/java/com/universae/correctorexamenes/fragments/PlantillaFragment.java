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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.universae.correctorexamenes.ArreglosBD;
import com.universae.correctorexamenes.R;
import com.universae.correctorexamenes.SQLiteDB.AlumnoAppController;
import com.universae.correctorexamenes.SQLiteDB.PlantillaAppController;
import com.universae.correctorexamenes.adapters.ExamenAdapters;
import com.universae.correctorexamenes.models.Alumno;
import com.universae.correctorexamenes.models.Plantilla;

import java.util.ArrayList;
import java.util.Arrays;


public class PlantillaFragment extends Fragment {

    private String codigo;
    private EditText  etCodigo;

    private FloatingActionButton guardarFAB;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            codigo = getArguments().getString("codigo");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_plantilla, container, false);
        etCodigo = rootView.findViewById(R.id.eTCodigo);
        guardarFAB = rootView.findViewById(R.id.fabGuardarP);

        guardarFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlantillaAppController plantillaAppController = new PlantillaAppController(getContext());
                ArrayList<Plantilla> plantilla = plantillaAppController.obtenerPlantillaId(codigo);


                for (Plantilla p : plantilla) {
                    String coordenadas = p.getCoordenadas();
                    long id = p.getId();
                    ExamenFragment examenFragment = new ExamenFragment();
                        String lista = Arrays.toString(examenFragment.getLista());
                        lista =lista.replace("[]", "");
                        Plantilla plantillaCambiado = new Plantilla(codigo, lista, coordenadas, id);

                        plantillaAppController.guardarCambios(plantillaCambiado);


                }


            }
        });



        // Set the values from the arguments
        etCodigo.setText(codigo);


        if ( codigo != null) {

            // Get the activity context
            Context context = getActivity();
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, new String[]{"A", "B", "C", "D", "Null", "Empty"});

            ArreglosBD arreglosBD = new ArreglosBD();
            ArrayList<String> listaExamen = arreglosBD.existePlantillaEnDB(getContext(), codigo);
            ExamenFragment examenFragment = new ExamenFragment();
            String[] listaNueva = examenFragment.cambiarLista(listaExamen);

            // 1. get a reference to recyclerView
            RecyclerView rvExamen = rootView.findViewById(R.id.RVExamen);
            // 2. set layoutManger
            rvExamen.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            // 3. create an adapter
            ExamenAdapters mAdapter = new ExamenAdapters(listaNueva, adapter);
            // 4. set adapter
            rvExamen.setAdapter(mAdapter);
            // 5. set item animator to DefaultAnimator
            rvExamen.setItemAnimator(new DefaultItemAnimator());
        }

        return rootView;
    }

}