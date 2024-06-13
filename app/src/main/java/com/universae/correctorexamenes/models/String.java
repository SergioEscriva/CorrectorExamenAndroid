package com.universae.correctorexamenes.models;

public class String {
    private java.lang.String identificacion;
    private java.lang.String codigo;
    private java.lang.String respuestas;
    private long id;

    public String(long id) {
        this.id = id;
    }

    public String(java.lang.String codigo) {
        this.codigo = codigo;
    }

    public String(java.lang.String identificacion, java.lang.String codigo, java.lang.String respuestas) {
        this.codigo = codigo;
        this.respuestas = respuestas;
        this.identificacion = identificacion;
    }

    // Constructor para cuando instanciamos desde la BD
    public String(java.lang.String identificacion, java.lang.String codigo, java.lang.String respuestas, long id) {
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

    public java.lang.String getCodigo() {
        return codigo;
    }

    public void setCodigo(java.lang.String codigo) {
        this.codigo = codigo;
    }

    public java.lang.String getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(java.lang.String respuestas) {
        this.respuestas = respuestas;
    }

    public java.lang.String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(java.lang.String identificacion) {
        this.identificacion = identificacion;
    }

    @Override
    public java.lang.String toString() {
        return "Alumno{" +
                ", identificacion='" + identificacion + '\'' +
                ", codigo='" + codigo + '\'' +
                ", id='" + id + '\'' +
                ", respuestas='" + respuestas +
                "'}";

    }

}
