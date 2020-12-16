package com.savenews.model

import com.beust.klaxon.Json

data class Appearance (
	val gender: String,
	val race: String,
	val height: List<String>,
	val weight: List<String>,

	@Json(name = "eye-color")
	val eyeColor: String,

	@Json(name = "hair-color")
	val hairColor: String
)