package com.universae.correctorexamenes; //src/correctorExamenes/examen2.jpg

//import java.awt.image.BufferedImage;

import org.json.JSONException;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;


//import utilidades.Utilidades;

public class BuscarCirculos {
    private String imagePath;
    private Map<Integer, String> examenAlumno;
    private List<Par> allCircles;


    public Map<Integer, String> buscarCirculos(int y, int x) throws JSONException, IOException {
        // Cargar la imagen
        String imagePathInv = "./bnarchivo-negro.jpg";//
        Mat srcBlack = Imgcodecs.imread(imagePathInv);
        Mat srcWhite = Imgcodecs.imread(imagePathInv);

        if (srcBlack.empty()) {
            System.out.println("No se pudo cargar la imagen");
            return null;
        }

        //allCircles = rebuscarCirculos(srcBlack, "all");

        //List<Par> white1Circles = rebuscarCirculos(srcWhite, "white");

        //NumerarMarcados numerarMarcados = new NumerarMarcados();
        //examenAlumno = numerarMarcados.busquedaLetras(allCircles, white1Circles, x, y);

        return examenAlumno;

    }

    //        public static void invertirOscurecer(BufferedImage img, int intY) throws IOException, JSONException {

    // File file = new File(imagePath);
    // BufferedImage img = ImageIO.read(file);

    //	// Invierte los valores RGB de cada pixel
    //    	for (int y = 0; y < img.getHeight(); y++) {
    //    	    for (int x = 0; x < img.getWidth(); x++) {
    //    		int pixel = img.getRGB(x, y);
    //    		int r = (pixel >> 16) & 0xff;
    //    		int g = (pixel >> 8) & 0xff;
    //    		int b = pixel & 0xff;
    //    		int nuevoPixel = (255 - r) << 16 | (255 - g) << 8 | (255 - b);
    //
    //    		img.setRGB(x, y, nuevoPixel);
    //    	    }
    //    	}
    //
    //    	// conviert blanco y negro
    //    	BufferedImage imagenNegra = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
    //    	List<Double> lista = new ArrayList<>();
    //    	for (int i = 0; i < img.getWidth(); i++) {
    //    	    for (int j = 0; j < img.getHeight(); j++) {
    //    		int pixel = img.getRGB(i, j);
    //    		int luminosidad = (pixel >> 16) & 0xFF;
    //    		if (luminosidad > 127) {
    //    		    imagenNegra.setRGB(i, j, 0xFFFFFFFF);
    //    		} else {
    //    		    imagenNegra.setRGB(i, j, 0x00000000);
    //    		}
    //    	    }
    //    	}
    //
    //    	ImageIO.write(imagenNegra, "jpg", new File("./bnarchivo-negro.jpg")); // no tocar
    //
    //        }
    //
    //
    //    	ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    //        image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
    //    	byte[] buffer = byteArrayOutputStream.toByteArray();

    //    public void buscarRespuestas(byte[] byteArray) {
    //
    //        // Assuming you have a ByteBuffer named `buffer`
    //        //ByteBuffer buffer = imageBuffer; // Replace with your method to get ByteBuffer
    //
    //        // Convert ByteBuffer to Bitmap
    //        //byte[] byteArray = new byte[buffer.remaining()];
    //        //buffer.get(byteArray);
    //        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    //
    //        // Perform OCR
    //        String recognizedText = performOcr(bitmap);
    //
    //        // Log recognized text
    //        Log.d(TAG, "Recognized Text: " + recognizedText);
    //    }


    //    private String performOcr(Bitmap bitmap) {
    //        //String TESS_DATA = "src/resources/tessdata_best";
    //        TessBaseAPI tessBaseAPI = new TessBaseAPI();
    //        String datapath = "/data/data/com.universae.correctorexamenes/files/resources/";//"/mnt/sdcard/tesseract/";// "/data/data/com.universae.correctorexamenes/files/resources/tessdata_best";//getFilesDir() + TESS_DATA;
    //        tessBaseAPI.init(datapath, "spa"); // Initialize with English language
    //
    //        tessBaseAPI.setImage(bitmap);
    //
    //        String recognizedText = tessBaseAPI.getUTF8Text();
    //        tessBaseAPI.end();
    //        System.out.println("Texto reconocido: " + recognizedText);
    //        return recognizedText;
    //    }


    //    public Map<String, String> calcularNota(JSONArray plantillaString, Double penalizacion)
    //            throws JSONException, IOException {
    //
    //        Map<String, String> notas = new HashMap<>();
    //        ArrayList<Integer> resultado = new ArrayList<>();
    //        int aciertos = 0;
    //        int falladas = 0;
    //        int blanco = 0;
    //        int nulas = 0;
    //
    //        for (int i = 0; i <= 39; i++) {
    //            String preguntaPlantilla = plantillaString.getString(i);
    //            String preguntaExamen = examenAlumno.get(i + 1);
    //            if (preguntaPlantilla.equals(preguntaExamen)) {
    //                resultado.add(1);
    //                aciertos += 1;
    //            } else if (preguntaExamen.equals("Nula")) {
    //                nulas += 1;
    //
    //            } else if (preguntaExamen.equals("Empty")) {
    //                blanco += 1;
    //
    //            } else {
    //                resultado.add(0);
    //                falladas += 1;
    //
    //            }
    //        }
    //        double notaFinal = resultado.stream().reduce(0, (a, b) -> a + b);
    //        double penaliza = penalizacion * falladas;
    //
    //        double notaReal = (notaFinal / 4) + penaliza;
    //
    //        notas.put("notaFinal", String.valueOf(notaReal));
    //        notas.put("aciertos", String.valueOf(aciertos));
    //        notas.put("fallos", String.valueOf(falladas));
    //        notas.put("blanco", String.valueOf(blanco));
    //        notas.put("nulas", String.valueOf(nulas));
    //        return notas;
    //    }


    //	private void copyTessDataFiles(String path) {
    //
    //		// Configurar la ruta del idioma (opcional)
    //			String datapath = "src/resources/tessdata_best";
    //			tesseract.setDatapath(new File(datapath).getPath());
    //
    //
    //
    //		try {
    //			String fileList[] = getAssets().list(path);
    //			for (String fileName : fileList) {
    //				String pathToDataFile = path + "/" + fileName;
    //				if (path.equals(TESS_DATA)) {
    //					String filePathOnDevice = getFilesDir() + "/" + fileName;
    //					if (!(new File(filePathOnDevice)).exists()) {
    //						InputStream in = getAssets().open(pathToDataFile);
    //						OutputStream out = new FileOutputStream(filePathOnDevice);
    //						byte[] buffer = new byte[1024];
    //						int read;
    //						while ((read = in.read(buffer)) != -1) {
    //							out.write(buffer, 0, read);
    //						}
    //						in.close();
    //						out.flush();
    //						out.close();
    //					}
    //				} else {
    //					copyTessDataFiles(pathToDataFile);
    //				}
    //			}
    //		} catch (IOException e) {
    //			Log.e(TAG, "Error copying tessdata files: " + e.getMessage());
    //		}
    //	}


    public List<Par> rebuscarCirculos(Mat imgOriginal1, String circulos) {
        Mat imgAnalizada = new Mat();
        List<Par> lista = new ArrayList<>();
        double radio = 0.8;
        // Cargar la imagen desde el almacenamiento interno

        String imagePath = "/data/data/com.universae.correctorexamenes/files/muestra1.jpg";
        Mat imgOriginal = Imgcodecs.imread(imagePath);
        //
        // Reducción de la resolución
        //        Mat imgReduced = new Mat();
        //        Size size = new Size(imgOriginal.width() / 2, imgOriginal.height() / 2);
        //        Imgproc.resize(imgOriginal, imgReduced, size);

        Mat imgReduced = imgOriginal;
        Mat imgEscalaGrises = new Mat();
        Imgproc.cvtColor(imgReduced, imgEscalaGrises, Imgproc.COLOR_BGR2GRAY);
        Imgproc.GaussianBlur(imgEscalaGrises, imgEscalaGrises, new Size(9, 9), 2, 2);

        // Guarda Imagen en Gris
        // String rutaMuestra = "/data/data/com.universae.correctorexamenes/files/muestra.jpg";
        // Imgcodecs.imwrite(rutaMuestra, imgEscalaGrises);

        // Invertir los colores
        Mat imgInverted = new Mat();
        Core.bitwise_not(imgEscalaGrises, imgInverted);
        imgAnalizada = imgInverted;


        if (circulos.equals("blancos")) {
            radio = 0.0;
            System.out.println("Radio " + radio);

            //        // blanco y negro
            Mat imgBW = new Mat();
            Imgproc.threshold(imgInverted, imgBW, 150, 255, Imgproc.THRESH_BINARY);

            // Guarda imagen Blanco y negro
            Imgproc.GaussianBlur(imgBW, imgBW, new Size(9, 9), 2, 2);
            String rutaInvertido = "/data/data/com.universae.correctorexamenes/files/invertido.jpg";
            Imgcodecs.imwrite(rutaInvertido, imgBW);
            imgAnalizada = imgBW;
        }


        // Busca círculos
        Mat imgCirculosDetectados = new Mat();
        Imgproc.HoughCircles(imgAnalizada, imgCirculosDetectados, Imgproc.HOUGH_GRADIENT, 1.0,
                (double) imgAnalizada.rows() / 60,
                100, 25, 25, 50);

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

        Collections.sort(listaCirculosDetectados, new Comparator<Point>() {
            @Override
            public int compare(Point p1, Point p2) {
                if (p1.x > p2.x) {
                    return - 1;
                } else if (p1.x < p2.x) {
                    return 1;
                } else {
                    return Double.compare(p1.y, p2.x);
                }
            }
        });


        // Guarda todos los circulos.
        String rutaCirculos = "/data/data/com.universae.correctorexamenes/files/todos.jpg";
        Imgcodecs.imwrite(rutaCirculos, imgReduced);

        for (Point p : listaCirculosDetectados) {
            Par pares = new Par(p.x, p.y);
            lista.add(pares);
        }

        System.out.println("Circulos detectados: " + listaCirculosDetectados.size());
        System.out.println("Circulos detectados: " + lista.size());
        return lista;
    }

    class Par {
        double x, y;

        Par(double x, double y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "(" + x + ", " + y + ")";

        }

    }


}






