package com.universae.correctorexamenes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.universae.correctorexamenes.adapters.ExamenAdapters;
import com.universae.correctorexamenes.fragments.ExamenFragment;

public class EditarActivity extends AppCompatActivity {
    private String identificacion, codigo;
    private EditText etIdentificacion, etCodigo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        identificacion = extras.getString("identificacion");
        codigo = extras.getString("codigo");


        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_editar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        View view = this.getWindow().getDecorView().findViewById(android.R.id.content);
        //Crea View con inflater
        LayoutInflater inflater =
                (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);

        View fragmentExamen = inflater.inflate(R.layout.fragment_examen, null);
        etIdentificacion = fragmentExamen.findViewById(R.id.eTIdentificacion);
        etIdentificacion.setText(identificacion);
        etCodigo = fragmentExamen.findViewById(R.id.eTCodigo);
        etCodigo.setText(codigo);

//        View activityTransactionView = inflater.inflate(R.layout.activity_add_transaction, null);
//        ViewGroup viewGroup = null;
//        View filaWallet = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_examen, viewGroup, false);
//        tvIdentificacion = filaWallet.findViewById(R.id.tVIdentificacion);
//        tvIdentificacion.setText(identificacion);

    }







}