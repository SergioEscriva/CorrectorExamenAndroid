package com.universae.correctorexamenes;

import android.content.Context;

import com.universae.correctorexamenes.SQLiteDB.AlumnoAppController;
import com.universae.correctorexamenes.SQLiteDB.PlantillaAppController;
import com.universae.correctorexamenes.models.String;
import com.universae.correctorexamenes.models.Plantilla;

import java.util.ArrayList;
import java.util.Map;

public class ArreglosBD {


    public java.lang.String guardarDB(Context context, ArrayList<java.lang.String> listaAbajoMarcados, Map<java.lang.String, java.lang.String> arrayDatosArriba, java.lang.String examenPlantilla) {
        PlantillaAppController appController = new PlantillaAppController(context);
        AlumnoAppController alumnoAppController = new AlumnoAppController(context);
        java.lang.String codigo = "";
        java.lang.String plantilla = "";
        java.lang.String identificacion = "";

        for (Map.Entry<java.lang.String, java.lang.String> respuestasArriba : arrayDatosArriba.entrySet()) {
            java.lang.String key = respuestasArriba.getKey();
            if (key.contains("codigo")) {
                codigo = respuestasArriba.getValue();
            } else if (key.contains("identificacion")) {
                identificacion = respuestasArriba.getValue();

            }
        }

        //        for (String respuestasAbajo : listaAbajoMarcados.values()) {
        //            plantilla += respuestasAbajo + ",";
        //
        //        }
        plantilla = listaAbajoMarcados.toString();

        switch (examenPlantilla) {
            case "examen":
                String introducirDBalumno = new String(identificacion, codigo, plantilla);
                alumnoAppController.nuevoAlumno(introducirDBalumno);
                break;
            case "plantilla":
                Plantilla introducirDBplantilla = new Plantilla(codigo, plantilla);
                appController.nuevoPlantilla(introducirDBplantilla);
                break;

        }
        return codigo;

    }

    public ArrayList<java.lang.String> existePlantillaEnDB(Context context, java.lang.String codigo) {
        PlantillaAppController plantillaAppController = new PlantillaAppController(context);
        ArrayList<Plantilla> plantillaList = plantillaAppController.obtenerPlantillaId(codigo);
        ArrayList<java.lang.String> respuestasDB = new ArrayList<>();

        // Array de las  respuestas de la plantilla de la base de datos
        for (Plantilla plantilla : plantillaList) {
            java.lang.String plantillaKey = plantilla.getCodigo();
            java.lang.String plantillaValores = plantilla.getRespuestas();
            if (plantillaKey.equals(codigo)) {
                for (java.lang.String respuestas : plantillaValores.split(",")) {
                    respuestasDB.add(respuestas);
                }
            }

        }
        return respuestasDB;
    }

    public ArrayList<java.lang.String> existeAlumnoEnDB(Context context, java.lang.String identificacion, java.lang.String codigo) {
        AlumnoAppController alumnoDB = new AlumnoAppController(context);
        ArrayList<String> alumnoList = alumnoDB.obtenerAlumno(identificacion);
        ArrayList<java.lang.String> examenDB = new ArrayList<>();

        // Array de las  respuestas de la plantilla de la base de datos
        for (String alumno : alumnoList) {
            java.lang.String plantillaKey = alumno.getCodigo();
            java.lang.String plantillaValores = alumno.getRespuestas();
            if (plantillaKey.equals(codigo)) {
                for (java.lang.String respuestas : plantillaValores.split(",")) {
                    examenDB.add(respuestas);
                }
            }

        }
        return examenDB;
    }


}
