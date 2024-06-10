package com.universae.correctorexamenes;

import android.content.Context;

import com.universae.correctorexamenes.SQLiteDB.AlumnoAppController;
import com.universae.correctorexamenes.SQLiteDB.PlantillaAppController;
import com.universae.correctorexamenes.models.Alumno;
import com.universae.correctorexamenes.models.Plantilla;

import java.util.ArrayList;
import java.util.Map;

public class ArreglosBD {


    public void guardarDB(Context context, Map<Integer, String> listaAbajoMarcados, Map<String, String> arrayDatosArriba, String examenPlantilla) {
        PlantillaAppController appController = new PlantillaAppController(context);
        AlumnoAppController alumnoAppController = new AlumnoAppController(context);
        String codigo = "";
        String plantilla = "";
        String identificacion = "";

        for (Map.Entry<String, String> respuestasArriba : arrayDatosArriba.entrySet()) {
            String key = respuestasArriba.getKey();
            if (key.contains("codigo")) {
                codigo = respuestasArriba.getValue();
            } else if (key.contains("identificacion")) {
                identificacion = respuestasArriba.getValue();

            }
        }

        for (String respuestasAbajo : listaAbajoMarcados.values()) {
            plantilla += respuestasAbajo + ",";

        }

        switch (examenPlantilla) {
            case "examen":
                Alumno introducirDBalumno = new Alumno(identificacion, codigo, plantilla);
                alumnoAppController.nuevoAlumno(introducirDBalumno);
                break;
            case "plantilla":
                Plantilla introducirDBplantilla = new Plantilla(codigo, plantilla);
                appController.nuevoPlantilla(introducirDBplantilla);
                break;

        }

        System.out.println("Plantilla " + plantilla);

    }

    public ArrayList<String> existeEnDB(Context context, String codigo) {
        PlantillaAppController plantillaAppController = new PlantillaAppController(context);
        ArrayList<Plantilla> plantillaList = plantillaAppController.obtenerPlantillaId(codigo);
        ArrayList<String> respuestasDB = null;

        for (Plantilla plantilla : plantillaList) {
            String plantillacodigo = plantilla.getCodigo();
            if (plantillacodigo.equals(codigo)) {
                System.out.println("Ya existe la plantilla");
                respuestasDB.add(plantilla.getRespuestas());
            }

        }
        return respuestasDB;
    }


}
