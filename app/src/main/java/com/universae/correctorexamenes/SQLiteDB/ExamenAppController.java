package com.universae.correctorexamenes.SQLiteDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.universae.correctorexamenes.models.Alumno;

import java.util.ArrayList;


public class ExamenAppController {

    private AyudanteBaseDeDatos ayudanteBaseDeDatos;
    private java.lang.String NOMBRE_TABLA = "alumno";


    public ExamenAppController(Context contexto) {
        ayudanteBaseDeDatos = new AyudanteBaseDeDatos(contexto);
    }


    public int eliminarAlumno(Alumno alumno) {

        SQLiteDatabase baseDeDatos = ayudanteBaseDeDatos.getWritableDatabase();
        String[] argumentos = {String.valueOf(alumno.getId())};
        return baseDeDatos.delete(NOMBRE_TABLA, "id = ?", argumentos);
    }


    public void nuevoAlumno(Alumno alumno) {

        // writable porque vamos a insertar
        SQLiteDatabase baseDeDatos = ayudanteBaseDeDatos.getWritableDatabase();
        ContentValues valoresParaInsertar = new ContentValues();
        //Comprobamos que el alumno no tenga ya corregido el mismo examen
        ArrayList<Alumno> bdAlumno = obtenerAlumno(alumno.getIdentificacion());

        if (bdAlumno.isEmpty()) {
            // Recuperamos Valores
            valoresParaInsertar.put("identificacion", alumno.getIdentificacion());
            valoresParaInsertar.put("codigo", alumno.getCodigo());
            valoresParaInsertar.put("respuestas", alumno.getRespuestas());

            // Agregamos a la BD
            baseDeDatos.insert(NOMBRE_TABLA, null, valoresParaInsertar);
        } else {
            for (Alumno alumnoDB : bdAlumno) {
                java.lang.String id = alumnoDB.getIdentificacion();
                java.lang.String cod = alumnoDB.getCodigo();
                if (id.equals(alumno.getIdentificacion()) && cod.equals(alumno.getCodigo())) {

                } else {
                    // Recuperamos Valores
                    valoresParaInsertar.put("identificacion", alumno.getIdentificacion());
                    valoresParaInsertar.put("codigo", alumno.getCodigo());
                    valoresParaInsertar.put("respuestas", alumno.getRespuestas());

                    // Agregamos a la BD
                    baseDeDatos.insert(NOMBRE_TABLA, null, valoresParaInsertar);
                }


            }


        }
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
        java.lang.String identificacionObtenidoDeBD = java.lang.String.valueOf(cursor.getString(0));
        java.lang.String codigoObtenidoDeBD = java.lang.String.valueOf(cursor.getString(1));
        java.lang.String respuestasObtenidoDeBD = java.lang.String.valueOf(cursor.getString(2));
        Long alumnoIdObtenidoDeBD = (cursor.getLong(3));
        Alumno usuarioObtenidaDeBD = new Alumno(identificacionObtenidoDeBD, codigoObtenidoDeBD, respuestasObtenidoDeBD, alumnoIdObtenidoDeBD);
        alumnos.add(usuarioObtenidaDeBD);

        // Fin del ciclo. Cerramos cursor y regresamos la lista
        cursor.close();

        return alumnos;
    }

    public int guardarCambios(Alumno examenEditado) {
        SQLiteDatabase baseDeDatos = ayudanteBaseDeDatos.getWritableDatabase();
        ContentValues valoresParaActualizar = new ContentValues();

        valoresParaActualizar.put("codigo", examenEditado.getCodigo());
        valoresParaActualizar.put("identificacion", examenEditado.getIdentificacion());
        valoresParaActualizar.put("respuestas", examenEditado.getRespuestas());
        valoresParaActualizar.put("id", examenEditado.getId());

        // where id...
        String campoParaActualizar = "id = ?";
        // ... = idUsuario
        String[] argumentosParaActualizar = {String.valueOf(examenEditado.getId())};
        return baseDeDatos.update(NOMBRE_TABLA, valoresParaActualizar, campoParaActualizar, argumentosParaActualizar);
    }

}