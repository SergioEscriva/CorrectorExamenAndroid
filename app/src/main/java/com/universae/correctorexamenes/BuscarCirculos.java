package com.universae.correctorexamenes; //src/correctorExamenes/examen2.jpg

//import java.awt.image.BufferedImage;

import com.universae.correctorexamenes.models.Par;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//import utilidades.Utilidades;

public class BuscarCirculos {

    private ArrayList<String> examenAlumno;
    private List<Par> allCircles;


    private static double calculateCentroidX(List<Par> pairs) {
        double sumX = 0;
        for (Par pair : pairs) {
            sumX += pair.getNumeroX();
        }
        return sumX / pairs.size();
    }

    private static double calculateCentroidY(List<Par> pairs) {
        double sumY = 0;
        for (Par pair : pairs) {
            sumY += pair.getNumeroY();
        }
        return sumY / pairs.size();
    }

    private static double calculateMaxRadius(List<Par> pairs, double centroidX, double centroidY) {
        double maxDistance = 0;
        for (Par pair : pairs) {
            double currentX = pair.getNumeroX();
            double currentY = pair.getNumeroY();
            double distance = Math.sqrt(Math.pow(currentX - centroidX, 2) + Math.pow(currentY - centroidY, 2));
            if (distance > maxDistance) {
                maxDistance = distance;
            }
        }
        return maxDistance;
    }

    public Map<String, String> calcularNota(ArrayList<String> plantillaDB, ArrayList<String> examenAlumno, Double penalizacion) {

        Map<String, String> notas = new HashMap<>();
        ArrayList<Integer> resultado = new ArrayList<>();
        int aciertos = 0;
        int falladas = 0;
        int blanco = 0;
        int nulas = 0;

        for (int i = 1; i <= 40; i++) {
            String preguntaPlantilla = plantillaDB.get(i);
            String preguntaExamen = examenAlumno.get(i);
            if (preguntaPlantilla.equals(preguntaExamen)) {
                resultado.add(1);
                aciertos += 1;
            } else if (preguntaExamen.equals("Nula")) {
                nulas += 1;

            } else if (preguntaExamen.equals("Empty")) {
                blanco += 1;

            } else {
                resultado.add(0);
                falladas += 1;

            }
        }
        double notaFinal = resultado.stream().reduce(0, (a, b) -> a + b);
        double penaliza = penalizacion * falladas;

        double notaReal = (notaFinal / 4) + penaliza;

        notas.put("notaFinal", String.valueOf(notaReal));
        notas.put("aciertos", String.valueOf(aciertos));
        notas.put("fallos", String.valueOf(falladas));
        notas.put("blanco", String.valueOf(blanco));
        notas.put("nulas", String.valueOf(nulas));
        return notas;
    }

    public List<Par> rebuscarCirculos(Mat imgOriginal, String circulos) {
        Mat imgAnalizada = new Mat();
        List<Par> lista = new ArrayList<>();
        double radio = 0.0;

        // Cargar la imagen desde el almacenamiento interno
        String rutaInvertido = "/data/data/com.universae.correctorexamenes/files/invertido.jpg";
        // Imagen principal arriba del código

        // Mat imgOriginal = Imgcodecs.imread(imagePath);
        Mat imgReduced = imgOriginal;
        Mat imgEscalaGrises = new Mat();
        Imgproc.cvtColor(imgReduced, imgEscalaGrises, Imgproc.COLOR_BGR2GRAY);
        Imgproc.GaussianBlur(imgEscalaGrises, imgEscalaGrises, new Size(9, 9), 2, 2);


        // Guarda Imagen en Gris
        // String rutaMuestra = "/data/data/com.universae.correctorexamenes/files/muestra.jpg";
        // Imgcodecs.imwrite(rutaMuestra, imgEscalaGrises);

        imgAnalizada = imgEscalaGrises;


        if (circulos.equals("blancos")) {
            radio = 0.8;
            System.out.println("Radio Blancos" + radio);
            Imgproc.GaussianBlur(imgEscalaGrises, imgEscalaGrises, new Size(9, 9), 2, 2);
            Imgproc.GaussianBlur(imgEscalaGrises, imgEscalaGrises, new Size(9, 9), 2, 2);
            Imgproc.GaussianBlur(imgEscalaGrises, imgEscalaGrises, new Size(9, 9), 2, 2);

            Mat imgBW = imgEscalaGrises;
            Imgcodecs.imwrite(rutaInvertido, imgBW);
            imgAnalizada = imgBW;
        }
        // Invertir los colores
        Mat imgInverted = new Mat();
        Core.bitwise_not(imgAnalizada, imgInverted);
        rutaInvertido = "/data/data/com.universae.correctorexamenes/files/invertido.jpg";
        Imgcodecs.imwrite(rutaInvertido, imgInverted);
        imgAnalizada = imgInverted;


        // Busca círculos
        Mat imgCirculosDetectados = new Mat();
        Imgproc.HoughCircles(imgAnalizada, imgCirculosDetectados, Imgproc.HOUGH_GRADIENT, 1.0,
                (double) imgAnalizada.rows() / 60,
                100, 25, 29, 50);

        System.out.println("Circulos detectados: " + imgCirculosDetectados.size());
        List<Point> listaCirculosDetectados = new ArrayList<>();
        for (int i = 0; i < imgCirculosDetectados.cols(); i++) {
            double[] circle = imgCirculosDetectados.get(0, i);
            if (circle == null)
                continue;
            Point center = new Point(Math.round(circle[0]), Math.round(circle[1]));
            int radius = (int) Math.round(circle[2]);

            Mat mask = Mat.zeros(imgAnalizada.size(), CvType.CV_8UC1);
            Imgproc.circle(mask, center, radius, new Scalar(255, 255, 255), - 1);

            Mat circleROI = new Mat();
            imgAnalizada.copyTo(circleROI, mask);

            int whitePixels = Core.countNonZero(circleROI);

            if (whitePixels > (Math.PI * radius * radius * radio)) {
                listaCirculosDetectados.add(center);
                Imgproc.circle(imgReduced, center, radius, new Scalar(0, 255, 0), 3);
            }


            mask.release();
            circleROI.release();
        }


        // Guarda todos los circulos.
        String rutaCirculos = "/data/data/com.universae.correctorexamenes/files/todos.jpg";
        Imgcodecs.imwrite(rutaCirculos, imgReduced);


        for (Point p : listaCirculosDetectados) {
            Par pares = new Par(p.x, p.y);
            lista.add(pares);
        }


        return lista;
    }

    // colorea los circulos según la pregunta acertada o no
    public void correcionCirculos(List<Par> listaDetectados, Mat imgOriginal) {

        //        String rutaCirculos = imagePath;
        //        Mat imgOriginal = Imgcodecs.imread(rutaCirculos);

        // Iterate through detected points and draw circles
        for (Par pair : listaDetectados) {
            double x = pair.getNumeroX();
            double y = pair.getNumeroY();

            // Calculate radius based on your requirements (e.g., constant radius or dynamic radius calculation)
            double radius = calculateRadius(x, y); // Replace with your radius calculation logic

            // Draw the circle
            Point center = new Point(x, y);
            Imgproc.circle(imgOriginal, center, (int) Math.round(radius + 10), new Scalar(0, 0, 255), 3);
        }

        // Save the modified image
        String rutaCirculosCorregidos = "/data/data/com.universae.correctorexamenes/files/corregido.jpg";
        Imgcodecs.imwrite(rutaCirculosCorregidos, imgOriginal);
    }

    // Implement the radius calculation function
    private double calculateRadius(double x, double y) {
        // Replace this placeholder with your actual radius calculation logic
        // For example, you could use a constant radius or calculate a dynamic radius based on the point's position or other factors
        double constantRadius = 20; // Replace with your desired constant radius
        return constantRadius;
    }

}















