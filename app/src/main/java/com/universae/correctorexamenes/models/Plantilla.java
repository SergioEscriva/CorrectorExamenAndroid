package com.universae.correctorexamenes.models;

public class Plantilla {

    private String codigo;
    private String respuestas;
    private String coordenadas;
    private long id;

    public Plantilla(long id) {
        this.id = id;
    }

    public Plantilla(String codigo) {
        this.codigo = codigo;
    }

    public Plantilla(String codigo, String respuestas, String coordenadas) {
        this.codigo = codigo;
        this.respuestas = respuestas;
        this.coordenadas = coordenadas;
    }

    // Constructor para cuando instanciamos desde la BD
    public Plantilla(String codigo, String respuestas, String coordenadas, long id) {
        this.codigo = codigo;
        this.respuestas = respuestas;
        this.coordenadas = coordenadas;
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

    public String getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(String coordenadas) {
        this.coordenadas = coordenadas;
    }

    @Override
    public String toString() {
        return "Plantilla{" +
                ", codigo='" + codigo + '\'' +
                ", id='" + id + '\'' +
                ", respuestas='" + respuestas +
                ", coordenadas='" + coordenadas +
                "'}";

    }

}
