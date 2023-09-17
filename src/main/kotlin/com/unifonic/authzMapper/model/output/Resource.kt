package com.unifonic.authzMapper.model.output

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import java.util.*


@JsonPropertyOrder(
    "name",
    "ownerManagedAccess",
    "displayName",
    "attributes",
    "_id",
    "uris",
    "scopes",
)
class Resource(
    @JsonProperty("name") val name: String,

    @JsonProperty("scopes") val scopes: SortedSet<Scope>,
) {
    @JsonProperty("_id")
    fun getUUID(): UUID = UUID.randomUUID()

    @JsonProperty("displayName")
    fun getDisplayName() = ""

    @JsonProperty("ownerManagedAccess")
    fun getOMA() = false

    @JsonProperty("attributes")
    fun getAttributes() = object

    @JsonProperty("uris")
    fun getURIs() = emptyArray<String>()


    @JsonPropertyOrder(
        "name",
    )
    class Scope(
        @JsonProperty("name") val name: String,
    ) : Comparable<Scope> {
        override fun compareTo(other: Scope): Int {
            return name.compareTo(other.name)
        }
    }
}
