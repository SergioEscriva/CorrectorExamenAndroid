package com.universae.correctorexamenes;

import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.tabs.TabLayout;
import com.universae.correctorexamenes.fragments.ExamenFragment;
import com.universae.correctorexamenes.fragments.PlantillaFragment;

public class EditarActivity extends AppCompatActivity {
    private String identificacion, codigo;

    private TabLayout tab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        identificacion = extras.getString("identificacion");
        codigo = extras.getString("codigo");
        Bundle bundle = new Bundle();
        bundle.putString("identificacion", identificacion);
        bundle.putString("codigo", codigo);


        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_editar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tab = findViewById(R.id.tabLayout);
        tab.setSelectedTabIndicatorColor(Color.WHITE);
        tab.setTabTextColors(Color.parseColor("#FFBDBFBC"), Color.WHITE);
        tab.setBackgroundColor(Color.parseColor("#221F73"));


        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String pestana = tab.getText().toString();

                switch (pestana) {
                    case "Examen":
                        ExamenFragment examenFragment = new ExamenFragment();
                        examenFragment.setArguments(bundle);

                        // Inicia la transacción del fragmento
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragmentContainerView3, examenFragment)
                                .commit();

                        break;
                    case "Plantilla":
                        PlantillaFragment plantillaFragment = new PlantillaFragment();
                        plantillaFragment.setArguments(bundle);

                        // Inicia la transacción del fragmento
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragmentContainerView3, plantillaFragment)
                                .commit();

                        break;

                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        // Configura el fragmento y pasa los datos
        ExamenFragment examenFragment = new ExamenFragment();
        examenFragment.setArguments(bundle);

        // Inicia la transacción del fragmento
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView3, examenFragment)
                .commit();

    }


}


