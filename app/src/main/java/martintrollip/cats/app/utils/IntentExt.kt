package martintrollip.cats.app.utils;

import android.content.Intent
import android.content.pm.PackageManager

/**
 * @author Martin Trollip
 * @since 2020/06/23 17:32
 */
fun Intent.canLaunch(packageManager: PackageManager): Boolean {
    return resolveActivity(packageManager) != null
}