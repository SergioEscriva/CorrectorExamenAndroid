package com.universae.correctorexamenes.utilidades;

import android.graphics.Bitmap;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

private Bitmap processDocument(Bitmap bitmap) {
    // Convert bitmap to Mat
    Mat mat = new Mat();
    Utils.bitmapToMat(bitmap, mat);

    // Convert to grayscale
    Mat gray = new Mat();
    Imgproc.cvtColor(mat, gray, Imgproc.COLOR_BGR2GRAY);

    // Apply Gaussian blur
    Imgproc.GaussianBlur(gray, gray, new Size(5, 5), 0);

    // Detect edges
    Mat edges = new Mat();
    Imgproc.Canny(gray, edges, 75, 200);

    // Find contours
    List<MatOfPoint> contours = new ArrayList<>();
    Mat hierarchy = new Mat();
    Imgproc.findContours(edges, contours, hierarchy, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);

    // Find the biggest rectangle
    MatOfPoint2f biggest = findBiggestRectangle(contours);

    // Warp perspective
    if (biggest != null) {
        Mat warp = warpPerspective(mat, biggest);
        Utils.matToBitmap(warp, bitmap);
    }

    return bitmap;
}

private MatOfPoint2f findBiggestRectangle(List<MatOfPoint> contours) {
    // Find the biggest rectangle among the contours
    // This is where you implement contour filtering and rectangle detection
    return null; // Placeholder, replace with actual detected rectangle
}

private Mat warpPerspective(Mat mat, MatOfPoint2f biggest) {
    // Warp the perspective to get a top-down view of the document
    MatOfPoint2f dest = new MatOfPoint2f(
            new Point(0, 0),
            new Point(mat.cols() - 1, 0),
            new Point(mat.cols() - 1, mat.rows() - 1),
            new Point(0, mat.rows() - 1)
    );

    Mat warpMat = Imgproc.getPerspectiveTransform(biggest, dest);
    Mat warp = new Mat();
    Imgproc.warpPerspective(mat, warp, warpMat, mat.size());
    return warp;
}

