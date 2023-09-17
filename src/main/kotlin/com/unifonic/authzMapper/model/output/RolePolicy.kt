package com.unifonic.authzMapper.model.output

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
class RolePolicy(
    @JsonProperty("name") val name: String,
    @JsonProperty("config") val config: Config,
) {
    @JsonProperty("id")
    fun getUUID(): UUID = UUID.randomUUID()

    @JsonProperty("type")
    fun getType() = "role"

    @JsonProperty("logic")
    fun getLogic() = "POSITIVE"

    @JsonProperty("decisionStrategy")
    fun getDecisionStrategy() = "UNANIMOUS"


    @JsonPropertyOrder(
        "roles",
    )
    class Config(
        @JsonProperty("roles") val roles: String,
    )
}
