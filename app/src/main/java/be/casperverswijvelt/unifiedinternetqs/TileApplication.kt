package be.casperverswijvelt.unifiedinternetqs

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.util.Log
import android.widget.Toast
import be.casperverswijvelt.unifiedinternetqs.util.ExecutorServiceSingleton
import be.casperverswijvelt.unifiedinternetqs.util.ShizukuUtil
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.topjohnwu.superuser.Shell

class TileApplication : Application() {

    companion object {
        const val CHANNEL_ID = "autoStartServiceChannel"
        const val CHANNEL_NAME = "Shizuku Detection"
        const val TAG = "TileApplication"
    }

    override fun onCreate() {
        super.onCreate()

        Log.d(TAG, "Created Tile Application")

        ExecutorServiceSingleton.getInstance()

        // Check if main shell has root access
        Shell.getShell {

            // If neither root access or Shizuku access is detected, assume that
            //  Shizuku is used but not bound yet: start Shizuku detection
            //  foreground service.
            // See https://github.com/RikkaApps/Shizuku/issues/175 for why this is
            //  needed
            if (
                Shell.isAppGrantedRoot() != true &&
                !ShizukuUtil.hasShizukuPermission()
            ) {

                startShizukuDetectionService()
            }
        }
    }

    private fun startShizukuDetectionService() {
        try {
            createNotificationChannel()
            startForegroundService(
                Intent(
                    this,
                    ShizukuDetectService::class.java
                )
            )
        } catch (e: Throwable) {
            Log.d(
                TAG,
                "Failed to start foreground service due to an ${e.message}"
            )
            FirebaseCrashlytics.getInstance().recordException(e)

            // Not sure what the cause of the 'ForegroundServiceStartNotAllowedException'
            //  is or how to solve it.
            Toast.makeText(
                applicationContext,
                R.string.toast_foreground_service_error,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun createNotificationChannel() {
        val serviceChannel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        getSystemService(NotificationManager::class.java)
            .createNotificationChannel(serviceChannel)
    }
}