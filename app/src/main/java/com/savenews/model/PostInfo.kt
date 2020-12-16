package com.savenews.model

import com.beust.klaxon.Klaxon

private val klaxon = Klaxon()

//data class PostInfo(val id:String,val medicinalproduct:String,val drugindication:String,val drugdosagetext:String)

data class PostInfo (
    val response: String,
    val characters: List<Characters>
) {
    public fun toJson() = klaxon.toJsonString(this)

    companion object {
        public fun fromJson(json: String) = klaxon.parse<PostInfo>(json)
    }
}