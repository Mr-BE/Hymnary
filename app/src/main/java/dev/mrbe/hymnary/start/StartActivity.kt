package dev.mrbe.hymnary.start

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import dev.mrbe.hymnary.R
import dev.mrbe.hymnary.databinding.ActivityStartBinding

class StartActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // make the activity on full screen
//        requestWindowFeature(Window.FEATURE_NO_TITLE)
//        window.setFlags(
//            WindowManager.LayoutParams.FLAG_FULLSCREEN,
//            WindowManager.LayoutParams.FLAG_FULLSCREEN)

//        window.insetsController?.hide(WindowInsets.Type.statusBars())

        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

//        val navController = findNavController(R.id.nav_host_fragment_content_start)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_start) as NavHostFragment
        val navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

//        supportActionBar?.hide()

//        binding.fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }
    }

//    override fun onWindowFocusChanged(hasFocus: Boolean) {
//        super.onWindowFocusChanged(hasFocus)
//        if (hasFocus) {
//            hideSystemUI()
//        }
//    }
//
//    private fun hideSystemUI() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            window.setDecorFitsSystemWindows(false)
//            window.insetsController?.let {
//                it.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
//                it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
//            }
//        } else {
//            @Suppress("DEPRECATION")
//            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
////                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
////                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
////                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
////                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
////                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
//                    )
//        }
////        supportActionBar?.hide()
//    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_start)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}