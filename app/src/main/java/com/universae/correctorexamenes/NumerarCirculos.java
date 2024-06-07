package com.universae.correctorexamenes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NumerarCirculos {
    private int numero1 = 0;

    public Map<String, Par> busquedaLetras(List<Par> allCircles, String arribaAbajo) {
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

        switch (arribaAbajo) {
            case "Superior":
                listaNumerosLetras = numerarSuperior(circulosList);
                break;
            case "Inferior":

                listaNumerosLetras = numerarInferior(circulosList);
                break;
            // return null;

        }
        return listaNumerosLetras;

    }


    public Map<String, Par> busquedaLetras1(List<Par> allCircles, String arribaAbajo) {
        List<Par> listaFinal = new ArrayList<>();
        int num1 = 0;
        int num2 = 0;
        int ymenor = 1438;
        int ymayor = 1519;
        int incremento = 80;

        String[] opciones = {"1f", "2f","3f", "4f","5f", "6f","7f", "8f","9f", "10f",};

        // Convertir la lista de pares a un array bidimensional
        double[][] puntos = new double[allCircles.size()][2];
        for (int i = 0; i < allCircles.size(); i++) {
            puntos[i][0] = allCircles.get(i).getNumeroX();
            puntos[i][1] = allCircles.get(i).getNumeroY();
            for (String casos : opciones) {
                switch (casos){
                    case "1f":
                        num1 = ymenor;
                        num2 = ymayor;
                        break;
                    case "2f":
                        num1 = ymenor + incremento;
                        num2 = ymayor + incremento;
                        break;
                    case "3f":
                        num1 = ymenor + incremento * 2;
                        num2 = ymayor + incremento * 2;
                        break;
                    case "4f":
                        num1 = ymenor + incremento * 3;
                        num2 = ymayor + incremento * 3;
                        break;
                    case "5f":
                        num1 = ymenor + incremento * 4;
                        num2 = ymayor + incremento * 4;
                        break;
                    case "6f":
                        num1 = ymenor + incremento * 5;
                        num2 = ymayor + incremento * 5;
                        break;
                    case "7f":
                        num1 = ymenor + incremento * 6;
                        num2 = ymayor + incremento * 6;
                        break;
                    case "8f":
                        num1 = ymenor + incremento * 7;
                        num2 = ymayor + incremento * 7;
                        break;
                    case "9f":
                        num1 = ymenor + incremento * 8;
                        num2 = ymayor + incremento * 8;
                        break;
                    case "10f":
                        num1 = ymenor + incremento * 9;
                        num2 = ymayor + incremento * 9;
                        break;


                }
                if (puntos[i][1] < num2 && puntos[i][1] > num1) {
                    Par puntofinal = new Par(puntos[i][0], num1);
                    listaFinal.add(puntofinal);
                }
            }

        }

//                // Ordenar el array de puntos x primero
//                Arrays.sort(puntos, (double[] punto1, double[] punto2) -> {
//                    // Comparar por valor de x y luego por valor de y
//                    if (punto1[0] < punto2[0]) {
//                        return - 1;
//                    } else if (punto1[0] > punto2[0]) {
//                        return 1;
//                    } else {
//                        if (punto1[1] < punto2[1]) {
//                            return - 1;
//                        } else if (punto1[1] > punto2[1]) {
//                            return 1;
//                        } else {
//                            return 0; // Los puntos son iguales
//                        }
//                    }
//                });

        // Ordenar el array de puntos x primero
        //        Arrays.sort(puntos, (double[] punto1, double[] punto2) -> {
        //            // Comparar por valor de x y luego por valor de y
        //            if (punto1[1] < punto2[1]) {
        //                return - 1;
        //            } else if (punto1[1] > punto2[1]) {
        //                return 1;
        //            } else {
        //                if (punto1[0] < punto2[0]) {
        //                    return - 1;
        //                } else if (punto1[0] > punto2[0]) {
        //                    return 1;
        //                } else {
        //                    return 0; // Los puntos son iguales
        //                }
        //            }
        //        });
        //
        //
        //        // Ordenar el array de puntos x primero
        //        Arrays.sort(puntos, (double[] punto1, double[] punto2) -> {
        //            // Comparar por valor de x y luego por valor de y
        //            if (punto1[1] < punto2[1]) {
        //                return - 1;
        //            } else if (punto1[1] > punto2[1]) {
        //                return 1;
        //            } else {
        //                if (punto1[0] < punto2[0]) {
        //                    return - 1;
        //                } else if (punto1[0] > punto2[0]) {
        //                    return 1;
        //                } else {
        //                    return 0; // Los puntos son iguales
        //                }
        //            }
        //        });

                Collections.sort(listaFinal, new Comparator<Par>() {
                    @Override
                    public int compare(Par p1, Par p2) {
                        if (p1.getNumeroY() != p2.getNumeroY()) {
                            return Double.compare(p1.getNumeroY(), p2.getNumeroY());
                        } else {
                            return Double.compare(p1.getNumeroX(), p2.getNumeroX());
                        }
                    }
                });
        System.out.println("lista final " + listaFinal);


        // Ordenar la lista
        //        Collections.sort(allCircles, new Comparator<Par>() {
        //            @Override
        //            public int compare(Par p1, Par p2) {
        //                if (p1.getNumeroY() != p2.getNumeroY()) {
        //                    return Double.compare(p1.getNumeroY(), p2.getNumeroY()); // Primero ordena por y
        //                } else {
        //                    return Double.compare(p1.getNumeroX(), p2.getNumeroX()); // Luego ordena por x si y es igual
        //                }
        //            }
        //        });

        // Ordenar la lista

        //
        //        Collections.sort(allCircles, (Par t1, Par t2) -> { //AQUI INVERTIR PARA  QUE SEA PRIMERO Y
        //            int result = Double.compare(t1.getNumeroY(), t2.getNumeroY());
        //            if (result == 0)
        //                result = Double.compare(t1.getNumeroX(), t2.getNumeroX());
        //            return result;
        //        });


        //        List<Par> circulosList = new ArrayList<Par>();
        //        for (double[] puntossDoubles : puntos) {
        //            double xx = puntossDoubles[0];
        //            double yy = puntossDoubles[1];
        //            Par fila = new Par(xx, yy);
        //            circulosList.add(fila);
        //        }


        //                Par[] puntoss = allCircles.toArray(new Par[0]);
        //
        //
        //                Arrays.sort(puntoss,
        //                        Comparator.comparingDouble(Par::getNumeroX).thenComparingDouble(Par::getNumeroY));
        //
        //
        //                System.out.println("puntoss " + Arrays.asList(puntoss));


        Map<String, Par> listaNumerosLetras = new HashMap<>();

        switch (arribaAbajo) {
            case "Superior":
                listaNumerosLetras = numerarSuperior(allCircles);
                break;
            case "Inferior":

                listaNumerosLetras = numerarInferior(allCircles);
                break;
            // return null;

        }
        return listaNumerosLetras;

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
        System.out.println("Inferior: " + listaNumerosLetras);

        return listaNumerosLetras;


    }


    public Map<String, Par> numerarSuperior(List<Par> circulosList) {
        Map<String, Par> listaNumerosLetras = new HashMap<>();

        // Inicializamos Variables
        String[] letrasAutomaticas = {""};
        int empieza = 0;
        int termina = 0;
        int a1 = 0;
        int a2 = 0;
        int columnaNum = 0;


        String[] columnas = {"Nie", "DNI", "Letra DNI", "Código Examen"};
        System.out.println(numeroPregunta(circulosList.get(0), "DNI") + " circulosList" + circulosList);
        for (String columnaIndividual : columnas) {
            // numera columnas
            switch (columnaIndividual) {
                case "Nie":
                    empieza = 1020;
                    termina = 1250;
                    letrasAutomaticas = new String[]{"A", "B", "C"};
                    a1 = 0;
                    a2 = 25;
                    //columnaNum = 0;
                    break;
                case "DNI":
                    empieza = 1300;
                    termina = 1800;
                    letrasAutomaticas = new String[]{"Aa", "Bb", "Cc", "Dd", "Ee", "Ff", "Gg", "Hh"};
                    a1 = 26;
                    a2 = 105;
                    columnaNum = 26;
                    break;
                case "Letra DNI":
                    empieza = 2000;
                    termina = 2200;
                    letrasAutomaticas = new String[]{"Alp", "Bet", "Cha"};
                    a1 = 106;
                    a2 = 131;
                    columnaNum = 106;
                    break;
                case "Código Examen":
                    empieza = 2400;
                    termina = 2800;
                    letrasAutomaticas = new String[]{"alpha", "bravo", "charlie"};
                    a1 = 132;
                    a2 = 161;
                    columnaNum = 132;
                    break;


            }


            for (int i = a1; i <= a2; i++) {
                Par parNumeradosPar = circulosList.get(i);
                int number = numeroPregunta(circulosList.get(i), "DNI");
                String valorLetra = String.valueOf((number + columnaNum) + columnaIndividual);
                listaNumerosLetras.put(valorLetra, parNumeradosPar);


            }

        }


        System.out.println("Superior: " + listaNumerosLetras);

        return listaNumerosLetras;


    }

    public Integer numeroPregunta(Par fila, String indice) {
        int horquillaInicial = 0;
        int horquillaSize = 0;
        int numeroInicio = 0;

        switch (indice) {
            case "Respuestas":
                horquillaInicial = 2600; // (y + 155); // Altura de "A" normalmente y+55
                horquillaSize = 135; //95 // es la media de separación entre filas 95
                numeroInicio = 1;
                System.out.println("Respuestas");
                break;

            case "DNI":
                horquillaInicial = 1450;
                horquillaSize = 81;
                // numeroInicio = 1;
                System.out.println("DNI");
                break;

        }
        double numero = fila.getNumeroY();
        int horquilla = (int) Math.ceil((numero - horquillaInicial) / horquillaSize) - 1;
        System.out.println("Horquilla " + (horquilla) + " <> " + fila);

        return horquilla;
    }


}
