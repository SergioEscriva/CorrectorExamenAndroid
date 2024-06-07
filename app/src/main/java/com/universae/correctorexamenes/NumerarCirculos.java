package com.universae.correctorexamenes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NumerarCirculos {
    private int numero1 = 0;

    public Map<String, Par> busquedaLetras(List<Par> allCircles) {
        List<Par> listaFinal = new ArrayList<>();

        // Convertir la lista de pares a un array bidimensional
        double[][] puntos = new double[allCircles.size()][2];
        for (int i = 0; i < allCircles.size(); i++) {
            puntos[i][0] = allCircles.get(i).getNumeroX();
            puntos[i][1] = allCircles.get(i).getNumeroY();
        }

        // Ordenar el array de puntos x primero
        Arrays.sort(puntos, (double[] punto1, double[] punto2) -> {
            // Comparar por valor de x y luego por valor de y
            if (punto1[0] < punto2[0]) {
                return - 1;
            } else if (punto1[0] > punto2[0]) {
                return 1;
            } else {
                if (punto1[1] < punto2[1]) {
                    return - 1;
                } else if (punto1[1] > punto2[1]) {
                    return 1;
                } else {
                    return 0; // Los puntos son iguales
                }
            }
        });

        List<Par> circulosList = new ArrayList<Par>();
        for (double[] puntossDoubles : puntos) {
            double xx = puntossDoubles[0];
            double yy = puntossDoubles[1];
            Par fila = new Par(xx, yy);
            circulosList.add(fila);
        }

        Map<String, Par> listaNumerosLetras = new HashMap<>();

        listaNumerosLetras = numerarInferior(circulosList);
        return listaNumerosLetras;
        // return null;

    }

    public Map<String, Par> numerarInferior(List<Par> circulosList) {

        Map<String, Par> listaNumerosLetras = new HashMap<>();

        int a1 = 0;
        int a2 = 0;

        String[] letrasAutomaticas = {"A", "B", "C", "D"};
        for (String letra : letrasAutomaticas) {

            switch (letra) {

                case "A":
                    a1 = 0;
                    a2 = 9;
                    break;
                case "B":
                    a1 = 10;
                    a2 = 19;
                    break;
                case "C":
                    a1 = 20;
                    a2 = 29;
                    break;
                case "D":
                    a1 = 30;
                    a2 = 39;
                    break;
            }

            for (int i = a1; i <= a2; i++) {
                Par parNumeradosPar = circulosList.get(i);
                int number = numeroPregunta(circulosList.get(i), "Respuestas");
                String valorLetra = String.valueOf(number) + letra;
                listaNumerosLetras.put(valorLetra, parNumeradosPar);


            }
            for (int i = a1 + 40; i <= a2 + 40; i++) {
                Par parNumeradosPar = circulosList.get(i);
                int number = numeroPregunta(circulosList.get(i), "Respuestas");
                String valorLetra = String.valueOf(number + 10) + letra;
                listaNumerosLetras.put(valorLetra, parNumeradosPar);


            }

            for (int i = a1 + 80; i <= a2 + 80; i++) {
                Par parNumeradosPar = circulosList.get(i);
                int number = numeroPregunta(circulosList.get(i), "Respuestas");
                String valorLetra = String.valueOf(number + 20) + letra;
                listaNumerosLetras.put(valorLetra, parNumeradosPar);


            }
            for (int i = a1 + 120; i <= a2 + 120; i++) {
                Par parNumeradosPar = circulosList.get(i);
                int number = numeroPregunta(circulosList.get(i), "Respuestas");
                String valorLetra = String.valueOf(number + 30) + letra;
                listaNumerosLetras.put(valorLetra, parNumeradosPar);


            }
        }
        System.out.println(listaNumerosLetras);

        return listaNumerosLetras;


    }

    public Map<String, Par> numerarSuperior(List<Par> circulosList, String columna) {
        Map<String, Par> listaNumerosLetras = new HashMap<>();

        // Inicializamos Variables
        String[] letrasAutomaticas = {""};
        int empieza = 0;
        int termina = 0;
        int a1 = 0;
        int a2 = 0;
        int columnaNum = 0;

        // numera columnas
        switch (columna) {
            case "Nie":
                empieza = 1000;
                termina = 1250;
                letrasAutomaticas = new String[]{"A", "B", "C"};
                a1 = empieza;
                a2 = termina;
                //columnaNum = 0;
                break;
            case "DNI":
                empieza = 1255;
                termina = 1600;
                letrasAutomaticas = new String[]{"A", "B", "C", "D", "E", "F", "G", "H"};
                a1 = empieza;
                a2 = termina;
                columnaNum = 10;
                break;
            case "Letra DNI":
                empieza = 1700;
                termina = 2000;
                letrasAutomaticas = new String[]{"A", "B", "C"};
                a1 = empieza;
                a2 = termina;
                columnaNum = 20;
                break;
            case "Código Examen":
                empieza = 2300;
                termina = 2800;
                letrasAutomaticas = new String[]{"A", "B", "C"};
                a1 = empieza;
                a2 = termina;
                columnaNum = 30;
                break;


        }


        for (String letra : letrasAutomaticas) {

            switch (letra) {

                case "A":
                    a1 = 0;
                    a2 = 9;
                    break;
                case "B":
                    a1 = 10;
                    a2 = 19;
                    break;
                case "C":
                    a1 = 20;
                    a2 = 29;
                    break;
                case "D":
                    a1 = 30;
                    a2 = 39;
                    break;
                case "E":
                    a1 = 40;
                    a2 = 49;
                    break;
                case "F":
                    a1 = 50;
                    a2 = 59;
                    break;
                case "G":
                    a1 = 60;
                    a2 = 69;
                    break;
                case "H":
                    a1 = 70;
                    a2 = 79;
                    break;
            }

            for (int i = a1; i <= a2; i++) {
                Par parNumeradosPar = circulosList.get(i);
                int number = numeroPregunta(circulosList.get(i), "DNI");
                String valorLetra = String.valueOf(number) + letra;
                listaNumerosLetras.put(valorLetra, parNumeradosPar);


            }
            for (int i = a1 + 40; i <= a2 + 40; i++) {
                Par parNumeradosPar = circulosList.get(i);
                int number = numeroPregunta(circulosList.get(i), "DNI");
                String valorLetra = String.valueOf(number + columnaNum) + letra;
                listaNumerosLetras.put(valorLetra, parNumeradosPar);


            }

            for (int i = a1 + 80; i <= a2 + 80; i++) {
                Par parNumeradosPar = circulosList.get(i);
                int number = numeroPregunta(circulosList.get(i), "DNI");
                String valorLetra = String.valueOf(number + columnaNum) + letra;
                listaNumerosLetras.put(valorLetra, parNumeradosPar);


            }
            for (int i = a1 + 120; i <= a2 + 120; i++) {
                Par parNumeradosPar = circulosList.get(i);
                int number = numeroPregunta(circulosList.get(i), "DNI");
                String valorLetra = String.valueOf(number + columnaNum) + letra;
                listaNumerosLetras.put(valorLetra, parNumeradosPar);


            }

            for (int i = a1 + 140; i <= a2 + 140; i++) {
                Par parNumeradosPar = circulosList.get(i);
                int number = numeroPregunta(circulosList.get(i), "DNI");
                String valorLetra = String.valueOf(number + columnaNum) + letra;
                listaNumerosLetras.put(valorLetra, parNumeradosPar);

            }
            for (int i = a1 + 180; i <= a2 + 180; i++) {
                Par parNumeradosPar = circulosList.get(i);
                int number = numeroPregunta(circulosList.get(i), "DNI");
                String valorLetra = String.valueOf(number + columnaNum) + letra;
                listaNumerosLetras.put(valorLetra, parNumeradosPar);

            }
            for (int i = a1 + 220; i <= a2 + 220; i++) {
                Par parNumeradosPar = circulosList.get(i);
                int number = numeroPregunta(circulosList.get(i), "DNI");
                String valorLetra = String.valueOf(number) + letra;
                listaNumerosLetras.put(valorLetra, parNumeradosPar);
            }
            for (int i = a1 + 260; i <= a2 + 260; i++) {
                Par parNumeradosPar = circulosList.get(i);
                int number = numeroPregunta(circulosList.get(i), "DNI");
                String valorLetra = String.valueOf(number + columnaNum) + letra;
                listaNumerosLetras.put(valorLetra, parNumeradosPar);
            }


        }


        System.out.println(listaNumerosLetras);

        return listaNumerosLetras;


    }

    public Integer numeroPregunta(Par fila, String indice) {
        int horquillaInicial = 0;
        int horquillaSize = 0;

        switch (indice) {
            case "Respuestas":
                horquillaInicial = 2600; // (y + 155); // Altura de "A" normalmente y+55
                horquillaSize = 135; //95 // es la media de separación entre filas 95
                break;

            case "DNI":
                horquillaInicial = 1505;
                horquillaSize = 81;
                break;

        }
        double numero = fila.getNumeroY();
        int horquilla = (int) Math.ceil((numero - horquillaInicial) / horquillaSize) + 1;
        System.out.println("Horquilla " + (horquilla - 1) + " <> " + fila);
        return horquilla;
    }


}
