package dev.mrbe.hymnary

import android.app.Application
import dev.mrbe.hymnary.ui.auth.AuthViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        /**
         * use Koin Library as a service locator
         */
        val myModule = module {
            //Declare a ViewModel - be later inject into Fragment with dedicated injector using by viewModel()
            viewModel {
                AuthViewModel()
            }
        }
        startKoin {
            androidContext(this@App)
            modules(listOf(myModule))
        }
    }

}