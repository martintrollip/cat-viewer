package martintrollip.cats.app.utils;

import android.content.Intent
import android.content.pm.PackageManager

/**
 * Extension functions for Intents
 */
fun Intent.canLaunch(packageManager: PackageManager): Boolean {
    return resolveActivity(packageManager) != null
}