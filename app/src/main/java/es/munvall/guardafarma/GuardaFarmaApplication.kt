package es.munvall.guardafarma

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import org.osmdroid.config.Configuration

@HiltAndroidApp
class GuardaFarmaApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Configuracion OSMDroid
        Configuration.getInstance().userAgentValue = "Guardafarma/1.0"
        Configuration.getInstance().load(this, getSharedPreferences("osmdroid", MODE_PRIVATE))
    }
}