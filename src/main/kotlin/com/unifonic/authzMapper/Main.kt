package com.unifonic.authzMapper


import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.unifonic.authzMapper.model.*
import java.io.FileInputStream
import java.io.FileOutputStream


fun main(args: Array<String>) {
    if (args.size != 1)
        return println("ERROR: Expecting input file name as command-line argument")

    val inputJson = FileInputStream(args[0])

    val jacksonMapper: ObjectMapper = ObjectMapper()
        .setSerializationInclusion(NON_NULL)
        .enable(SerializationFeature.INDENT_OUTPUT)

    val legacyPermissions: Set<LegacyJson> = jacksonMapper.readValue(
        inputJson, object : TypeReference<Set<LegacyJson>>() {})

    val resources = createResources(legacyPermissions)
    val scopes = createScopes(legacyPermissions)
    val policies = createPolicies(legacyPermissions)
    val authZConfig = AuthZConfig(resources, policies, scopes)

    jacksonMapper.writeValue(FileOutputStream("Resources.json"), resources)
    jacksonMapper.writeValue(FileOutputStream("Scopes.json"), scopes)
    jacksonMapper.writeValue(FileOutputStream("Policies.json"), policies)
    jacksonMapper.writeValue(FileOutputStream("AuthZ-Config.json"), authZConfig)
}

fun createScopes(legacyPermissions: Set<LegacyJson>): Collection<Scope> {
    val scopeCache = HashMap<String, Scope>()
    for (permission in legacyPermissions) {
        val scope = (scopeCache[permission.scope]
            ?: Scope(permission.scope))

        scopeCache[permission.scope] = scope
    }

    return scopeCache.values.sortedBy { it.name }
}

fun createResources(legacyPermissions: Set<LegacyJson>): Collection<Resource> {
    val resourceCache = HashMap<String, Resource>()

    for (permission in legacyPermissions) {
        val scope = Resource.Scope(permission.scope)

        val resource = resourceCache[permission.resource]
            ?: Resource(permission.resource, sortedSetOf())

        resource.scopes.add(scope)
        resourceCache[permission.resource] = resource
    }

    return resourceCache.values.sortedBy { it.name }
}

fun createPolicies(legacyPermissions: Set<LegacyJson>): Set<Collection<Any>> {
    return setOf(
        createRolePolicies(legacyPermissions),
        createScopePolicies(legacyPermissions)
    )
}

fun createRolePolicies(legacyPermissions: Set<LegacyJson>): Collection<RolePolicy> {
    val roleCache = HashMap<String, RolePolicy>()

    for (permission in legacyPermissions) {
        val config = RolePolicy.Config(genRoles(permission.role))

        val role = roleCache[permission.role]
            ?: RolePolicy(permission.role, config)

        roleCache[permission.role] = role
    }

    return roleCache.values.sortedBy { it.name }
}

fun createScopePolicies(legacyPermissions: Set<LegacyJson>): Collection<ScopePolicy> {
    val policyCache = HashMap<String, String>()

    for (permission in legacyPermissions) {
        val policyName = "${permission.resource} - ${permission.scope}"
        val policy = policyCache[policyName]
            ?. run { appendApplyPolicy(this, permission.role) }
            ?: run { initApplyPolicy(permission.role) }

        policyCache[policyName] = policy
    }

    return policyCache.map {
        val (resource, scope) = it.key.split(" - ")

        val config = ScopePolicy.Config(
            genResources(resource),
            genScopes(scope),
            genApplyPolicies(it.value)
        )

        ScopePolicy(it.key, config)
    }.sortedBy { it.name }
}
