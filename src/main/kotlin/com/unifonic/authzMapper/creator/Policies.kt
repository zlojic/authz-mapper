package com.unifonic.authzMapper.creator

import com.unifonic.authzMapper.model.input.LegacyJson
import com.unifonic.authzMapper.model.output.RolePolicy
import com.unifonic.authzMapper.model.output.ScopePolicy


fun createPolicies(legacyPermissions: Set<LegacyJson>): Collection<Any> {
    val rolePolicies = createRolePolicies(legacyPermissions)
    val scopePolicies = createScopePolicies(legacyPermissions)

    return rolePolicies.plus(scopePolicies)
}

private fun createRolePolicies(legacyPermissions: Set<LegacyJson>): Collection<RolePolicy> {
    val roleCache = HashMap<String, RolePolicy>()

    for (permission in legacyPermissions) {
        val config = RolePolicy.Config(genRoles(permission.role))
        val role = roleCache[permission.role] ?: RolePolicy(permission.role, config)
        roleCache[permission.role] = role
    }

    return roleCache.values.sortedBy { it.name }
}

private fun createScopePolicies(legacyPermissions: Set<LegacyJson>): Collection<ScopePolicy> {
    val policyCache = HashMap<String, String>()

    for (permission in legacyPermissions) {
        val policyName = "${permission.resource} - ${permission.scope}"
        val policy = when (val it = policyCache[policyName]) {
            null -> initApplyPolicy(permission.role)
            else -> appendApplyPolicy(it, permission.role)
        }

        policyCache[policyName] = policy
    }

    return policyCache.map {
        val (resource, scope) = it.key.split(" - ")
        val config = ScopePolicy.Config(
            genResources(resource), genScopes(scope), genApplyPolicies(it.value)
        )

        ScopePolicy(it.key, config)
    }.sortedBy { it.name }
}
