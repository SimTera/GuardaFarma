plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.compose)
    id("org.jetbrains.kotlin.kapt")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

// 1 SECRETS DEBE IR FUERA DEL BLOQUE ANDROID
secrets {
    defaultPropertiesFileName = "secrets.properties"
}

// 2 KAPT DEBE IR FUERA DEL BLOQUE ANDROID
kapt {
    correctErrorTypes = true
}

android {
    namespace = "es.munvall.guardafarma"
    compileSdk = 36

    defaultConfig {
        applicationId = "es.munvall.guardafarma"
        minSdk = 26
        targetSdk = 36
        versionCode = 4
        versionName = "1.4"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // buildConfigField("String", "MAPS_API_KEY", "\"${secrets.getProperty("MAPS_API_KEY")}\"")
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
        // 3 ACTUALIZADO A JAVA 17
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        // 3 ACTUALIZADO A JAVA 17
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.play.services.maps)
    implementation(libs.androidx.ui.android)
    implementation(libs.androidx.foundation.layout.android)
    implementation(libs.androidx.material3.android)
    implementation(libs.androidx.ui.tooling.preview.android)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.1.5")

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // ViewModel y Gson
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.gson)

    // Dependencias específicas para Google Maps / Hilt con Compose
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3")

    // OSMDroid (Mapas alternativos)
    implementation("org.osmdroid:osmdroid-android:6.1.20") {
        exclude(group = "com.j256.ormlite")
    }
    implementation("org.osmdroid:osmdroid-wms:6.1.20") {
        exclude(group = "com.j256.ormlite")
    }
    implementation("org.osmdroid:osmdroid-geopackage:6.1.20") {
        exclude(group = "com.j256.ormlite")
    }
    implementation("mil.nga.geopackage:geopackage-android:6.7.5")

    // Ubicación y Permisos
    implementation("com.google.android.gms:play-services-location:21.2.0")
    implementation("com.google.accompanist:accompanist-permissions:0.34.0")

    // Serialización y Retrofit
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")
}



