package com.savenews

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Handler
import android.widget.Toast
import com.savenews.api.APIComponent
import com.savenews.api.APIModule
import com.savenews.api.DaggerAPIComponent
import com.savenews.model.MyObjectBox
import com.savenews.repository.APIURL
import io.objectbox.BoxStore
import kotlin.concurrent.fixedRateTimer

class NewsApplication : Application() {

    companion object {
        var ctx: Context? = null
        lateinit var apiComponent:APIComponent
    }
    object ObjectBox {
        lateinit var boxStore: BoxStore
            private set

        fun init(context: Context) {
            boxStore = MyObjectBox.builder()
                .androidContext(context.applicationContext)
                .build()
        }
    }
    override fun onCreate() {
        super.onCreate()
        ctx = applicationContext
        apiComponent = initDaggerComponent()

        fixedRateTimer("timer",false,0,3000){
            if(!isNetworkAvailable(ctx)) {
                val handler = Handler(applicationContext.mainLooper)
                handler.post { Toast.makeText(applicationContext, "No internet!", Toast.LENGTH_LONG).show() }
            }
        }

        ObjectBox.init(this)
    }

    fun initDaggerComponent():APIComponent{
        apiComponent = DaggerAPIComponent
            .builder()
            .aPIModule(APIModule(APIURL.BASE_URL))
            .build()
        return  apiComponent
    }

    private fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }
}