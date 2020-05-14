package com.myproject.retrofitdemo.services

import android.os.Build
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

object ServiceBuilder {


    // private const val URL = "http://10.0.2.3:9000" // genymotion ip
    //private const val URL = "http://127.0.0.1:9000" // localhost ip
    private const val URL = "https://sparshims.com/sparshims_sales/"


    /*Level.BODY
    Level.BASIC
    Level.HEADERS
    Level.NONE
     */
    private val logger: HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    // Create a Custom Interceptor to apply Headers Application wide
    // It add Header to all http request of application

    val headerIntercaptor:Interceptor = object:Interceptor{
        override fun intercept(chain: Interceptor.Chain): Response {

            var request: Request = chain.request()

            request = request.newBuilder()
                .addHeader("x-device-type", Build.DEVICE)  // avoid hardcoding headers and using system's
                .addHeader("Accept-Language", Locale.getDefault().language)
                .build()

            val response: Response = chain.proceed(request)

            return response
        }
    }

    // Create OkHttp Client
    private val okHttp: OkHttpClient.Builder = OkHttpClient.Builder()
        .callTimeout(5,TimeUnit.SECONDS) // by default timeout duration is 10 Seconds here we are changing it to 5 seconds
        .addInterceptor(headerIntercaptor) // add only if you want to add header interceptor for all http request
        .addInterceptor(logger) // Adding Logging Interceptor to log request and response  details

// Create Gson object
    private val gson: Gson = GsonBuilder().setLenient().create()

    // Create retrofit Builder
    private val builder: Retrofit.Builder = Retrofit.Builder().baseUrl(URL)
        .addConverterFactory(ScalarsConverterFactory.create())  // for response other then json array i.e String
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttp.build())

    /*
    Refer above code if you want to receive response in multiple format like json array ,string etc
But if you  receive response only in json array format bellow code is enough
    private val builder: Retrofit.Builder = Retrofit.Builder().baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttp.build())

        //you don't need bellow line
      -> private val gson: Gson = GsonBuilder().setLenient().create()

     */

    // Create retrofit
    private val retrofit: Retrofit = builder.build()

    fun <T> buildService(serviceType: Class<T>): T {
        return retrofit.create(serviceType)
    }

}