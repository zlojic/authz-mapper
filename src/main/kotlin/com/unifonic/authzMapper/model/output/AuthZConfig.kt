package com.unifonic.authzMapper.model.output

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder


@JsonPropertyOrder(
    "allowRemoteResourceManagement",
    "policyEnforcementMode",
    "resources",
    "policies",
    "scopes",
    "decisionStrategy",
)
class AuthZConfig(
    @JsonProperty("resources") val resources: Collection<Resource>,
    @JsonProperty("policies") val policies: Collection<Any>,
    @JsonProperty("scopes") val scopes: Collection<Scope>,
) {
    @JsonProperty("allowRemoteResourceManagement")
    fun getAllowRRM() = true

    @JsonProperty("policyEnforcementMode")
    fun getPEM() = "ENFORCING"

    @JsonProperty("decisionStrategy")
    fun getDS() = "UNANIMOUS"
}
