package com.universae.correctorexamenes;

import android.Manifest;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LifecycleOwner;

import com.google.android.material.textfield.TextInputEditText;
import com.google.common.util.concurrent.ListenableFuture;
import com.universae.correctorexamenes.models.Par;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {
    PreviewView previewView;
    int codigo_permiso = 200;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private ImageCapture imageCapture;
    private Button image_capture_button;


    private Button btnCorregir;
    private Button btnRepetir;
    private ImageView imagePreview;
    private ImageView imageViewMuestra;
    private TextInputEditText inputCodigo;
    private TextView ayuda;
    private List<Par> listaBlancosExamen;
    private List<Par> listaTodosExamen;
    private List<Par> listaBlancosPlantilla;
    private List<Par> listaTodosPlantilla;


    private ArrayList<String> plantillaDB = new ArrayList<>();
    private BuscarCirculos buscarCirculos = new BuscarCirculos();
    private NumerarCirculos numerarCirculos = new NumerarCirculos();
    private ArreglosBD arreglosBD = new ArreglosBD();
    private int metodo = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        OpenCVLoader.initDebug();

        verificarPermisos();
        previewView = findViewById(R.id.preview_view);
        btnCorregir = findViewById(R.id.BttnCorregir);
        btnRepetir = findViewById(R.id.BttnRepetir);
        imagePreview = findViewById(R.id.imageView2);
        imageViewMuestra = findViewById(R.id.imgViewMuestra);
        inputCodigo = findViewById(R.id.inputCodigo);
        ayuda = findViewById(R.id.txtAyuda);


        image_capture_button = findViewById(R.id.image_capture_button);

        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                startCameraX(cameraProvider);

            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, getExecutor());


        btnCorregir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Corrige el examen
                cuentaMarcadosExamen();
            }


        });
        btnRepetir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Repetir la foto
                image_capture_button.setText("Escanear Examen");
                takePhoto("examen");
                imageViewMuestra.setVisibility(View.INVISIBLE);
                previewView.setVisibility(View.VISIBLE);
                imagePreview.setVisibility(View.VISIBLE);

            }


        });


    }


    private void cuentaMarcadosExamen() {

        NumerarMarcados numerarMarcados = new NumerarMarcados();
        System.out.println(listaTodosExamen + " " + listaBlancosExamen);
        ArrayList<String> listaAbajoMarcados = numerarMarcados.busquedaLetras(listaTodosExamen, listaBlancosExamen, "abajo");
        ArrayList<String> listaArribaMarcados = numerarMarcados.busquedaLetras(listaTodosExamen, listaBlancosExamen, "arriba");
        Map<String, String> arrayDatosArriba = numerarMarcados.arrayDatos(listaArribaMarcados);

        // muestra la imagen corregida con los circulos por colores.
        String imagePath = "/data/data/com.universae.correctorexamenes/files/corregido.jpg";
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        imageViewMuestra.setImageBitmap(bitmap);

        System.out.println("JJJJJJJJ " + listaAbajoMarcados);
        System.out.println("pppppppp " + plantillaDB.get(1));
        // Guarda la Examen en DB
        arreglosBD.guardarDB(getBaseContext(), listaAbajoMarcados, arrayDatosArriba, "examen");
        // Calcula nota examen todo
        Map<String, String> nota = buscarCirculos.calcularNota(plantillaDB, listaAbajoMarcados, 0.0);
        System.out.println(" nota " + nota);


    }

    private void cuentaMarcadosPlantilla() {
        NumerarMarcados numerarMarcados = new NumerarMarcados();
        System.out.println(listaTodosPlantilla + " 2 " + listaBlancosPlantilla);
        ArrayList<String> listaAbajoMarcados = numerarMarcados.busquedaLetras(listaTodosPlantilla, listaBlancosPlantilla, "abajo");
        ArrayList<String> listaArribaMarcados = numerarMarcados.busquedaLetras(listaTodosPlantilla, listaBlancosPlantilla, "arriba");
        Map<String, String> arrayDatosArriba = numerarMarcados.arrayDatos(listaArribaMarcados);

        // muestra la imagen corregida con los circulos por colores.
        String imagePath = "/data/data/com.universae.correctorexamenes/files/corregidoPlantilla.jpg";
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        imageViewMuestra.setImageBitmap(bitmap);

        // Guardar examen Alumno BD
String codigo = arreglosBD.guardarDB(getBaseContext(), listaAbajoMarcados, arrayDatosArriba, "plantilla");
        //arreglosBD.guardarDB(getBaseContext(), listaAbajoMarcados, arrayDatosArriba, "plantilla");
        plantillaDB = arreglosBD.existeEnDB(getBaseContext(), codigo);
        inputCodigo.setText(codigo);

    }

    private Executor getExecutor() {
        return ContextCompat.getMainExecutor(this);
    }

    private void startCameraX(ProcessCameraProvider cameraProvider) {
        cameraProvider.unbindAll();//limpiamos la camara
        ///creamos el selector de camara y seleccionamos la camara trasera
        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();
        //implementamos un preview
        Preview preview = new Preview.Builder().build();
        //en el preview agregamos un provedor de superficie que es donde se vincula con el layout
        preview.setSurfaceProvider(previewView.createSurfaceProvider());


        // capturamos la imagen,creamos esta variable como de instancia principal para que este disponible para otros casos.
        imageCapture = new ImageCapture.Builder().setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .build();
        //bind to lifecycle: viculamos estos casos con la camera X
        cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector, preview, imageCapture);


        image_capture_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                botones();

//                switch (metodo) {
//                    case 0:
//                        hideKeyboard(v);
//                        previewView.setVisibility(View.VISIBLE);
//                        imagePreview.setVisibility(View.VISIBLE);
//                        // Primera vez que se ejecuta pide Num Plantilla.
//                        String codigo = inputCodigo.getText().toString();
//                        ayuda.setVisibility(View.INVISIBLE);
//                        // si existe la carga en memoria.
//                        plantillaDB = arreglosBD.existeEnDB(getBaseContext(), codigo);
//
//                        if (plantillaDB.size() == 0) {
//                            System.out.println("No existe en DB");
//                            image_capture_button.setText("Escanear Plantilla...");
//                            takePhoto("plantilla");
//
//
//                        }
//                        image_capture_button.setText("Escanear Examen...");
//
//
//                        metodo = 1;
//
//                        break;
//                    case 1:
//
//                        // Siguientes veces el número plantilla ya está en memoria.
//                        image_capture_button.setText("Escanear Examen...");
//                        takePhoto("examen");
//                        imageViewMuestra.setVisibility(View.VISIBLE);
//                        image_capture_button.setText("Procesando Examen...");
//                        previewView.setVisibility(View.INVISIBLE);
//                        imagePreview.setVisibility(View.INVISIBLE);
//
//                        break;
//
//                }


            }


        });
    }
    public void botones(){
        switch (metodo) {
            case 0:
//                hideKeyboard(v);
                previewView.setVisibility(View.VISIBLE);
                imagePreview.setVisibility(View.VISIBLE);
                // Primera vez que se ejecuta pide Num Plantilla.
                String codigo = inputCodigo.getText().toString();
                ayuda.setVisibility(View.INVISIBLE);
                // si existe la carga en memoria.
                plantillaDB = arreglosBD.existeEnDB(getBaseContext(), codigo);

                if (plantillaDB.size() == 0) {
                    System.out.println("No existe en DB");
                    image_capture_button.setText("Escanear Plantilla...");
                    takePhoto("plantilla");


                }
                image_capture_button.setText("Escanear Examen...");


                metodo = 1;

                break;
            case 1:

                // Siguientes veces el número plantilla ya está en memoria.
                image_capture_button.setText("Escanear Examen");
                takePhoto("examen");
                imageViewMuestra.setVisibility(View.VISIBLE);
                image_capture_button.setText("Procesando Examen...");
                previewView.setVisibility(View.INVISIBLE);
                imagePreview.setVisibility(View.INVISIBLE);

                break;

        }

    }


    private void verificarPermisos() {
        int permisoCamara = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int permisoalmacenamientoEscribir = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permisoalmacenamientoLeer = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permisoCamara == getPackageManager().PERMISSION_GRANTED
                && permisoalmacenamientoEscribir == getPackageManager().PERMISSION_GRANTED
                && permisoalmacenamientoLeer == getPackageManager().PERMISSION_GRANTED)
        //si el permiso no esta otorgado mandamos a preguntar otorgarlo
        {
        } else {
            requestPermissions(new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE}, codigo_permiso);
        }
    }


    private void takePhoto(String plantillaExamen) {
        if (imageCapture == null) return;

        imageCapture.takePicture(ContextCompat.getMainExecutor(this), new ImageCapture.OnImageCapturedCallback() {
            @Override
            public void onCaptureSuccess(@NonNull ImageProxy image) {

                // Oculta la vista del preview de la foto.
                imageViewMuestra.setVisibility(View.VISIBLE);

                previewView.setVisibility(View.INVISIBLE);
                imagePreview.setVisibility(View.INVISIBLE);


                // Convert ImageProxy to byte array
                imageToByteArray(image, plantillaExamen);


                image.close();

            }

            @Override
            public void onError(@NonNull ImageCaptureException exception) {
                Log.e("CameraX", "Photo capture failed: " + exception.getMessage(), exception);
            }
        });
    }

    private void imageToByteArray(ImageProxy image, String plantillaExamen) {


        ByteBuffer buffer = image.getPlanes()[0].getBuffer();
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);
        // System.out.println(bytes);

        /// Busca los círculos en la imagen TODO Para Pruebas jpg del directorio.
        String imagePathPrueba = "/data/data/com.universae.correctorexamenes/files/muestraDNIValidos.jpg";  /// Imagen principal
        Mat mat = Imgcodecs.imread(imagePathPrueba);
        /// Todo descomentar para utilizar cámara.
        // Mat mat = processImageData(bytes);

        if (plantillaExamen.equals("plantilla")) {
            listaTodosPlantilla = buscarCirculos.rebuscarCirculos(mat, "all");
            listaBlancosPlantilla = buscarCirculos.rebuscarCirculos(mat, "blancos");
            cuentaMarcadosPlantilla();
            botones();

        } else if (plantillaExamen.equals("examen")){
            System.out.println("Entrando examen");
            listaTodosExamen = buscarCirculos.rebuscarCirculos(mat, "all");
            listaBlancosExamen = buscarCirculos.rebuscarCirculos(mat, "blancos");
            //Guarda la imagen corregida con los circulos por colores
            buscarCirculos.correcionCirculos(listaBlancosExamen, mat);
        }



        mostrarExamen();
    }

    private void mostrarExamen() {


        //        //Corrige el examen
        //        NumerarMarcados numerarMarcados = new NumerarMarcados();
        //        ArrayList<String> listaMarcadosNumerados = numerarMarcados.busquedaLetras(listaTodos, listaBlancos, "arriba");

        // muestra la imagen con los circulos
        String imagePath = "/data/data/com.universae.correctorexamenes/files/todos.jpg";
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        imageViewMuestra.setImageBitmap(bitmap);


        // Reorganiza la pantalla
        image_capture_button.setVisibility(View.INVISIBLE);
        previewView.setVisibility(View.INVISIBLE);
        imagePreview.setVisibility(View.INVISIBLE);
        btnCorregir.setVisibility(View.VISIBLE);
        btnRepetir.setVisibility(View.VISIBLE);
        imageViewMuestra.setVisibility(View.VISIBLE);


        //buscarCirculos.buscarRespuestas(bytes);
        //  return bytes;
    }

    private Mat processImageData(byte[] imageData) {
        // Process the image data (e.g., convert to Bitmap, save to file, etc.)
        // Here, you can save the imageData to a buffer or perform other operations
        // For example, to convert to a Bitmap:
        Mat mat = new Mat();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
        //Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.your_image);
        Utils.bitmapToMat(bitmap, mat);
        return mat;
        // You can now use the bitmap for further processing
        // For instance, saving it to a file or displaying it in an ImageView
    }

    // Cierra el teclado
    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


}
