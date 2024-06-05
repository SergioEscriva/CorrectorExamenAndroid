package com.universae.correctorexamenes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NumerarMarcados {
    public Map<Integer, String> busquedaLetras(List<Par> allCircles, List<Par> whiteCircles, int x, int y) {


        List<Par> listaEliminadosBlancos = new java.util.ArrayList<>();
        // Elimina de respuestas por arriba 2565
        for (int n = 0; n < whiteCircles.size(); n++) {
            if (whiteCircles.get(n).getNumeroY() <= 2500) {
                listaEliminadosBlancos.add(whiteCircles.get(n));
                System.out.println("Eliminado: " + listaEliminadosBlancos);
            }
        }
        List<Par> listaEliminadosTodos = new java.util.ArrayList<>();
        for (int n = 0; n < allCircles.size(); n++) {
            if (allCircles.get(n).getNumeroY() <= 2500) {
                listaEliminadosTodos.add(allCircles.get(n));
            }
        }


        System.out.println("Tamaño: " + listaEliminadosTodos.size() + " TODOS" + listaEliminadosTodos);
        System.out.println("Tamaño: " + listaEliminadosBlancos.size() + " Marcados" + listaEliminadosBlancos);
        NumerarCirculos numerarTodos = new NumerarCirculos();
        Map<String, Par> todosNumeradosMap = numerarTodos.busquedaLetras(allCircles, y); // Todos los circulos

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
            for (Par respuestas : whiteCircles) {

                if (respuestas.toString().contains(value.toString())) {
                    if (circulosMarcados.get(Integer.valueOf(llaveNumero)).equals("Empty")) {
                        circulosMarcados.put(Integer.valueOf(llaveNumero), llaveLetra);

                    } else {
                        circulosMarcados.put(Integer.valueOf(llaveNumero), "Nula");
                        System.out.println("No Marcado " + llaveNumero + " " + llaveLetra);
                    }

                }

            }

        }
        System.out.println("NumMarc: " + circulosMarcados.size() + " Marcados --> " + circulosMarcados);
        return circulosMarcados;

    }

}
