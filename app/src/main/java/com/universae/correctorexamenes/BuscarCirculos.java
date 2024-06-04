package com.universae.correctorexamenes; //src/correctorExamenes/examen2.jpg

//import java.awt.image.BufferedImage;

import static android.content.ContentValues.TAG;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.util.Log;

import com.googlecode.tesseract.android.TessBaseAPI;

import org.json.JSONArray;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//import utilidades.Utilidades;

public class BuscarCirculos {
    private static String imagePath;
    private static Map<Integer, String> examenAlumno;
    private static List<Par> allCircles;

    static {
        //System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static Map<Integer, String> buscarCirculos(int y, int x) throws JSONException, IOException {
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

    //    public static void invertirOscurecer(BufferedImage img, int intY) throws IOException, JSONException {
    //
    //	// File file = new File(imagePath);
    //	// BufferedImage img = ImageIO.read(file);
    //
    ////	// Invierte los valores RGB de cada pixel
    //	for (int y = 0; y < img.getHeight(); y++) {
    //	    for (int x = 0; x < img.getWidth(); x++) {
    //		int pixel = img.getRGB(x, y);
    //		int r = (pixel >> 16) & 0xff;
    //		int g = (pixel >> 8) & 0xff;
    //		int b = pixel & 0xff;
    //		int nuevoPixel = (255 - r) << 16 | (255 - g) << 8 | (255 - b);
    //
    //		img.setRGB(x, y, nuevoPixel);
    //	    }
    //	}
    //
    //	// conviert blanco y negro
    //	BufferedImage imagenNegra = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
    //	List<Double> lista = new ArrayList<>();
    //	for (int i = 0; i < img.getWidth(); i++) {
    //	    for (int j = 0; j < img.getHeight(); j++) {
    //		int pixel = img.getRGB(i, j);
    //		int luminosidad = (pixel >> 16) & 0xFF;
    //		if (luminosidad > 127) {
    //		    imagenNegra.setRGB(i, j, 0xFFFFFFFF);
    //		} else {
    //		    imagenNegra.setRGB(i, j, 0x00000000);
    //		}
    //	    }
    //	}
    //
    //	ImageIO.write(imagenNegra, "jpg", new File("./bnarchivo-negro.jpg")); // no tocar
    //
    //    }


    //	ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    //    image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
    //	byte[] buffer = byteArrayOutputStream.toByteArray();
    //
    public void buscarRespuestas(byte[] byteArray) {

        // Assuming you have a ByteBuffer named `buffer`
        //ByteBuffer buffer = imageBuffer; // Replace with your method to get ByteBuffer

        // Convert ByteBuffer to Bitmap
        //byte[] byteArray = new byte[buffer.remaining()];
        //buffer.get(byteArray);
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        // Perform OCR
        String recognizedText = performOcr(bitmap);

        // Log recognized text
        Log.d(TAG, "Recognized Text: " + recognizedText);
    }


    private String performOcr(Bitmap bitmap) {
        //String TESS_DATA = "src/resources/tessdata_best";
        TessBaseAPI tessBaseAPI = new TessBaseAPI();
        String datapath = "/data/data/com.universae.correctorexamenes/files/resources/";//"/mnt/sdcard/tesseract/";// "/data/data/com.universae.correctorexamenes/files/resources/tessdata_best";//getFilesDir() + TESS_DATA;
        tessBaseAPI.init(datapath, "spa"); // Initialize with English language

        tessBaseAPI.setImage(bitmap);

        String recognizedText = tessBaseAPI.getUTF8Text();
        tessBaseAPI.end();
        System.out.println("Texto reconocido: " + recognizedText);
        return recognizedText;
    }


    public Map<String, String> calcularNota(JSONArray plantillaString, Double penalizacion)
            throws JSONException, IOException {

        Map<String, String> notas = new HashMap<>();
        ArrayList<Integer> resultado = new ArrayList<>();
        int aciertos = 0;
        int falladas = 0;
        int blanco = 0;
        int nulas = 0;

        for (int i = 0; i <= 39; i++) {
            String preguntaPlantilla = plantillaString.getString(i);
            String preguntaExamen = examenAlumno.get(i + 1);
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




    public  List<Par> rebuscarCirculos(Mat src, String circulos) {

	double radio = 0.0;
	String rutaCirculos = "/data/data/com.universae.correctorexamenes/files/blancos.jpg";    //"./blancos.jpg";
	radio = 0.8;
	List<Par> lista = new ArrayList<>();
	if (circulos.equals("all")) {
	    radio = 0.0;
	    rutaCirculos = "/data/data/com.universae.correctorexamenes/files/todos.jpg";    //"./todos.jpg";
	}
	Mat gray = new Mat();
	Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
	// Aplicar desenfoque para reducir el ruido
	Imgproc.GaussianBlur(gray, gray, new Size(9, 9), 2, 2);

	// Detectar círculos utilizando la transformada de Hough
	Mat circles = new Mat();
	Imgproc.HoughCircles(gray, circles, Imgproc.HOUGH_GRADIENT, 1, gray.rows() / 25, 100, 25, 15, 50); // números
	// que hay
	// que jugar
	// para que
	// localize
	// los
	// circulos

	// Crear una lista para almacenar los círculos blancos detectados
	List<Point> whiteCircles = new ArrayList<>();

	// Verificar cada círculo detectado
	for (int i = 0; i < circles.cols(); i++) {
	    double[] circle = circles.get(0, i);
	    if (circle == null)
		continue;
	    Point center = new Point(Math.round(circle[0]), Math.round(circle[1]));
	    int radius = (int) Math.round(circle[2]);

	    // Crear una máscara para el círculo
	    Mat mask = Mat.zeros(gray.size(), CvType.CV_8UC1);
	    Imgproc.circle(mask, center, radius, new Scalar(255, 255, 255), -1);

	    // Extraer la región del círculo de la imagen original
	    Mat circleROI = new Mat();
	    src.copyTo(circleROI, mask);

	    // Convertir la región del círculo a escala de grises y umbralizar
	    Mat circleGray = new Mat();
	    Imgproc.cvtColor(circleROI, circleGray, Imgproc.COLOR_BGR2GRAY);
	    Imgproc.threshold(circleGray, circleGray, 200, 255, Imgproc.THRESH_BINARY);

	    // Calcular la cantidad de píxeles blancos en el círculo
	    int whitePixels = Core.countNonZero(circleGray);

	    // Si la mayoría de los píxeles son blancos, añadimos el círculo a la lista de
	    // círculos blancos
	    if (whitePixels > (Math.PI * radius * radius * radio)) { // 70% de los píxeles son blancos Poner 0.6 para
		// que 0.8
		// pille más
		whiteCircles.add(center);
		// Dibujar el círculo detectado en la imagen original
		Imgproc.circle(src, center, radius, new Scalar(0, 255, 0), 3);
	    }

	    // Ordenar los círculos detectados de izquierda a derecha y de arriba a abajo
	    Collections.sort(whiteCircles, new Comparator<Point>() {
		@Override
		public int compare(Point p1, Point p2) {
		    if (p1.x > p2.x) {
			return -1;
		    } else if (p1.x < p2.x) {
			return 1;
		    } else {
			return Double.compare(p1.y, p2.x);
		    }
		}
	    });
	}

	// Guardar la imagen resultante con los círculos detectados
	Imgcodecs.imwrite(rutaCirculos, src);

	// Mostrar resultados
	for (Point p : whiteCircles) {
	    Par pares = new Par(p.x, p.y);
	    lista.add(pares);
	}

	return lista;

    }

 }


