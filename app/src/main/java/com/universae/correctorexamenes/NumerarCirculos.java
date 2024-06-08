package com.universae.correctorexamenes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NumerarCirculos {
    String[] abc = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private int numero1 = 0;

    private static String getValueFromPair(double[] pair) {
        // Assuming the first value is the letter and the second is the number
        String value = (char) pair[0] + String.valueOf((int) pair[1]);
        return value;
    }

    private static String getValueFromPair(double x, double y) {
        // Assuming the first value is the letter and the second is the number
        String value = (char) x + String.valueOf((int) y);
        return value;
    }

    public Map<String, Par> busquedaLetrasAbajo(List<Par> allCircles) {
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

    }

    public Map<String, Par> busquedaLetrasArriba(List<Par> allCircles) {
        List<Par> listaFinal = new ArrayList<>();
        int num1 = 0;
        int num2 = 0;
        int ymenor = 1438;
        int ymayor = 1519;
        int incremento = 80;

        String[] opciones = {"1f", "2f", "3f", "4f", "5f", "6f", "7f", "8f", "9f", "10f",};

        // Convertir la lista de pares a un array bidimensional
        double[][] puntos = new double[allCircles.size()][2];
        for (int i = 0; i < allCircles.size(); i++) {
            puntos[i][0] = allCircles.get(i).getNumeroX();
            puntos[i][1] = allCircles.get(i).getNumeroY();

            //Seteamos las Y iguales para que podamos compararlos.
            for (String casos : opciones) {
                switch (casos) {
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

        //Comparamos los pares y obtenemos la nueva lista
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


        Map<String, Par> listaNumerosLetras = new HashMap<>();

        listaNumerosLetras = numerarSuperior(listaFinal);


        return listaNumerosLetras;

    }

    public  List<Par> igualarYArriba(List<Par> allCircles) {
        List<Par> listaFinal = new ArrayList<>();
        int num1 = 0;
        int num2 = 0;
        int ymenor = 1438;
        int ymayor = 1519;
        int incremento = 80;

        String[] opciones = {"1f", "2f", "3f", "4f", "5f", "6f", "7f", "8f", "9f", "10f",};

        // Convertir la lista de pares a un array bidimensional
        double[][] puntos = new double[allCircles.size()][2];
        for (int i = 0; i < allCircles.size(); i++) {
            puntos[i][0] = allCircles.get(i).getNumeroX();
            puntos[i][1] = allCircles.get(i).getNumeroY();

            //Seteamos las Y iguales para que podamos compararlos.
            for (String casos : opciones) {
                switch (casos) {
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

        //Comparamos los pares y obtenemos la nueva lista
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





        return listaFinal;

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
                int number = numeroPregunta(circulosList.get(i));
                String valorLetra = String.valueOf(number) + letra;
                listaNumerosLetras.put(valorLetra, parNumeradosPar);


            }
            for (int i = a1 + 40; i <= a2 + 40; i++) {
                Par parNumeradosPar = circulosList.get(i);
                int number = numeroPregunta(circulosList.get(i));
                String valorLetra = String.valueOf(number + 10) + letra;
                listaNumerosLetras.put(valorLetra, parNumeradosPar);


            }

            for (int i = a1 + 80; i <= a2 + 80; i++) {
                Par parNumeradosPar = circulosList.get(i);
                int number = numeroPregunta(circulosList.get(i));
                String valorLetra = String.valueOf(number + 20) + letra;
                listaNumerosLetras.put(valorLetra, parNumeradosPar);


            }
            for (int i = a1 + 120; i <= a2 + 120; i++) {
                Par parNumeradosPar = circulosList.get(i);
                int number = numeroPregunta(circulosList.get(i));
                String valorLetra = String.valueOf(number + 30) + letra;
                listaNumerosLetras.put(valorLetra, parNumeradosPar);


            }
        }
        //System.out.println("Inferior: " + listaNumerosLetras);

        return listaNumerosLetras;


    }

    public Map<String, Par> numerarSuperior(List<Par> circulosList) {

/*
        /////////////////////////////////////////////////////////////////
        //////////  gpt
        List<Par> circulos = circulosList;
        // Definir letras y números
        String[] letras = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        String[] numeros = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        System.out.println(circulosList.size() + " circulosList " + circulosList);
        // Ordenar la lista primero por Y (ascendente) y luego por X (ascendente)
        circulos.sort(Comparator.comparingDouble(Par::getNumeroY).thenComparingDouble(Par::getNumeroX));

        // Crear un mapa para almacenar la asociación de coordenadas con letras/números
        Map<Par, String> coordenadasAsignadas = new HashMap<>();

        // Variable para iterar sobre letras y números
        int letraIndex = 0;
        int numeroIndex = 0;
        int totalLetras = letras.length;
        int totalNumeros = numeros.length;

        // Asignar letras y números a los círculos detectados
        for (int i = 0; i < circulos.size(); i++) {
            Par par = circulos.get(i);

            if (letraIndex < totalLetras) {
                coordenadasAsignadas.put(par, letras[letraIndex++]);
            } else if (numeroIndex < totalNumeros) {
                coordenadasAsignadas.put(par, numeros[numeroIndex++]);
            }

            // Si ambos índices alcanzan sus límites, resetear
            if (letraIndex == totalLetras && numeroIndex == totalNumeros) {
                letraIndex = 0;
                numeroIndex = 0;
            }
        }

        // Imprimir las coordenadas con las letras/números asignados
        for (Map.Entry<Par, String> entry : coordenadasAsignadas.entrySet()) {
            System.out.println(coordenadasAsignadas.size() + " Coordenada: " + entry.getKey() + " Asignado: " + entry.getValue());
        }
        //////////// GPT
        ////////////////////////////////////////////////////////
*/

        Map<String, Par> listaNumerosLetras = new HashMap<>();
        Map<String, Par> dniFinal = new HashMap<>();

        //Creamos un Array con todos los valores posibles
        String[] dni = {"A","B","C","0","0","0","0","0","0","0","0","A","B","C","0","0","0",
                        "D","E","F","1","1","1","1","1","1","1","1","D","E","F","1","1","1",
                        "G","H","I","2","2","2","2","2","2","2","2","G","H","I","2","2","2",
                        "J","K","L","3","3","3","3","3","3","3","3","J","K","L","3","3","3",
                        "M","N","O","4","4","4","4","4","4","4","4","M","N","O","4","4","4",
                        "P","Q","R","5","5","5","5","5","5","5","5","P","Q","R","5","5","5",
                        "S","T","U","6","6","6","6","6","6","6","6","S","T","U","6","6","6",
                        "V","W","X","7","7","7","7","7","7","7","7","V","W","X","7","7","7",
                        "Y","Z",    "8","8","8","8","8","8","8","8","Y","Z",    "8","8","8",
                                    "9","9","9","9","9","9","9","9",            "9","9","9",};

        //Asociamos un número a cada Par
        for (int i = 0; i < circulosList.size(); i++) {
            listaNumerosLetras.put(String.valueOf(i), circulosList.get(i));
        }
        //Asignamos su valor a cada Par
        for (int i = 0; i < circulosList.size(); i++) {
                Par parNumeradosPar = circulosList.get(i);
                String valorLetra = String.valueOf(i) + dni[i];
                dniFinal.put(valorLetra, parNumeradosPar);

            }

        System.out.println("ESTE ES El Bueno " + dniFinal);


        return dniFinal;


    }

    public Integer numeroPregunta(Par fila) {
        int horquillaInicial = 2600; // (y + 155); // Altura de "A" normalmente y+55
        int horquillaSize = 135; //95 // es la media de separación entre filas 95

                System.out.println("Respuestas");



        double numero = fila.getNumeroY();
        int horquilla = (int) Math.ceil((numero - horquillaInicial) / horquillaSize) + 1;
        //System.out.println("Horquilla " + (horquilla) + " <> " + fila);

        return horquilla;
    }


}
