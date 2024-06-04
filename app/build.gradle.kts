plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.universae.correctorexamenes"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.universae.correctorexamenes"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

//    implementation(libs.appcompat)
//    implementation(libs.material)
    implementation(files("libs/opencv-490.jar"))
    implementation(files("libs/libopencv_java490.so"))


    implementation("com.rmtheis:tess-two:9.1.0")


    // implementation(files("cosas/tess4j-4.0.0.jar"))
//    implementation(libs.activity)
//    implementation(libs.constraintlayout)
//    testImplementation(libs.junit)
//    androidTestImplementation(libs.ext.junit)
//    androidTestImplementation(libs.espresso.core)
//    implementation("org.opencv:opencv-android:4.5.3")
//    implementation("com.github.journeyapps:zxing-android-embedded:4.3.0")
//    implementation("com.google.zxing:core:3.4.1")
//    implementation("androidx.camera:camera-core:1.0.1")
//    implementation("androidx.camera:camera-camera2:1.0.1")
//    implementation("androidx.camera:camera-lifecycle:1.0.1")

//    implementation ("androidx.camera:camera-camera2:1.0.0-beta04")
//    implementation ("androidx.camera:camera-lifecycle:1.0.0-beta04")
//    implementation ("androidx.camera:camera-view:1.0.0-alpha11")

    // implementation("com.google.android.gms:play-services-vision:19.2.0")


    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.sceneform.base)
    implementation(project(":OpenCV"))
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("androidx.camera:camera-camera2:1.0.0-beta04")
    implementation("androidx.camera:camera-lifecycle:1.0.0-beta04")
    implementation("androidx.camera:camera-view:1.0.0-alpha11")
    //implementation (project(":opencv"))

}

