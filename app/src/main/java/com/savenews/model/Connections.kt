package com.savenews.model

import com.beust.klaxon.Json

data class Connections (
	@Json(name = "group-affiliation")
	val groupAffiliation: String,

	val relatives: String
)
