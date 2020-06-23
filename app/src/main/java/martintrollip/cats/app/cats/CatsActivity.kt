package martintrollip.cats.app.cats

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import martintrollip.cats.app.R
import martintrollip.cats.app.utils.canLaunch


/**
 * Main activity for the cat viewer. Holds the Navigation Host Fragment and the Drawer, Toolbar, etc.
 */
class CatsActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cats)
        setupNavigation()
    }

    override fun onResume() {
        super.onResume()
        if (!isConnected()) {
            val alertDialog: AlertDialog = AlertDialog.Builder(this).create()

            alertDialog.setTitle(R.string.network_error_title)
            alertDialog.setMessage(getString(R.string.network_error_message))
            alertDialog.setIcon(R.drawable.ic_cat)
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.ok), { dialog, onOk -> launchConnectivitySettings() })

            alertDialog.show()
        }
    }

    private fun setupNavigation() {
        setupNavigationDrawer()
        setSupportActionBar(findViewById(R.id.toolbar))

        val navController: NavController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration =
                AppBarConfiguration.Builder(R.id.cat_list_fragment_dest, R.id.about_fragment_dest)
                        .setOpenableLayout(drawerLayout)
                        .build()
        setupActionBarWithNavController(navController, appBarConfiguration)
        findViewById<NavigationView>(R.id.nav_view).setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun setupNavigationDrawer() {
        drawerLayout = (findViewById<DrawerLayout>(R.id.drawer_layout))
                .apply {
                    setStatusBarBackground(R.color.colorPrimaryDark)
                }
    }

    private fun isConnected(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
        return networkInfo?.isConnected ?: false
    }

    @SuppressLint("NewApi") /*because we check if the new intent can be launch otherwise fall back to default*/
    private fun launchConnectivitySettings() {
        var intent = Intent(Settings.ACTION_DATA_USAGE_SETTINGS)

        if (!intent.canLaunch(packageManager)) {
            intent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
        }

        startActivity(intent)
    }
}
