package com.savenews.model

import com.beust.klaxon.Json

data class Biography (
	@Json(name = "full-name")
	val fullName: String,

	@Json(name = "alter-egos")
	val alterEgos: String,

	val aliases: List<String>,

	@Json(name = "place-of-birth")
	val placeOfBirth: String,

	@Json(name = "first-appearance")
	val firstAppearance: String,

	val publisher: String,
	val alignment: String
)
