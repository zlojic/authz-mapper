package com.unifonic.authzMapper.creator

import com.unifonic.authzMapper.model.input.LegacyJson
import com.unifonic.authzMapper.model.output.Scope


fun createScopes(legacyPermissions: Set<LegacyJson>): Collection<Scope> {
    val scopeCache = HashMap<String, Scope>()

    for (permission in legacyPermissions) {
        val scope = scopeCache[permission.scope] ?: Scope(permission.scope)
        scopeCache[permission.scope] = scope
    }

    return scopeCache.values.sortedBy { it.name }
}
