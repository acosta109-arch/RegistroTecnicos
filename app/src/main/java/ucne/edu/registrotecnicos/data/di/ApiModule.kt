package ucne.edu.registrotecnicos.data.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ucne.edu.registrotecnicos.data.remote.ArticuloManagerApi
import ucne.edu.registrotecnicos.data.remote.sistemas.SistemaManagerApi
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ApiModule {
    private const val BASE_URL_ARTICULOS = "https://apitarea3.azurewebsites.net/"
    private const val BASE_URL_TICKETS = "https://sistematicket.azurewebsites.net/"

    @Provides
    @Singleton
    fun providesMoshi(): Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    @Provides
    @Singleton
    fun providesArticuloManagerApi(moshi: Moshi): ArticuloManagerApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_ARTICULOS)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(ArticuloManagerApi::class.java)
    }

    @Provides
    @Singleton
    fun providesSistemaTicketApi(moshi: Moshi): SistemaManagerApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_TICKETS)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(SistemaManagerApi::class.java)
    }
}
