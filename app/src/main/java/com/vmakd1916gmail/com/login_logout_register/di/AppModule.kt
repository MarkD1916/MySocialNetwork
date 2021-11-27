package com.vmakd1916gmail.com.login_logout_register.di

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.vmakd1916gmail.com.login_logout_register.DB.MySocialNetworkDAO
import com.vmakd1916gmail.com.login_logout_register.DB.MySocialNetworkDatabase
import com.vmakd1916gmail.com.login_logout_register.R
import com.vmakd1916gmail.com.login_logout_register.repositories.auth.AuthRepositoryImpl
import com.vmakd1916gmail.com.login_logout_register.api.AuthApi
import com.vmakd1916gmail.com.login_logout_register.other.RequestTokenInterceptor
import com.vmakd1916gmail.com.login_logout_register.other.TokenPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val DATABASE_NAME = "social_network_database"

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplicationContext(
        @ApplicationContext context: Context
    ) = context

    @Singleton
    @Provides
    fun providePicassoInstance(
        @ApplicationContext context: Context
    ) = Glide.with(context).setDefaultRequestOptions(
        RequestOptions()
            .placeholder(R.drawable.ic_round_hourglass_bottom_24)
            .error(R.drawable.ic_baseline_error_outline_24)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
    )

    @Singleton
    @Provides
    fun provideTokenPreferences(@ApplicationContext context: Context) =
        TokenPreferences(context)

    @Provides
    fun providesBaseUrl(): String = "https://vmakd1916mdsocialnetworkserver.herokuapp.com"

    @Provides
    @Singleton
    fun provideRequestInterceptor(tokenPreferences: TokenPreferences): RequestTokenInterceptor =
        RequestTokenInterceptor(tokenPreferences)

    @Provides
    fun provideOkHttpClient(requestTokenInterceptor: RequestTokenInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(requestTokenInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(BASE_URL: String, client: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(client)
        .build()

    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthApi =
        retrofit.create(AuthApi::class.java)

    @Provides
    @Singleton
    fun provideAuthRepository(
        authApi: AuthApi
    ): AuthRepositoryImpl = AuthRepositoryImpl(authApi)


    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): MySocialNetworkDatabase {
        return Room.databaseBuilder(
            appContext,
            MySocialNetworkDatabase::class.java,
            DATABASE_NAME
        ).build()
    }


    @InstallIn(SingletonComponent::class)
    @Module
    class DatabaseModule {
        @Provides
        fun provideMySocialNetworkDao(appDatabase: MySocialNetworkDatabase): MySocialNetworkDAO {
            return appDatabase.mySocialNetworkDAO()
        }
    }


}