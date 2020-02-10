package hu.pe.warwind.cashHolder

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface DOLApiClient {

    /* Get token */
    @Headers("Content-Type: application/json")
    @POST("api.php")
    fun getToken(@Body data: LoginRequest): Observable<LoginResponse>

    companion object {

        fun create(): DOLApiClient {

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://dol.warwind.pe.hu/")
                .build()

            return retrofit.create(DOLApiClient::class.java)

        }
    }
}