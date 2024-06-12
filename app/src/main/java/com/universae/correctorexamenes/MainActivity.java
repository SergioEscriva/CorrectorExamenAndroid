package com.universae.correctorexamenes;

import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.android.material.textfield.TextInputLayout;
import com.google.common.util.concurrent.ListenableFuture;
import com.universae.correctorexamenes.models.Par;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
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
    private FrameLayout frameNotas;
    private TextView notaFinal, aciertos, fallos, blancos, nulas;
    private TextView notaFinalNum, aciertosNum, fallosNum, blancosNum, nulasNum;
    private TextInputLayout layoutCodigo;
    private TextView textAfinar, textAfinarNum;
    private ProgressBar progressBar;
    private Spinner spinner;


    private ArrayList<String> plantillaDB = new ArrayList<>();
    private BuscarCirculos buscarCirculos = new BuscarCirculos();
    private NumerarCirculos numerarCirculos = new NumerarCirculos();
    private ArreglosBD arreglosBD = new ArreglosBD();
    private int metodo = 0;

    private View view;
    private Map<String, String> nota = new HashMap<>();

    private String codigo;

    private ArrayList<String> listaAbajoMarcadosExamen = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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
        layoutCodigo = findViewById(R.id.layoutCodigo);
        notaFinal = findViewById(R.id.textNota);
        aciertos = findViewById(R.id.textAciertos);
        fallos = findViewById(R.id.textFallos);
        blancos = findViewById(R.id.textBlancos);
        nulas = findViewById(R.id.textNulos);
        notaFinalNum = findViewById(R.id.textNotaNum);
        aciertosNum = findViewById(R.id.textAciertosNum);
        fallosNum = findViewById(R.id.textFallosNum);
        blancosNum = findViewById(R.id.textBlancosNum);
        nulasNum = findViewById(R.id.textNulosNum);
        textAfinar = findViewById(R.id.textAfinar);
        textAfinarNum = findViewById(R.id.textAfinarNum);
        progressBar = findViewById(R.id.progressBar);
        spinner = findViewById(R.id.spinner);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, new String[]{"0.0", "0.4", "0.8", "0.9", "1"});
        //spinner.setAdapter(adapter);

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

        //Spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String seleccion = parent.getItemAtPosition(position).toString();
                textAfinarNum.setText(seleccion);

                // Aquí puedes hacer algo con la selección
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Aquí puedes hacer algo si no se selecciona nada
            }
        });

        btnCorregir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Guarda el examen y corrige
                cuentaMarcadosExamen();
                plantillaDB = arreglosBD.existeEnDB(getBaseContext(), codigo);

                if (plantillaDB.isEmpty()) {
                    image_capture_button.setText("Escanear Plantilla");
                    botones("plantilla");

                } else {
                    crearToast("Plantilla cargada con éxito");
                    image_capture_button.setText("Escanear Examen");

                    calcularNota(listaAbajoMarcadosExamen);
                }

            }


        });
        btnRepetir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Repetir la foto
                textAfinar.setVisibility(View.VISIBLE);
                textAfinarNum.setVisibility(View.VISIBLE);
                spinner.setVisibility(View.VISIBLE);
                image_capture_button.setText("Escanear Examen");
                image_capture_button.setVisibility(View.VISIBLE);
                imageViewMuestra.setImageDrawable(getDrawable(R.drawable.icono_examen));
                imageViewMuestra.setVisibility(View.INVISIBLE);
                previewView.setVisibility(View.VISIBLE);
                imagePreview.setVisibility(View.VISIBLE);
                btnCorregir.setVisibility(View.INVISIBLE);
                btnRepetir.setVisibility(View.INVISIBLE);
                // Oculta e las notas.
                notaFinal.setVisibility(View.INVISIBLE);
                aciertos.setVisibility(View.INVISIBLE);
                fallos.setVisibility(View.INVISIBLE);
                blancos.setVisibility(View.INVISIBLE);
                nulas.setVisibility(View.INVISIBLE);
                notaFinalNum.setVisibility(View.INVISIBLE);
                aciertosNum.setVisibility(View.INVISIBLE);
                fallosNum.setVisibility(View.INVISIBLE);
                blancosNum.setVisibility(View.INVISIBLE);
                nulasNum.setVisibility(View.INVISIBLE);
            }


        });


    }


    private void cuentaMarcadosExamen() {


        NumerarMarcados numerarMarcados = new NumerarMarcados();

        listaAbajoMarcadosExamen = numerarMarcados.busquedaLetras(listaTodosExamen, listaBlancosExamen, "abajo");
        ArrayList<String> listaArribaMarcados = numerarMarcados.busquedaLetras(listaTodosExamen, listaBlancosExamen, "arriba");
        Map<String, String> arrayDatosArriba = numerarMarcados.arrayDatos(listaArribaMarcados);
        codigo = arrayDatosArriba.get("codigo");

        // muestra la imagen corregida con los circulos por colores.
        String imagePath = "/data/data/com.universae.correctorexamenes/files/corregido.jpg";
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        imageViewMuestra.setImageBitmap(bitmap);

        // Guarda la Examen en DB
        arreglosBD.guardarDB(getBaseContext(), listaAbajoMarcadosExamen, arrayDatosArriba, "examen");
        plantillaDB = arreglosBD.existeEnDB(getBaseContext(), codigo);

        // Corrige el examen

    }

    public void calcularNota(ArrayList<String> listaAbajoMarcadosExamen) {
        // Calcula nota examen
        nota = buscarCirculos.calcularNota(plantillaDB, listaAbajoMarcadosExamen, 0.0);

        if (Double.valueOf(nota.get("notaFinal")) <= 4f) {
            notaFinalNum.setTextColor(Color.RED);
        } else {
            notaFinalNum.setTextColor(Color.GREEN);
        }

        // Setea las notas
        notaFinalNum.setText(nota.get("notaFinal"));
        aciertosNum.setTextColor(Color.GREEN);
        aciertosNum.setText(nota.get("aciertos"));
        fallosNum.setTextColor(Color.RED);
        fallosNum.setText(nota.get("fallos"));
        blancosNum.setTextColor(Color.WHITE);
        blancosNum.setText(nota.get("blanco"));
        nulasNum.setTextColor(Color.YELLOW);
        nulasNum.setText(nota.get("nulas"));


        // Oculta el código para ver las notas.
        inputCodigo.setVisibility(View.INVISIBLE);
        layoutCodigo.setVisibility(View.INVISIBLE);
        notaFinal.setVisibility(View.VISIBLE);
        aciertos.setVisibility(View.VISIBLE);
        fallos.setVisibility(View.VISIBLE);
        blancos.setVisibility(View.VISIBLE);
        nulas.setVisibility(View.VISIBLE);
        notaFinalNum.setVisibility(View.VISIBLE);
        aciertosNum.setVisibility(View.VISIBLE);
        fallosNum.setVisibility(View.VISIBLE);
        blancosNum.setVisibility(View.VISIBLE);
        nulasNum.setVisibility(View.VISIBLE);

    }


    private void cuentaMarcadosPlantilla() {
        NumerarMarcados numerarMarcados = new NumerarMarcados();
        ArrayList<String> listaAbajoMarcados = numerarMarcados.busquedaLetras(listaTodosPlantilla, listaBlancosPlantilla, "abajo");
        ArrayList<String> listaArribaMarcados = numerarMarcados.busquedaLetras(listaTodosPlantilla, listaBlancosPlantilla, "arriba");
        Map<String, String> arrayDatosArriba = numerarMarcados.arrayDatos(listaArribaMarcados);

        // muestra la imagen corregida con los circulos por colores.
        String imagePath = "/data/data/com.universae.correctorexamenes/files/corregidoPlantilla.jpg";
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        imageViewMuestra.setImageBitmap(bitmap);

        // Guardar examen Plantilla BD
        String codigoPlantilla = arreglosBD.guardarDB(getBaseContext(), listaAbajoMarcados, arrayDatosArriba, "plantilla");
        plantillaDB = arreglosBD.existeEnDB(getBaseContext(), codigoPlantilla);

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
            public void onClick(View view) {
                switch (metodo) {
                    case 0:
                        botones("examen");
                        metodo = 1;
                        break;
                    case 1:
                        botones("fotoPlantilla");
                        metodo = 0;
                        break;

                }


            }


        });
    }

    public void botones(String examenPlantilla) {

        spinner.setVisibility(View.VISIBLE);
        spinner.setSelection(3);
        previewView.setVisibility(View.VISIBLE);
        imagePreview.setVisibility(View.VISIBLE);
        textAfinar.setVisibility(View.VISIBLE);
        textAfinarNum.setVisibility(View.VISIBLE);


        switch (examenPlantilla) {
            case "examen":
                image_capture_button.setText("Procesando Examen...");
                takePhoto("examen");
                imageViewMuestra.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                previewView.setVisibility(View.INVISIBLE);
                imagePreview.setVisibility(View.INVISIBLE);
                break;
            case "plantilla":
                image_capture_button.setVisibility(View.VISIBLE);
                btnRepetir.setVisibility(View.INVISIBLE);
                btnCorregir.setVisibility(View.INVISIBLE);
                imageViewMuestra.setVisibility(View.INVISIBLE);
                previewView.setVisibility(View.VISIBLE);
                imagePreview.setVisibility(View.VISIBLE);
                crearDialog();
                image_capture_button.setText("Escanear Plantilla");
                break;
            case "fotoPlantilla":
                image_capture_button.setText("Procesando Plantilla...");
                takePhoto("plantilla");
                imageViewMuestra.setVisibility(View.VISIBLE);
                imageViewMuestra.setImageDrawable(getDrawable(R.drawable.icono_examen));
                progressBar.setVisibility(View.VISIBLE);
                previewView.setVisibility(View.INVISIBLE);
                imagePreview.setVisibility(View.INVISIBLE);
                break;


        }

        textAfinar.setVisibility(View.INVISIBLE);
        textAfinarNum.setVisibility(View.INVISIBLE);
        spinner.setVisibility(View.INVISIBLE);

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

        /// Busca los círculos en la imagen TODO Para Pruebas jpg del directorio.
        String imagePathPrueba = "/data/data/com.universae.correctorexamenes/files/muestraDNIValidos.jpg";  /// Imagen principal
        //String imagePathPrueba = "/data/data/com.universae.correctorexamenes/files/muestraDNINoValidos.jpg";
        Mat mat = Imgcodecs.imread(imagePathPrueba);
        /// Todo descomentar para utilizar cámara.
        // Mat mat = processImageData(bytes);

        if (plantillaExamen.equals("plantilla")) {
            listaTodosPlantilla = buscarCirculos.rebuscarCirculos(mat, "all");
            listaBlancosPlantilla = buscarCirculos.rebuscarCirculos(mat, "blancos");
            cuentaMarcadosPlantilla();
            mostrarExamen();


        } else if (plantillaExamen.equals("examen")) {

            listaTodosExamen = buscarCirculos.rebuscarCirculos(mat, "all");
            listaBlancosExamen = buscarCirculos.rebuscarCirculos(mat, "blancos");
            //Guarda la imagen corregida con los circulos por colores
            buscarCirculos.correcionCirculos(listaBlancosExamen, mat);
            mostrarExamen();
        }


    }

    private void mostrarExamen() {
        progressBar.setVisibility(View.INVISIBLE);
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

    public void crearToast(String texto) {
        Toast.makeText(getBaseContext(), texto, Toast.LENGTH_SHORT).show();

    }

    public void crearDialog() {

        AlertDialog alertDialog = new AlertDialog
                .Builder(this)
                .setMessage("Enfoque la plantilla para capturar imagen")
                .setPositiveButton("Cerrar", (dialog, which) -> {
                    dialog.dismiss();
                    ;
                })
                .create();
        alertDialog.show();
    }


}
