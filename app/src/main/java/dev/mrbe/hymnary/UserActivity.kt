package dev.mrbe.hymnary

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import dev.mrbe.hymnary.databinding.ActivityUserBinding

class UserActivity : AppCompatActivity() {


    //binding var
    private lateinit var binding: ActivityUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }


//    companion object {
//        private const val TAG = "Hymnary"
//        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
//        private const val REQUEST_CODE_PERMISSIONS = 10
//        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
//    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.overflow_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.sign_in -> {
                startActivity(Intent(this.applicationContext, AuthActivity::class.java))
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}

