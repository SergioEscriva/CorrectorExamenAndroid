package com.universae.correctorexamenes.models;

public class Alumno {
    private String identificacion;
    private String codigo;
    private String respuestas;
    private long id;

    public Alumno(long id) {
        this.id = id;
    }

    public Alumno(String codigo) {
        this.codigo = codigo;
    }

    public Alumno(String identificacion, String codigo, String respuestas) {
        this.codigo = codigo;
        this.respuestas = respuestas;
        this.identificacion = identificacion;
    }

    // Constructor para cuando instanciamos desde la BD
    public Alumno(String identificacion, String codigo, String respuestas, long id) {
        this.identificacion = identificacion;
        this.codigo = codigo;
        this.respuestas = respuestas;
        this.id = id;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(String respuestas) {
        this.respuestas = respuestas;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    @Override
    public String toString() {
        return "Alumno{" +
                ", identificacion='" + identificacion + '\'' +
                ", codigo='" + codigo + '\'' +
                ", id='" + id + '\'' +
                ", respuestas='" + respuestas +
                "'}";

    }

}
