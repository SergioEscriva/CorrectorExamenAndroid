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

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation ("androidx.camera:camera-camera2:1.0.0-beta04")
    implementation ("androidx.camera:camera-lifecycle:1.0.0-beta04")
    implementation ("androidx.camera:camera-view:1.0.0-alpha11")


}