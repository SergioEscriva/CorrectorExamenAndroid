package com.universae.correctorexamenes.SQLiteDB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//Crear Base de Datos Interna
public class AyudanteBaseDeDatos extends SQLiteOpenHelper {

    private static final String
            NOMBRE_BASE_DE_DATOS = "correctorExamenes",
            NOMBRE_TABLA_PLANTILLA = "plantilla",
            NOMBRE_TABLA_ALUMNO = "alumno";

    private static final int VERSION_BASE_DE_DATOS = 1;


    public AyudanteBaseDeDatos(Context context) {
        super(context,
                NOMBRE_BASE_DE_DATOS,
                null,
                VERSION_BASE_DE_DATOS);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(String.format("CREATE TABLE IF NOT EXISTS %s(id integer primary key autoincrement, codigo text unique, respuestas text,coordenadas text)", NOMBRE_TABLA_PLANTILLA));

        db.execSQL(String.format("CREATE TABLE IF NOT EXISTS %s(id integer primary key autoincrement, identificacion text, codigo text, respuestas text)", NOMBRE_TABLA_ALUMNO));


    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
