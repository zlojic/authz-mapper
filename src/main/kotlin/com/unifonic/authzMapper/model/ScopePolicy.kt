package com.unifonic.authzMapper.model


import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import java.util.*


@JsonPropertyOrder(
    "id",
    "name",
    "type",
    "logic",
    "decisionStrategy",
    "config",
)
class ScopePolicy(
    @JsonProperty("name") val name: String,

    @JsonProperty("config") val config: Config,
) {
    @JsonProperty("id")
    fun getUUID(): UUID = UUID.randomUUID()

    @JsonProperty("type")
    fun getType() = "scope"

    @JsonProperty("logic")
    fun getLogic() = "POSITIVE"

    @JsonProperty("decisionStrategy")
    fun getDecisionStrategy() = "AFFIRMATIVE"


    @JsonPropertyOrder(
        "resources",
        "scopes",
        "applyPolicies",
    )
    class Config(
        @JsonProperty("resources") val resources: String,
        @JsonProperty("scopes") val scopes: String,
        @JsonProperty("applyPolicies") val applyPolicies: String,
    )
}
