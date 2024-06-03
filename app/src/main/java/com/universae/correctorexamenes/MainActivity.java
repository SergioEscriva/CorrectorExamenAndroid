package com.universae.correctorexamenes;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

import android.Manifest;
import android.content.pm.PackageManager;

import java.util.AbstractCollection;

public class MainActivity extends AppCompatActivity {
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    PreviewView previewView;
    private ImageCapture imageCapture;

    int codigo_permiso=200;

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
        verificarPermisos();
        previewView = findViewById(R.id.preview_view);

        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                startCameraX(cameraProvider);

            }  catch (ExecutionException e)
            {e.printStackTrace();}
            catch ( InterruptedException e) {
                e.printStackTrace();
            }
        }, getExecutor());


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
        cameraProvider.bindToLifecycle((LifecycleOwner) this,cameraSelector,preview,imageCapture);

    }




    private void verificarPermisos() {
        int permisoCamara = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int permisoalmacenamientoEscribir=ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permisoalmacenamientoLeer=ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE);

        if(permisoCamara== getPackageManager().PERMISSION_GRANTED
                && permisoalmacenamientoEscribir==getPackageManager().PERMISSION_GRANTED
                && permisoalmacenamientoLeer==getPackageManager().PERMISSION_GRANTED )
        //si el permiso no esta otorgado mandamos a preguntar otorgarlo
        {}else{requestPermissions(new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE},codigo_permiso);}
    }
}