package com.universae.correctorexamenes.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.universae.correctorexamenes.ArreglosBD;
import com.universae.correctorexamenes.R;
import com.universae.correctorexamenes.SQLiteDB.ExamenAppController;
import com.universae.correctorexamenes.adapters.ExamenAdapters;
import com.universae.correctorexamenes.models.Alumno;

import java.util.ArrayList;
import java.util.Arrays;

public class ExamenFragment extends Fragment {
    private static String[] lista;
    private String identificacion, codigo;
    private EditText etIdentificacion, etCodigo;
    private FloatingActionButton guardarFAB, borrarFAB;
    private ExamenAdapters mAdapter;
    private RecyclerView rvExamen;
    private ArrayAdapter<String> adapter;


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
        View rootView = inflater.inflate(R.layout.fragment_examen, container, false);
        ExamenAppController examenAppController = new ExamenAppController(getContext());

        etIdentificacion = rootView.findViewById(R.id.eTIdentificacion);
        etCodigo = rootView.findViewById(R.id.eTCodigo);
        guardarFAB = rootView.findViewById(R.id.fabGuardarE);
        borrarFAB = rootView.findViewById(R.id.fabBorrarE);

        borrarFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Alumno> alumno = examenAppController.obtenerAlumno(identificacion);
                for (Alumno a : alumno) {
                    if (a.getCodigo().equals(codigo) && a.getIdentificacion().equals(identificacion)) {
                        AlertDialog alertDialog = new AlertDialog
                                .Builder(getContext())
                                .setMessage("Esta seguro que desea borrar el Examen " + codigo + "\ndel alumno " + identificacion + ".")
                                .setPositiveButton("Aceptar", (dialog, which) -> {
                                    examenAppController.eliminarAlumno(a);
                                })
                                .setNegativeButton("Cerrar",(dialog, which) -> {
                                    dialog.dismiss();
                                })
                                .create();
                        alertDialog.show();
                    }
                }

            }
        });


        guardarFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Alumno> alumno = examenAppController.obtenerAlumno(identificacion);
                for (Alumno a : alumno) {
                    if (a.getCodigo().equals(codigo) && a.getIdentificacion().equals(identificacion)) {
                        Long id = a.getId();
                        String lista = Arrays.toString(getLista());
                        lista.replace("[", "").replace("]", "");
                        Alumno examenCambiado = new Alumno(identificacion, codigo, lista, id);

                        examenAppController.guardarCambios(examenCambiado);

                    }
                }


            }
        });


        // Set the values from the arguments
        etIdentificacion.setText(identificacion);
        etCodigo.setText(codigo);


        if (identificacion != null || codigo != null) {

            // Get the activity context
            Context context = getActivity();
            // Adaptador personalizado para poder cambiar el texto del spinner
            adapter = new MyColorizedArrayAdapter(context, android.R.layout.simple_spinner_item, new String[]{"A", "B", "C", "D", "Null", "Empty"});

            ArreglosBD arreglosBD = new ArreglosBD();
            ArrayList<String> listaExamen = arreglosBD.existeAlumnoEnDB(getContext(), identificacion, codigo);
            String[] listaNueva = cambiarLista(listaExamen);

            // 1. get a reference to recyclerView
            rvExamen = rootView.findViewById(R.id.RVExamen);
            // 2. set layoutManger
            rvExamen.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            // 3. create an adapter
            mAdapter = new ExamenAdapters(listaNueva, adapter);
            // 4. set adapter
            rvExamen.setAdapter(mAdapter);
            // 5. set item animator to DefaultAnimator
            rvExamen.setItemAnimator(new DefaultItemAnimator());
        }

        return rootView;
    }

    public String[] cambiarLista(ArrayList<String> listaExamen) {
        String[] listaNueva = new String[40];


        for (int i = 0; i <= 39; i++) {
            listaNueva[i] = listaExamen.get(i);

        }

        return listaNueva;
    }

    public String[] getLista() {
        return lista;
    }

    public void setLista(String[] lista) {
        ExamenFragment.lista = lista;
    }

    public class MyColorizedArrayAdapter extends ArrayAdapter<String> {

        public MyColorizedArrayAdapter(Context context, int layoutResource, String[] items) {
            super(context, layoutResource, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = (TextView) super.getView(position, convertView, parent);

            // Establecer color de texto rojo
            textView.setTextColor(Color.WHITE);
            textView.setTextSize(20);

            return textView;
        }

    }
    public void crearDialog() {


    }


}