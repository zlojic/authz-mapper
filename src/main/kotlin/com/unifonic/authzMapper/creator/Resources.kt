package com.unifonic.authzMapper.creator

import com.unifonic.authzMapper.model.input.LegacyJson
import com.unifonic.authzMapper.model.output.Resource


fun createResources(legacyPermissions: Set<LegacyJson>): Collection<Resource> {
    val resourceCache = HashMap<String, Resource>()

    for (permission in legacyPermissions) {
        val scope = Resource.Scope(permission.scope)
        val resource = resourceCache[permission.resource] ?: Resource(permission.resource, sortedSetOf())
        resource.scopes.add(scope)
        resourceCache[permission.resource] = resource
    }

    return resourceCache.values.sortedBy { it.name }
}
