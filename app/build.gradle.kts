plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.compose)
    id("org.jetbrains.kotlin.kapt")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")

}


android {
    namespace = "com.example.guardafarma"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.guardafarma"
        minSdk = 26
        targetSdk = 35
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
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11

    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    secrets {
        defaultPropertiesFileName = "secrets.properties"
        // opcionalmente, puedes especificar el nombre del recurso generado
    }
    kapt {
        correctErrorTypes = true // ✅ Dentro de android
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.play.services.maps)
    implementation(libs.firebase.crashlytics.buildtools)
    implementation(libs.androidx.ui.android)
    implementation(libs.androidx.foundation.layout.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.1.5")

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.gson)
    implementation(libs.androidx.lifecycle.viewmodel.ktx) // Ejemplo con tu formato

    // Dependencias específicas para Google Maps con Compose
    implementation ("androidx.hilt:hilt-navigation-compose:1.2.0")
    implementation("com.google.maps.android:android-maps-utils:3.12.0")


    implementation("com.google.maps.android:maps-compose:4.4.1")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3") // Usa la última versión disponible para corrutinas

    // Opcional: para usar la ubicación del usuario o un json
    implementation("com.google.android.gms:play-services-location:21.2.0")
    implementation ("com.google.accompanist:accompanist-permissions:0.34.0")

    // Gson si llegas a usar JSON
    implementation("com.google.code.gson:gson:2.10.1")


}



