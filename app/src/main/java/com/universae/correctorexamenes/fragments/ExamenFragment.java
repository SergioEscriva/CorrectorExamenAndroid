package com.universae.correctorexamenes.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.universae.correctorexamenes.ArreglosBD;
import com.universae.correctorexamenes.R;
import com.universae.correctorexamenes.adapters.ExamenAdapters;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExamenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExamenFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String identificacion, codigo;
    private RecyclerView rvExamen;
    public ExamenFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExamenFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExamenFragment newInstance(String param1, String param2) {
        ExamenFragment fragment = new ExamenFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_examen, container, false);
        ArreglosBD arreglosBD = new ArreglosBD();

        ArrayList<String> listaExamen = new ArrayList<>();
        identificacion = "54872351E";
        codigo = "016";

        listaExamen = arreglosBD.existeAlumnoEnDB(getContext(), identificacion, codigo);

        // 1. get a reference to recyclerView
        rvExamen = rootView.findViewById(R.id.RVExamen);
        // 2. set layoutManger
        rvExamen.setLayoutManager(new LinearLayoutManager(getActivity()));


        // 3. create an adapter
        ExamenAdapters mAdapter = new ExamenAdapters(listaExamen);
        // 4. set adapter
        rvExamen.setAdapter(mAdapter);
        // 5. set item animator to DefaultAnimator
        rvExamen.setItemAnimator(new DefaultItemAnimator());

        return rootView;
    }





}