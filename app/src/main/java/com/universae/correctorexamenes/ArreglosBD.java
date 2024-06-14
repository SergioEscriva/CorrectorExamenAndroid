package com.universae.correctorexamenes;

import android.content.Context;

import com.universae.correctorexamenes.SQLiteDB.AlumnoAppController;
import com.universae.correctorexamenes.SQLiteDB.PlantillaAppController;
import com.universae.correctorexamenes.models.Alumno;
import com.universae.correctorexamenes.models.Par;
import com.universae.correctorexamenes.models.Plantilla;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArreglosBD {


    public String guardarDB(Context context, ArrayList<String> listaAbajoMarcados, List<Par> listaMarcadosPlantilla, Map<String, String> arrayDatosArriba, String examenPlantilla) {
        PlantillaAppController appController = new PlantillaAppController(context);
        AlumnoAppController alumnoAppController = new AlumnoAppController(context);
        String codigo = "";
        String plantilla = "";
        String identificacion = "";
        String coordenadas = "";

        for (Map.Entry<String, String> respuestasArriba : arrayDatosArriba.entrySet()) {
            String key = respuestasArriba.getKey();
            if (key.contains("codigo")) {
                codigo = respuestasArriba.getValue();
            } else if (key.contains("identificacion")) {
                identificacion = respuestasArriba.getValue();

            }
        }

        for (String respuestasAbajo : listaAbajoMarcados) {
            respuestasAbajo.replace("[]", "");
            plantilla += respuestasAbajo + ",";

        }


        coordenadas = listaMarcadosPlantilla.toString();

        switch (examenPlantilla) {
            case "examen":
                Alumno introducirDBalumno = new Alumno(identificacion, codigo, plantilla);
                alumnoAppController.nuevoAlumno(introducirDBalumno);
                break;
            case "plantilla":
                Plantilla introducirDBplantilla = new Plantilla(codigo, plantilla, coordenadas);
                appController.nuevoPlantilla(introducirDBplantilla);
                break;

        }
        return codigo;

    }

    public ArrayList<String> existePlantillaEnDB(Context context, String codigo) {
        PlantillaAppController plantillaAppController = new PlantillaAppController(context);
        ArrayList<Plantilla> plantillaList = plantillaAppController.obtenerPlantillaId(codigo);
        ArrayList<String> respuestasDB = new ArrayList<>();

        // Array de las  respuestas de la plantilla de la base de datos
        for (Plantilla plantilla : plantillaList) {
            String plantillaKey = plantilla.getCodigo();
            String plantillaValores = plantilla.getRespuestas();
            if (plantillaKey.equals(codigo)) {
                for (String respuestas : plantillaValores.split(",")) {
                    respuestasDB.add(respuestas);
                }
            }

        }
        return respuestasDB;
    }


    public List<Par> coordenadasPlantillaEnDB(Context context, String codigo) {
        PlantillaAppController plantillaAppController = new PlantillaAppController(context);
        ArrayList<Plantilla> plantillaList = plantillaAppController.obtenerPlantillaId(codigo);
        List<Par> respuestasDB = new ArrayList<>();
        String plantilla2 = "";
        List<Par> parList = new ArrayList<>();

        // Array de las  respuestas de la plantilla de la base de datos
        for (Plantilla plantilla : plantillaList) {
            String plantillaKey = plantilla.getCodigo();
            String plantillaCoordenadas = plantilla.getCoordenadas();

            if (plantillaKey.equals(codigo)) {
                // Remove the square brackets and split by "}, {"
                plantilla2 = plantillaCoordenadas.substring(1, plantillaCoordenadas.length() - 1);
                String[] pairs = plantilla2.split("\\}, \\{");


                // Iterar los pares de coordenadas
                for (String pair : pairs) {
                    // Remove curly braces and split by comma
                    pair = pair.replace("{", "").replace("}", "");
                    String[] coordinates = pair.split(", ");

                    // Parse coordinates to double and create Par objects
                    double x = Double.parseDouble(coordinates[0]);
                    double y = Double.parseDouble(coordinates[1]);

                    parList.add(new Par(x, y));
                }

            }

        }
        return parList;
    }

    public ArrayList<String> existeAlumnoEnDB(Context context, String identificacion, String codigo) {
        AlumnoAppController alumnoDB = new AlumnoAppController(context);
        ArrayList<Alumno> alumnoList = alumnoDB.obtenerAlumno(identificacion);
        ArrayList<String> examenDB = new ArrayList<>();

        // Array de las  respuestas de la plantilla de la base de datos
        for (Alumno alumno : alumnoList) {
            String plantillaKey = alumno.getCodigo();
            String plantillaValores = alumno.getRespuestas();
            if (plantillaKey.equals(codigo)) {
                for (String respuestas : plantillaValores.split(",")) {
                    examenDB.add(respuestas);
                }
            }

        }
        return examenDB;
    }


}
