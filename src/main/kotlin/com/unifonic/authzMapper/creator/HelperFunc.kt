package com.unifonic.authzMapper.creator


fun genRoles(role: String) = "[{\"id\":\"$role\",\"required\":true}]"

fun genResources(resource: String) = "[\"$resource\"]"

fun genScopes(scope: String) = "[\"$scope\"]"

fun genApplyPolicies(policies: String) = "[$policies]"

fun initApplyPolicy(role: String) = "\"$role\""

fun appendApplyPolicy(policies: String, role: String) = "$policies,\"$role\""
