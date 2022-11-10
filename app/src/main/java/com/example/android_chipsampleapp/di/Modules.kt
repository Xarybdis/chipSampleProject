package com.example.android_chipsampleapp.di

import com.example.android_chipsampleapp.network.Api
import com.example.android_chipsampleapp.network.Repository
import com.example.android_chipsampleapp.ui.breed_detail.BreedDetailViewModel
import com.example.android_chipsampleapp.ui.breed_list.BreedListViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://dog.ceo/api/"

/**
 * Build network module for requests.
 */
val networkModule = module {
    single(named("BASE_URL")) { BASE_URL }

    single {
        val interceptor = HttpLoggingInterceptor()
        interceptor.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    /**
     * Build httpclient
     */
    single {
        val client = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)

        //if (BuildConfig.DEBUG) {
            client.addInterceptor(get<HttpLoggingInterceptor>())
        //}
        client.build()
    }

    /**
     * Build Retrofit.
     */
    single {
        Retrofit.Builder()
            .baseUrl(get<String>(named("BASE_URL")))
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }

    single { get<Retrofit>().create(Api::class.java) }
}

val viewModelModule = module {
    single { Repository(get()) }
    viewModel { BreedListViewModel(get()) }
    viewModel { BreedDetailViewModel(get(), get()) }
}
