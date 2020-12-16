package com.savenews.model

import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.converter.PropertyConverter

@Entity
data class Characters(
	@Id(assignable = true) var id: Long = 0,
	val name: String,

	@Transient val powerstats: Powerstats?,
	@Transient val biography: Biography?,
	@Transient val appearance: Appearance?,
	@Transient val work: Work?,
	@Transient val connections: Connections?,

	@Convert(converter = ImageConverter::class, dbType = String::class)
	val image: Image?
)
class ImageConverter : PropertyConverter<Image, String> {
	override fun convertToEntityProperty(databaseValue: String): Image {
		return Image(databaseValue)
	}

	override fun convertToDatabaseValue(entityProperty: Image): String {
		return entityProperty.url
	}
}