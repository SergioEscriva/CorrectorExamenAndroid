package com.universae.correctorexamenes.utilidades;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

private void saveBitmap(Bitmap bitmap) {
    // Save the bitmap to a buffer or file
    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
    byte[] byteArray = stream.toByteArray();

    // You can now use byteArray as the buffer
    // Or save it to a file
    try {
        FileOutputStream fos = new FileOutputStream(new File(getExternalFilesDir(null), "scanned_document.png"));
        fos.write(byteArray);
        fos.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
