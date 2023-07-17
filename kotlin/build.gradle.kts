@file:Suppress("UnstableApiUsage")

import kotlinx.benchmark.gradle.*
import org.jetbrains.kotlin.allopen.gradle.*
import org.jetbrains.kotlin.gradle.tasks.*

plugins {
    kotlin("jvm") version "1.8.21"
    kotlin("plugin.allopen") version "1.8.21"
    id("org.jetbrains.kotlinx.benchmark") version "0.4.8"
    id("me.champeau.jmh") version "0.6.8"
    application
}

configure<AllOpenExtension> {
    annotation("org.openjdk.jmh.annotations.State")
}


group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.aerospike:aerospike-client:5.0.0")
    implementation("io.github.oshai:kotlin-logging-jvm:4.0.0")
    implementation("org.slf4j:slf4j-simple:2.0.3")
    implementation("org.jetbrains.kotlinx:kotlinx-benchmark-runtime:0.4.8")
    annotationProcessor("org.openjdk.jmh:jmh-generator-annprocess:1.35")
    testImplementation(kotlin("test"))
    testImplementation("io.kotest:kotest-property:5.6.2")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("MainKt")
}

benchmark {
    configurations {
        named("main") {
            iterationTime = 5
            iterationTimeUnit = "sec"

        }
    }
    targets {
        register("main") {
            this as JvmBenchmarkTarget
            jmhVersion = "1.21"
        }
    }
}