plugins {
    kotlin("jvm") version "1.9.+"
    id("com.github.johnrengelman.shadow") version "8.+"
}

group = "com.unifonic"
version = "0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.fasterxml.jackson.core:jackson-databind:2.+")
    testImplementation(kotlin("test"))
}

tasks {
    test {
        useJUnitPlatform()
    }

    jar {
        manifest {
            attributes["Main-Class"] = "com.unifonic.authzMapper.MainKt"
        }
    }
}

kotlin {
    jvmToolchain(17)
}
