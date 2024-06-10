package com.universae.correctorexamenes.SQLiteDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.universae.correctorexamenes.models.Plantilla;

import java.util.ArrayList;


public class PlantillaAppController {

    private AyudanteBaseDeDatos ayudanteBaseDeDatos;
    private String NOMBRE_TABLA = "plantilla";


    public PlantillaAppController(Context contexto) {
        ayudanteBaseDeDatos = new AyudanteBaseDeDatos(contexto);
    }


    public int eliminarPlantilla(Plantilla plantilla) {

        SQLiteDatabase baseDeDatos = ayudanteBaseDeDatos.getWritableDatabase();
        String[] argumentos = {String.valueOf(plantilla.getCodigo())};
        return baseDeDatos.delete(NOMBRE_TABLA, "codigo = ?", argumentos);
    }


    public long nuevoPlantilla(Plantilla plantilla) {
        // writable porque vamos a insertar
        SQLiteDatabase baseDeDatos = ayudanteBaseDeDatos.getWritableDatabase();
        ContentValues valoresParaInsertar = new ContentValues();

        // Recuperamos Valores

        valoresParaInsertar.put("codigo", plantilla.getCodigo());
        valoresParaInsertar.put("respuestas", plantilla.getRespuestas());

        // Agregamos a la BD
        long resultado = baseDeDatos.insert(NOMBRE_TABLA, null, valoresParaInsertar);
        return resultado;
    }


    public ArrayList<Plantilla> obtenerPlantillaId(String codigo) {
        ArrayList<Plantilla> plantillas = new ArrayList<>();

        // readable porque no vamos a modificar, solamente leer
        SQLiteDatabase baseDeDatos = ayudanteBaseDeDatos.getReadableDatabase();

        // Los plantillas son tods.
        String selection = "codigo= ?";
        String[] selectionArgs = {codigo};
        String[] columnasAConsultar = {"codigo", "respuestas", "id"};

        // Los plantillas son de toda la app.

        Cursor cursor = baseDeDatos.query(
                NOMBRE_TABLA,
                columnasAConsultar,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor == null) {
            /*
                Salimos aquí porque hubo un error, regresar
                lista vacía
             */
            return plantillas;

        }

        // Si no hay datos, igualmente regresamos la lista vacía
        if (! cursor.moveToFirst()) return plantillas;

        // El 0 es el número de la columna, como seleccionamos

        String codigoObtenidoDeBD = cursor.getString(0);
        String respuestasObtenidoDeBD = cursor.getString(1);
        Long plantillaIdObtenidoDeBD = cursor.getLong(2);
        Plantilla usuarioObtenidaDeBD = new Plantilla(codigoObtenidoDeBD, respuestasObtenidoDeBD, plantillaIdObtenidoDeBD);
        plantillas.add(usuarioObtenidaDeBD);

        // Fin del ciclo. Cerramos cursor y regresamos la lista
        cursor.close();

        return plantillas;
    }

}
