package dev.mrbe.hymnary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import dev.mrbe.hymnary.databinding.ActivityMainBinding
import androidx.navigation.ui.AppBarConfiguration
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private val cacheHelper: CacheHelper by inject()


    private lateinit var drawerLayout: DrawerLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(
            this, R.layout.activity_main
        )

        //attach drawer layout
        drawerLayout = binding.drawerLayout

        //set up navigation items
//        val navController = this.findNavController(R.id.navHostFragment)

        //this is currently is the only way to set up a NavController using FragmentContainerView
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val controller = navHostFragment.navController

        NavigationUI.setupActionBarWithNavController(this, controller, drawerLayout)
        NavigationUI.setupWithNavController(binding.navView, controller)
    }

    //Control up button and hamburger icon
    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.navHostFragment)
//        return navController.navigateUp() //handle just up navigation

        //allow system modify ui to show either hamburger or up navigation icons
        return NavigationUI.navigateUp(navController, drawerLayout)
    }
}