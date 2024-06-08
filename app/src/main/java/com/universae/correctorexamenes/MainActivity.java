package com.universae.correctorexamenes;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

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

import com.google.common.util.concurrent.ListenableFuture;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;

import java.nio.ByteBuffer;
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
    private Button btnCorrecto;
    private Button btnIncorrecto;
    private ImageView imagePreview;
    private ImageView imageViewMuestra;
    private List<Par> listaBlancos;
    private List<Par> listaTodos;
    private BuscarCirculos buscarCirculos = new BuscarCirculos();
    private NumerarCirculos numerarCirculos = new NumerarCirculos();
    private ProgressBar progressBar;


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
        //        {
        //            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        //                System.loadLibrary("opencv_jni");
        //            } else {
        //                System.loadLibrary("opencv");
        //            }
        //        }
        verificarPermisos();
        previewView = findViewById(R.id.preview_view);
        btnCorrecto = findViewById(R.id.BttnCorrecto);
        btnIncorrecto = findViewById(R.id.BttnRepetir);
        imagePreview = findViewById(R.id.imageView2);
        imageViewMuestra = findViewById(R.id.imgViewMuestra);


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


        btnCorrecto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //                NumerarMarcados numerarMarcados = new NumerarMarcados();
                //
                //                Map<Integer, String> listaMarcadosNumerados = numerarMarcados.busquedaLetras(listaTodos, listaBlancos, "arriba");

                // muestra la imagen corregida con los circulos por colores.
                String imagePath = "/data/data/com.universae.correctorexamenes/files/corregido.jpg";
                Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                imageViewMuestra.setImageBitmap(bitmap);
                // System.out.println(listaMarcadosNumerados);
            }

            ;

        });


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
                takePhoto();
                image_capture_button.setText("Procesando...");
                previewView.setVisibility(View.INVISIBLE);
                imagePreview.setVisibility(View.INVISIBLE);

            }

            ;

        });
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


    private void takePhoto() {
        if (imageCapture == null) return;

        imageCapture.takePicture(ContextCompat.getMainExecutor(this), new ImageCapture.OnImageCapturedCallback() {
            @Override
            public void onCaptureSuccess(@NonNull ImageProxy image) {
                // Convert ImageProxy to byte array
                byte[] imageData = imageToByteArray(image);

                // Process the image data as needed
                //processImageData(imageData);

                image.close();

            }

            @Override
            public void onError(@NonNull ImageCaptureException exception) {
                Log.e("CameraX", "Photo capture failed: " + exception.getMessage(), exception);
            }
        });
    }

    private byte[] imageToByteArray(ImageProxy image) {
        ByteBuffer buffer = image.getPlanes()[0].getBuffer();
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);
        // System.out.println(bytes);
        Mat mat = processImageData(bytes);
        //Mat mat = Imgcodecs.imdecode(bytes, Imgcodecs.IMREAD_UNCHANGED);
        // (new MatOfByte(bytes), Imgcodecs.IMREAD_UNCHANGED);//CV_LOAD_IMAGE_UNCHANGED);

        // Busca los círculos en la imagen
        listaTodos = buscarCirculos.rebuscarCirculos(mat, "all");
        listaBlancos = buscarCirculos.rebuscarCirculos(mat, "blancos");

        // Recupera imagen con todos los círculos y los muesta
        String imagePath = "/data/data/com.universae.correctorexamenes/files/todos.jpg";
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        imageViewMuestra.setImageBitmap(bitmap);


        //Corrige el examen
        NumerarMarcados numerarMarcados = new NumerarMarcados();
        Map<Integer, String> listaMarcadosNumerados = numerarMarcados.busquedaLetras(listaTodos, listaBlancos, "arriba");

        //Guarda la imagen corregida con los circulos por colores
        buscarCirculos.correcionCirculos(listaBlancos);


        // Reorganiza la pantalla
        image_capture_button.setVisibility(View.INVISIBLE);
        previewView.setVisibility(View.INVISIBLE);
        imagePreview.setVisibility(View.INVISIBLE);
        btnCorrecto.setVisibility(View.VISIBLE);
        btnIncorrecto.setVisibility(View.VISIBLE);
        imageViewMuestra.setVisibility(View.VISIBLE);


        //buscarCirculos.buscarRespuestas(bytes);
        return bytes;
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


}
