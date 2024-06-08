package com.universae.correctorexamenes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NumerarMarcados {
    public Map<Integer, String> busquedaLetras(List<Par> allCircles, List<Par> whiteCircles, String arribaAbajo) {

        List<Par> listaSuperiorMarcados = new java.util.ArrayList<>();
        List<Par> listaInferiorMarcados = new java.util.ArrayList<>();
        // Elimina de respuestas por arriba 2565
        for (int n = 0; n < whiteCircles.size(); n++) {
            if (whiteCircles.get(n).getNumeroY() >= 2500) {
                listaInferiorMarcados.add(whiteCircles.get(n));

            } else {
                listaSuperiorMarcados.add(whiteCircles.get(n));

            }
        }
        List<Par> listaSuperiorTodos = new java.util.ArrayList<>();
        List<Par> listaInferiorTodos = new java.util.ArrayList<>();
        for (int n = 0; n < allCircles.size(); n++) {
            if (allCircles.get(n).getNumeroY() >= 2500) {
                listaInferiorTodos.add(allCircles.get(n));

            } else if (allCircles.get(n).getNumeroY() < 2500 && allCircles.get(n).getNumeroY() > 1400) {
                listaSuperiorTodos.add(allCircles.get(n));

            }


        }

        //        System.out.println("Superior Todo Tamaño: " + listaSuperiorTodos.size() + " --> " + listaSuperiorTodos);
        //        System.out.println("Superior Marcados: " + listaSuperiorMarcados.size() + " --> " + listaSuperiorMarcados);
        //        System.out.println("Inferior Todo Tamaño: " + listaInferiorTodos.size() + " --> " + listaInferiorTodos);
        //        System.out.println("Inferior Marcado Tamaño: " + listaInferiorMarcados.size() + " --> " + listaInferiorMarcados);

        Map<Integer, String> listaMarcada = new HashMap<>();
        switch (arribaAbajo) {
            case "arriba":
                listaMarcada = metodoArriba(listaSuperiorTodos, listaSuperiorMarcados);


                break;
            case "abajo":

                listaMarcada = metodoAbajo(listaInferiorTodos, listaInferiorMarcados);


                break;


        }


        return listaMarcada;
    }

    public Map<Integer, String> metodoAbajo(List<Par> listaInferiorTodos, List<Par> listaInferiorMarcados) {
        System.out.println(listaInferiorTodos.size() + " tamaño");
        NumerarCirculos numerarTodos = new NumerarCirculos();
        Map<String, Par> todosNumeradosMap = numerarTodos.busquedaLetrasAbajo(listaInferiorTodos); // Todos los circulos

        int u = 0;
        Map<Integer, String> circulosMarcados = new HashMap<>();
        for (int i = 1; i <= 40; i++) {

            circulosMarcados.put(i, "Empty");
            u = i;
        }

        for (Map.Entry<String, Par> entry : todosNumeradosMap.entrySet()) {
            String llave = entry.getKey();
            String llaveNumero = llave.replaceFirst(".$", "");

            String llaveLetra = "";
            if (Integer.valueOf(llaveNumero) < 10) {
                llaveLetra = String.valueOf(llave.charAt(1));

            } else {
                llaveLetra = String.valueOf(llave.charAt(2));

            }


            Par value = entry.getValue();
            for (Par respuestas : listaInferiorMarcados) {
                //  System.out.println(respuestas + " respuestas");
                //

                //                System.out.println("1  " + value.toString());
                //                System.out.println("respuestas " + respuestas.toString());

                double miny = respuestas.getNumeroY() - 15;
                double maxy = respuestas.getNumeroY() + 15;
                double minx = respuestas.getNumeroX() - 10;
                double maxx = respuestas.getNumeroX() + 10;


                if (value.getNumeroX() <= maxx && value.getNumeroX() >= minx && value.getNumeroY() <= maxy && value.getNumeroY() >= miny) {
                    //                if (respuestas.toString().contains(value.toString())) {


                    if (circulosMarcados.get(Integer.valueOf(llaveNumero)).equals("Empty")) {
                        circulosMarcados.put(Integer.valueOf(llaveNumero), llaveLetra);


                    } else {
                        // System.out.println("2  " + value.toString());
                        circulosMarcados.put(Integer.valueOf(llaveNumero), "Nula");

                    }

                }

            }

        }
        //  System.out.println("NumMarc: " + circulosMarcados.size() + " Marcados --> " + circulosMarcados);
        return circulosMarcados;

    }

    public Map<Integer, String> metodoArriba(List<Par> listaSuperiorTodos, List<Par> listaSuperiorMarcados) {
        //System.out.println(listaSuperiorTodos.size() + " tamaño");
        NumerarCirculos numerarTodos = new NumerarCirculos();
        Map<String, Par> todosNumeradosMap = numerarTodos.busquedaLetrasArriba(listaSuperiorTodos); // Todos los circulos numerados
        List<Par> superiormarcados = numerarTodos.igualarYArriba(listaSuperiorMarcados);
//        int u = 0;
        Map<Integer, String> circulosMarcados = new HashMap<>();
        for (int i = 0; i <= 13; i++) {

            circulosMarcados.put(i, "Empty");
//            u = i;
        }


//                for (Par par :listaSuperiorTodos ){
        //NumerarCirculos numerarCirculos = new NumerarCirculos();
        //numerarCirculos.numeroPregunta(par,"DNI");
        System.out.println("print " + listaSuperiorMarcados);
        //        }


                for (Map.Entry<String, Par> entry : todosNumeradosMap.entrySet()) {
                    String llave = entry.getKey();
                    String llaveNumero = llave.replaceFirst(".$", "");

                    String llaveLetra = "";
                    if (Integer.valueOf(llaveNumero) < 10) {
                        llaveLetra = String.valueOf(llave.charAt(1));

                    } else if (Integer.valueOf(llaveNumero) >= 10 && Integer.valueOf(llaveNumero) < 100 ){
                        llaveLetra = String.valueOf(llave.charAt(2));

                    } else{
                        llaveLetra = String.valueOf(llave.charAt(3));
                    }


                    Par value = entry.getValue();
                    for (Par respuestas : superiormarcados) {
//                        System.out.println(respuestas + " respuestas");
//                        System.out.println(value.toString() + " value");


//                        System.out.println("1  " + value.toString());
        //                System.out.println("respuestas " + respuestas.toString());

                        double miny = respuestas.getNumeroY() - 15;
                        double maxy = respuestas.getNumeroY() + 15;
                        double minx = respuestas.getNumeroX() - 10;
                        double maxx = respuestas.getNumeroX() + 10;



                        if( value.getNumeroX() <= maxx && value.getNumeroX() >= minx && value.getNumeroY() <= maxy && value.getNumeroY() >= miny ) {
                            if (respuestas.toString().contains(value.toString())) {
                                System.out.println("Entraaaaaaa");
//                                System.out.println(respuestas.toString() + " respuestaTS " + value.toString() + " vTS");

                                            if (respuestas.getNumeroX() < 1340 && circulosMarcados.get(0).equals("Empty")) {
                                                circulosMarcados.put(0, llaveLetra);
                                                System.out.println("intentando " + circulosMarcados.get(0));
                                            } else if (respuestas.getNumeroX() > 1340 && respuestas.getNumeroX() < 1420 && circulosMarcados.get(1).equals("Empty")) {
                                                circulosMarcados.put(1, llaveLetra);
                                            } else  if (respuestas.getNumeroX() > 1420 && respuestas.getNumeroX() < 1500 && circulosMarcados.get(2).equals("Empty")) {
                                                circulosMarcados.put(2, llaveLetra);
                                            } else if (respuestas.getNumeroX() > 1500 && respuestas.getNumeroX() < 1580 && circulosMarcados.get(3).equals("Empty")) {
                                                circulosMarcados.put(3, llaveLetra);
                                            } else if (respuestas.getNumeroX() > 1580 && respuestas.getNumeroX() < 1660 && circulosMarcados.get(4).equals("Empty")) {
                                                circulosMarcados.put(4, llaveLetra);
                                            } else  if (respuestas.getNumeroX() > 1660 && respuestas.getNumeroX() < 1740 && circulosMarcados.get(5).equals("Empty")) {
                                                circulosMarcados.put(5, llaveLetra);
                                            } else if (respuestas.getNumeroX() > 1740 && respuestas.getNumeroX() < 1818 && circulosMarcados.get(6).equals("Empty")) {
                                                circulosMarcados.put(6, llaveLetra);
                                            } else   if (respuestas.getNumeroX() > 1818 && respuestas.getNumeroX() < 1900 && circulosMarcados.get(7).equals("Empty")) {
                                                circulosMarcados.put(7, llaveLetra);
                                            } else   if (respuestas.getNumeroX() > 1900 && respuestas.getNumeroX() < 2045 && circulosMarcados.get(8).equals("Empty")) {
                                                circulosMarcados.put(8, llaveLetra);
                                            } else  if (respuestas.getNumeroX() > 2045 && respuestas.getNumeroX() < 2135 && circulosMarcados.get(9).equals("Empty")) {
                                                circulosMarcados.put(9, llaveLetra);
                                            } else if (respuestas.getNumeroX() > 2135 && respuestas.getNumeroX() < 2218 && circulosMarcados.get(10).equals("Empty")) {
                                                circulosMarcados.put(10, llaveLetra);
                                            } else if (respuestas.getNumeroX() > 2218 && respuestas.getNumeroX() < 2258 && circulosMarcados.get(11).equals("Empty")) {
                                                circulosMarcados.put(11, llaveLetra);
                                            } else if (respuestas.getNumeroX() > 2258 && respuestas.getNumeroX() < 2538 && circulosMarcados.get(12).equals("Empty")) {
                                                circulosMarcados.put(12, llaveLetra);
                                            } else if (respuestas.getNumeroX() > 2538 && respuestas.getNumeroX() < 2620 && circulosMarcados.get(13).equals("Empty")) {
                                                circulosMarcados.put(13, llaveLetra);
                                            }







                            }
                        }

                    }


                }
                System.out.println("NumMarc: " + circulosMarcados.size() + " Marcados --> " + circulosMarcados);
        return circulosMarcados;

    }


}



