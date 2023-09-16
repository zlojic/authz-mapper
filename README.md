## AuthZMapper

Convert simple JSON permissions into Keycloak readable format

#### Building the project

```
# ./build-ninja.sh
```

#### Running the project

```
# ./authz-mapper.run example-input.json
```

#### Input file format

To ensure successful deserialization of input JSON file, the JSON should have the following structure:

```
[
  {
    "actionName": "UPDATE",
    "resourceName": "CUSTOMER",
    "roleName": "SUPPORT"
  },
  {
    "actionName": "VIEW",
    "resourceName": "PRODUCT",
    "roleName": "MANAGER"
  }
]
```

Each object in the JSON array should contain the three key-value pairs, where keys are following:

+ `actionName` ( `action`, `scope` )
+ `resourceName` ( `resource` )
+ `roleName` ( `role` )

> Aliases are listed in parentheses. They can also be used as a substitute for the standard one
