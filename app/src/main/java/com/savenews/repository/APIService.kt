package com.savenews.repository

import com.savenews.model.PostInfo
import retrofit2.http.GET
import retrofit2.http.Path
import io.reactivex.Observable

interface APIService {

    @GET("results/{id}")
    fun makeRxRequest(@Path("id") id: Int?): Observable<PostInfo>

    @GET("results")
    fun makeRxListRequest(): Observable<List<PostInfo>>

    @GET("SampleResponse.json")
    fun makeRxOneRequest(): Observable<PostInfo>
}