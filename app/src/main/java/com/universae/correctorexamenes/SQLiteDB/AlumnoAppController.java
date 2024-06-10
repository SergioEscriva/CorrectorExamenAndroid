package com.universae.correctorexamenes.SQLiteDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.universae.correctorexamenes.models.Alumno;

import java.util.ArrayList;


public class AlumnoAppController {

    private AyudanteBaseDeDatos ayudanteBaseDeDatos;
    private String NOMBRE_TABLA = "alumno";


    public AlumnoAppController(Context contexto) {
        ayudanteBaseDeDatos = new AyudanteBaseDeDatos(contexto);
    }


    public int eliminarAlumno(Alumno alumno) {

        SQLiteDatabase baseDeDatos = ayudanteBaseDeDatos.getWritableDatabase();
        String[] argumentos = {String.valueOf(alumno.getIdentificacion())};
        return baseDeDatos.delete(NOMBRE_TABLA, "identificacion = ?", argumentos);
    }


    public long nuevoAlumno(Alumno alumno) {
        // writable porque vamos a insertar
        SQLiteDatabase baseDeDatos = ayudanteBaseDeDatos.getWritableDatabase();
        ContentValues valoresParaInsertar = new ContentValues();

        // Recuperamos Valores
        valoresParaInsertar.put("identificacion", alumno.getIdentificacion());
        valoresParaInsertar.put("codigo", alumno.getCodigo());
        valoresParaInsertar.put("respuestas", alumno.getRespuestas());

        // Agregamos a la BD
        long resultado = baseDeDatos.insert(NOMBRE_TABLA, null, valoresParaInsertar);
        return resultado;
    }


    public ArrayList<Alumno> obtenerAlumno(String identificacion) {
        ArrayList<Alumno> alumnos = new ArrayList<>();
        // readable porque no vamos a modificar, solamente leer
        SQLiteDatabase baseDeDatos = ayudanteBaseDeDatos.getReadableDatabase();


        // Los alumnos son tods.
        String selection = "identificacion= ?";
        String[] selectionArgs = {identificacion};
        String[] columnasAConsultar = {"identificacion", "codigo", "respuestas", "id"};

        // Los alumnos son de toda la app.

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

            return alumnos;

        }

        // Si no hay datos, igualmente regresamos la lista vacía
        if (! cursor.moveToFirst()) return alumnos;


        // El 0 es el número de la columna, como seleccionamos
        String identificacionObtenidoDeBD = String.valueOf(cursor.getString(0));
        String codigoObtenidoDeBD = String.valueOf(cursor.getString(1));
        String respuestasObtenidoDeBD = String.valueOf(cursor.getString(2));
        Long alumnoIdObtenidoDeBD = (cursor.getLong(3));
        Alumno usuarioObtenidaDeBD = new Alumno(identificacionObtenidoDeBD, codigoObtenidoDeBD, respuestasObtenidoDeBD, alumnoIdObtenidoDeBD);
        alumnos.add(usuarioObtenidaDeBD);

        // Fin del ciclo. Cerramos cursor y regresamos la lista
        cursor.close();

        return alumnos;
    }

}
