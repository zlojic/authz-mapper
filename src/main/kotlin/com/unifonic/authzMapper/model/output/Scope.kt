package com.unifonic.authzMapper.model.output

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import java.util.*


@JsonPropertyOrder(
    "id",
    "name",
)
class Scope(
    @JsonProperty("name") val name: String,
) {
    @JsonProperty("id")
    fun getUUID(): UUID = UUID.randomUUID()
}
