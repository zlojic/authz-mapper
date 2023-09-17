package com.unifonic.authzMapper

import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.unifonic.authzMapper.creator.createPolicies
import com.unifonic.authzMapper.creator.createResources
import com.unifonic.authzMapper.creator.createScopes
import com.unifonic.authzMapper.model.input.LegacyJson
import com.unifonic.authzMapper.model.output.AuthZConfig
import java.io.FileInputStream
import java.io.FileOutputStream


fun main(args: Array<String>) {
    if (args.isEmpty()) return println("ERROR: Expecting input file name as command-line argument")

    val jackson = ObjectMapper()
    jackson.setSerializationInclusion(NON_NULL)
    jackson.enable(SerializationFeature.INDENT_OUTPUT)

    val inputJson = FileInputStream(args[0])
    val typeRef = object : TypeReference<Set<LegacyJson>>() {}
    val legacyPermissions: Set<LegacyJson> = jackson.readValue(inputJson, typeRef)

    val resources = createResources(legacyPermissions)
    val scopes = createScopes(legacyPermissions)
    val policies = createPolicies(legacyPermissions)
    val authZConfig = AuthZConfig(resources, policies, scopes)

    jackson.writeValue(FileOutputStream("Resources.json"), resources)
    jackson.writeValue(FileOutputStream("Scopes.json"), scopes)
    jackson.writeValue(FileOutputStream("Policies.json"), policies)
    jackson.writeValue(FileOutputStream("AuthZ-Config.json"), authZConfig)
}
