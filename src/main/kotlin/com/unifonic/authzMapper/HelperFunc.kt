package com.unifonic.authzMapper


fun genRoles(role: String) = "[{\"id\":\"$role\",\"required\":true}]"

fun genResources(resource: String) = "[\"$resource\"]"

fun genScopes(scope: String) = "[\"$scope\"]"

fun genApplyPolicies(policy: String) = "[$policy]"

fun initApplyPolicy(role: String) = "\"$role\""

fun appendApplyPolicy(policies: String, role: String) = "$policies,\"$role\""
