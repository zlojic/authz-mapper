package com.unifonic.authzMapper.model.input

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty


data class LegacyJson @JsonCreator constructor(
    @JsonProperty("actionName") @JsonAlias("action", "scope") val scope: String,
    @JsonProperty("resourceName") @JsonAlias("resource") val resource: String,
    @JsonProperty("roleName") @JsonAlias("role") val role: String,
)
