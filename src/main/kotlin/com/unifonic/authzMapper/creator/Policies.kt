package com.unifonic.authzMapper.creator

import com.unifonic.authzMapper.model.input.LegacyJson
import com.unifonic.authzMapper.model.output.RolePolicy
import com.unifonic.authzMapper.model.output.ScopePolicy


fun createPolicies(legacyPermissions: Set<LegacyJson>): Set<Collection<Any>> {
    return setOf(
        createRolePolicies(legacyPermissions), createScopePolicies(legacyPermissions)
    )
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
        val policy = policyCache[policyName]?.run { appendApplyPolicy(this, permission.role) } ?: run { initApplyPolicy(permission.role) }
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
