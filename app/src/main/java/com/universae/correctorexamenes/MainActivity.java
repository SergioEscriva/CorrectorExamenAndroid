package com.universae.correctorexamenes;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
    int codigo_permiso = 200;
    private PreviewView previewView;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private ImageCapture imageCapture;
    private Button image_capture_button;


    private Button btnCorregir;
    private Button btnRepetir;
    private ImageView imagePreview;
    private SubsamplingScaleImageView imageViewMuestra;

    private TextView textDNI, textDNINum, textCodigo, textCodigoNum;
    private List<Par> listaMarcadosExamen;
    private List<Par> listaTodosExamen;
    private List<Par> listaMarcadosPlantilla;
    private List<Par> listaTodosPlantilla;

    private TextView notaFinal, aciertos, fallos, blancos, nulas;
    private TextView notaFinalNum, aciertosNum, fallosNum, blancosNum, nulasNum, textPena;

    private TextView textAfinar;
    private ProgressBar progressBar;
    private Spinner spinnerAfinar, spinnerPena;

    private FloatingActionButton fabEditar;


    private ArrayList<String> plantillaDB = new ArrayList<>();
    private BuscarCirculos buscarCirculos = new BuscarCirculos();
    private NumerarCirculos numerarCirculos = new NumerarCirculos();
    private ArreglosBD arreglosBD = new ArreglosBD();
    private int metodo = 0;


    private Map<String, String> nota = new HashMap<>();

    private String codigo, seleccionPenalizacion, seleccionAfinar;

    private ArrayList<String> examenDB = new ArrayList<>();
    private Mat imagenMat1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainA), (v, insets) -> {
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
        imageViewMuestra.setImage(ImageSource.resource(R.drawable.iconouniversaesinchange));


        textCodigo = findViewById(R.id.textCodigo);
        textCodigoNum = findViewById(R.id.textCodigoNum);
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
        textPena = findViewById(R.id.textPena);
        progressBar = findViewById(R.id.progressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);
        textDNI = findViewById(R.id.textDNI);
        textDNINum = findViewById(R.id.textDNINum);
        fabEditar = findViewById(R.id.fab_editar);
        image_capture_button = findViewById(R.id.image_capture_button);
        spinnerAfinar = findViewById(R.id.spinner);
        spinnerPena = findViewById(R.id.spinnerPena);

        //Spinner
        ArrayAdapter<String> adapter = new MyColorizedArrayAdapter(this, android.R.layout.simple_spinner_item, new String[]{"0.0", "0.4", "0.8", "0.9", "1"});
        spinnerAfinar.setAdapter(adapter);
        spinnerAfinar.setSelection(2);
        seleccionAfinar = spinnerAfinar.getSelectedItem().toString();

        //Spinner Penalizacion
        ArrayAdapter<String> adapterPena = new MyColorizedArrayAdapter(this, android.R.layout.simple_spinner_item, new String[]{"0.0", "0.10", "0.15", "0.20", "0.25", "0.33"});
        spinnerPena.setAdapter(adapterPena);
        spinnerPena.setSelection(0);
        seleccionPenalizacion = spinnerPena.getSelectedItem().toString();


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
        spinnerAfinar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                seleccionAfinar = parent.getItemAtPosition(position).toString();
                // Aquí puedes hacer algo con la selección
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Aquí puedes hacer algo si no se selecciona nada
            }
        });

        spinnerPena.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                seleccionPenalizacion = parent.getItemAtPosition(position).toString();
                if (! examenDB.isEmpty()) {
                    calcularNota(examenDB);
                }

                // Aquí puedes hacer algo con la selección
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Aquí puedes hacer algo si no se selecciona nada
            }
        });

        //Guarda el examen y corrige
        btnCorregir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cuentaMarcadosExamen();
                plantillaDB = arreglosBD.existePlantillaEnDB(getBaseContext(), codigo);

                if (codigo.contains("Error") || textDNINum.getText().toString().contains("Error")) {
                    crearToast("NO GUARDADO, Error en el código, o DNI, volver a Escanear.");
                    repetirFoto();
                } else if (plantillaDB.isEmpty()) {
                    image_capture_button.setText("Escanear Plantilla");
                    crearToast("DETECTADA PLANTILLA NUEVA");
                    botones("plantilla");

                } else {

                    crearToast(String.format("Examen corregido"));
                    image_capture_button.setText("Escanear Examen");

                    calcularNota(examenDB);
                }

            }


        });
        btnRepetir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repetirFoto();
            }


        });
        fabEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditarActivity.class);
                String identificacion = textDNINum.getText().toString();
                intent.putExtra("identificacion", identificacion);
                intent.putExtra("codigo", codigo);
                startActivity(intent);


            }


        });


    }


    private void cuentaMarcadosExamen() {


        NumerarMarcados numerarMarcados = new NumerarMarcados();

        ArrayList<String> listaAbajoMarcadosExamen = numerarMarcados.busquedaLetras(listaTodosExamen, listaMarcadosExamen, "abajo");
        ArrayList<String> listaArribaMarcados = numerarMarcados.busquedaLetras(listaTodosExamen, listaMarcadosExamen, "arriba");
        Map<String, String> arrayDatosArriba = numerarMarcados.arrayDatos(listaArribaMarcados);
        codigo = arrayDatosArriba.get("codigo");
        String identificacion = arrayDatosArriba.get("identificacion");
        textDNINum.setText(identificacion);
        textCodigoNum.setText(codigo);


        // Si es nulo los datos, no guarda.
        if (arrayDatosArriba.containsValue("Error")) {
            crearToast("NO GUARDADO, Error de identificación, volver a Escanear.");
        } else {
            // Guarda la Examen en DB
            arreglosBD.guardarDB(getBaseContext(), listaAbajoMarcadosExamen, listaMarcadosExamen, arrayDatosArriba, "examen");
            plantillaDB = arreglosBD.existePlantillaEnDB(getBaseContext(), codigo);
        }

        examenDB = arreglosBD.existeAlumnoEnDB(getBaseContext(), identificacion, codigo);


    }

    public Bitmap imagenRecortada(Bitmap bitmap) {
        // Verificar si la imagen se ha cargado correctamente
        Bitmap finalBitmap = null;
        if (bitmap != null) {
            // Obtener las dimensiones de la imagen original
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();

            // Calcular las coordenadas para recortar la mitad inferior
            int x = 0;
            int y = 2200;//height / 2;
            int croppedHeight = 1800;//height / 2;

            // Crear un nuevo bitmap con solo la mitad inferior de la imagen original
            Bitmap croppedBitmap = Bitmap.createBitmap(bitmap, x, y, width, croppedHeight);

            // Aplicar zoom (por ejemplo, un 20% de aumento)
            float zoomFactor = 1.2f;
            int zoomedWidth = (int) (width * zoomFactor);
            int zoomedHeight = (int) (croppedHeight * zoomFactor);

            // Crear un bitmap escalado
            Bitmap zoomedBitmap = Bitmap.createScaledBitmap(croppedBitmap, zoomedWidth, zoomedHeight, true);

            // Ajustar el bitmap escalado al tamaño original de la mitad inferior
            int offsetX = (zoomedWidth - width) / 2;
            int offsetY = (zoomedHeight - croppedHeight) / 2;
            finalBitmap = Bitmap.createBitmap(zoomedBitmap, offsetX, offsetY, width, croppedHeight);
        }
        return finalBitmap;
    }

    public void calcularNota(ArrayList<String> examenDB) {
        // marca los circulos que no se detectaron como marcados
        List<Par> listadoMarcadosPlantilla = arreglosBD.coordenadasPlantillaEnDB(getBaseContext(), codigo);

        buscarCirculos.correcionCirculos(listadoMarcadosPlantilla, imagenMat1);
        Double penalizacion = Double.valueOf(seleccionPenalizacion);
        // Calcula nota examen
        nota = buscarCirculos.calcularNota(plantillaDB, examenDB, penalizacion);
        if (Double.valueOf(nota.get("notaFinal")) <= 4f) {
            notaFinalNum.setTextColor(Color.RED);
        } else {
            notaFinalNum.setTextColor(Color.GREEN);
        }

        // muestra la imagen corregida con los circulos por colores.
        String imagePathCorregido = "/data/data/com.universae.correctorexamenes/files/corregido.jpg";
        Bitmap bitmap = BitmapFactory.decodeFile(imagePathCorregido);
        Bitmap croppedBitmap = imagenRecortada(bitmap);
        // imageViewMuestra.setImageBitmap(croppedBitmap);
        imageViewMuestra.setImage(ImageSource.uri(imagePathCorregido));

        // Configurar el nivel de zoom inicial
        imageViewMuestra.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CUSTOM);
        imageViewMuestra.setMinScale(0.5f); // Establecer el nivel de zoom inicial
        imageViewMuestra.setMaxScale(1.0f); // Opcional: establecer el nivel de zoom máximo

        // Esperar hasta que la imagen esté cargada para configurar la vista inicial
        imageViewMuestra.setOnImageEventListener(new SubsamplingScaleImageView.OnImageEventListener() {
            @Override
            public void onReady() {
                // Imagen cargada
                float initialScale = imageViewMuestra.getMinScale(); // Obtener el nivel de zoom inicial

                // Calcular la posición inicial para mostrar la parte inferior de la imagen
                int imageHeight = imageViewMuestra.getSHeight(); // Altura de la imagen
                PointF center = new PointF(imageViewMuestra.getSWidth() / 2, imageHeight);

                // Aplicar el nivel de zoom y centrar en la parte inferior
                imageViewMuestra.setScaleAndCenter(initialScale, center);
            }

            @Override
            public void onImageLoaded() {
            }

            @Override
            public void onPreviewLoadError(Exception e) {
            }

            @Override
            public void onImageLoadError(Exception e) {
            }

            @Override
            public void onTileLoadError(Exception e) {
            }

            @Override
            public void onPreviewReleased() {
            }
        });


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
        textDNI.setVisibility(View.VISIBLE);
        textDNINum.setVisibility(View.VISIBLE);
        textCodigo.setVisibility(View.VISIBLE);
        textCodigoNum.setVisibility(View.VISIBLE);
        textPena.setVisibility(View.VISIBLE);
        spinnerPena.setVisibility(View.VISIBLE);
        fabEditar.setVisibility(View.VISIBLE);


    }


    private void cuentaMarcadosPlantilla() {
        NumerarMarcados numerarMarcados = new NumerarMarcados();
        ArrayList<String> listaAbajoMarcados = numerarMarcados.busquedaLetras(listaTodosPlantilla, listaMarcadosPlantilla, "abajo");
        ArrayList<String> listaArribaMarcados = numerarMarcados.busquedaLetras(listaTodosPlantilla, listaMarcadosPlantilla, "arriba");
        Map<String, String> arrayDatosArriba = numerarMarcados.arrayDatos(listaArribaMarcados);

        // muestra la imagen corregida con los circulos por colores.
        String imagePath = "/data/data/com.universae.correctorexamenes/files/corregidoPlantilla.jpg";
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        //imageViewMuestra.setImageBitmap(bitmap);
        imageViewMuestra.setImage(ImageSource.uri(imagePath));


        // Guardar examen Plantilla BD
        String codigoPlantilla = arreglosBD.guardarDB(getBaseContext(), listaAbajoMarcados, listaMarcadosPlantilla, arrayDatosArriba, "plantilla");
        plantillaDB = arreglosBD.existePlantillaEnDB(getBaseContext(), codigoPlantilla);

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

        spinnerAfinar.setVisibility(View.VISIBLE);
        previewView.setVisibility(View.VISIBLE);
        imagePreview.setVisibility(View.VISIBLE);
        textAfinar.setVisibility(View.VISIBLE);

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


                // muestra logo durante la carga y correción del examen.
                imageViewMuestra.setImage(ImageSource.resource(R.drawable.iconouniversaesinchange));



                progressBar.setVisibility(View.VISIBLE);
                previewView.setVisibility(View.INVISIBLE);
                imagePreview.setVisibility(View.INVISIBLE);
                break;


        }

        textAfinar.setVisibility(View.INVISIBLE);
        spinnerAfinar.setVisibility(View.INVISIBLE);

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
        //String imagePathPrueba = "/data/data/com.universae.correctorexamenes/files/muestraPlantilla.jpg";  /// Imagen principal
        //String imagePathPrueba = "/data/data/com.universae.correctorexamenes/files/muestraExamenBien.jpg";
        String imagePathPrueba = "/data/data/com.universae.correctorexamenes/files/muestraExamenMalas.jpg";
        //String imagePathPrueba = "/data/data/com.universae.correctorexamenes/files/muestraExamenMal.jpg";
        Mat imagenMat = Imgcodecs.imread(imagePathPrueba);
        imagenMat1 = Imgcodecs.imread(imagePathPrueba);

        /// Todo descomentar para utilizar cámara.
        //Mat imagenMat = processImageData(bytes);


        if (plantillaExamen.equals("plantilla")) {
            listaTodosPlantilla = buscarCirculos.rebuscarCirculos(imagenMat, "all", seleccionAfinar);
            listaMarcadosPlantilla = buscarCirculos.rebuscarCirculos(imagenMat, "blancos", seleccionAfinar);
            cuentaMarcadosPlantilla();
            mostrarExamen();


        } else if (plantillaExamen.equals("examen")) {

            listaTodosExamen = buscarCirculos.rebuscarCirculos(imagenMat, "all", seleccionAfinar);
            listaMarcadosExamen = buscarCirculos.rebuscarCirculos(imagenMat, "blancos", seleccionAfinar);
            // Save the modified image


            if (listaTodosExamen.size() == 326) {

                //Guarda la imagen corregida con los circulos por colores
                //buscarCirculos.correcionCirculos(listaBlancosExamen, imagenMat1);
                mostrarExamen();
            } else {
                crearToast("No se detectaron todos los circulos");
                repetirFoto();

            }
        }


    }

    private void mostrarExamen() {
        progressBar.setVisibility(View.INVISIBLE);
        // muestra la imagen con los circulos
        String imagePath = "/data/data/com.universae.correctorexamenes/files/todos.jpg";
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        //imageViewMuestra.setImageBitmap(bitmap);
        imageViewMuestra.setImage(ImageSource.uri(imagePath));
        imageViewMuestra.setMinScale(0.0f); // Establecer el nivel de zoom inicial


        // Reorganiza la pantalla
        image_capture_button.setVisibility(View.INVISIBLE);
        previewView.setVisibility(View.INVISIBLE);
        imagePreview.setVisibility(View.INVISIBLE);
        btnCorregir.setVisibility(View.VISIBLE);
        btnRepetir.setVisibility(View.VISIBLE);
        imageViewMuestra.setVisibility(View.VISIBLE);


    }

    private Mat processImageData(byte[] imageData) {
        // For example, to convert to a Bitmap:
        Mat mat = new Mat();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);

        Utils.bitmapToMat(bitmap, mat);

        return mat;
    }

    public void crearToast(String texto) {
        Toast.makeText(getBaseContext(), texto, Toast.LENGTH_LONG).show();

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

    public void repetirFoto() {
        //Repetir la foto
        textAfinar.setVisibility(View.VISIBLE);
        spinnerAfinar.setVisibility(View.VISIBLE);
        image_capture_button.setText("Escanear Examen");
        image_capture_button.setVisibility(View.VISIBLE);
        // muestra logo durante la carga y correción del examen.
        imageViewMuestra.setImage(ImageSource.resource(R.drawable.iconouniversaesinchange));
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
        textPena.setVisibility(View.INVISIBLE);
        spinnerPena.setVisibility(View.INVISIBLE);
        textDNI.setVisibility(View.INVISIBLE);
        textDNINum.setVisibility(View.INVISIBLE);
        textCodigo.setVisibility(View.INVISIBLE);
        textCodigoNum.setVisibility(View.INVISIBLE);
        fabEditar.setVisibility(View.INVISIBLE);
    }

    public class MyColorizedArrayAdapter extends ArrayAdapter<String> {

        public MyColorizedArrayAdapter(Context context, int layoutResource, String[] items) {
            super(context, layoutResource, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = (TextView) super.getView(position, convertView, parent);


            textView.setTextColor(Color.WHITE);
            textView.setTextSize(16);

            return textView;
        }

    }


}
