import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jlleitschuh.gradle.ktlint.KtlintExtension
import org.jlleitschuh.gradle.ktlint.tasks.BaseKtLintCheckTask

group = "cz.sentica.qwazar"

plugins {
    val kotlinVersion = "1.7.21"

    id("java")
    id("io.ebean") version "13.11.3" apply(false)
    id("org.springframework.boot") version "2.7.6" apply(false)
    id("io.spring.dependency-management") version "1.1.0"
    id("org.jlleitschuh.gradle.ktlint") version "11.0.0" apply(false)
    id("com.adarshr.test-logger") version "3.2.0" apply(false)

    kotlin("jvm") version kotlinVersion apply(false)
    kotlin("kapt") version kotlinVersion apply(false)
    kotlin("plugin.noarg") version kotlinVersion apply(false)
    kotlin("plugin.spring") version kotlinVersion apply(false)
}

repositories {
    mavenCentral()
}

subprojects {
    version = "2.110.0-SNAPSHOT"

    if (name != "frontend") {
        apply(plugin = "java")
        apply(plugin = "kotlin")
        apply(plugin = "com.adarshr.test-logger")
        apply(plugin = "org.jlleitschuh.gradle.ktlint")
        apply(plugin = "io.spring.dependency-management")

        if (parent?.name != "qwazar") {
            apply(plugin = "org.springframework.boot")
        }

        java.sourceCompatibility = JavaVersion.VERSION_17

        repositories {
            mavenCentral()
        }

        dependencyManagement {
            imports {
                mavenBom("org.jetbrains.kotlinx:kotlinx-coroutines-bom:1.6.4")
                mavenBom("io.ebean:ebean-bom:13.11.3")
                mavenBom("io.kotest:kotest-bom:5.5.4")
                mavenBom("org.mockito:mockito-bom:4.11.0")
                mavenBom("net.bytebuddy:byte-buddy-parent:1.12.19")
            }

            dependencies {
                dependency("com.google.guava:guava:31.1-jre")
                dependency("com.github.doyaaaaaken:kotlin-csv-jvm:1.6.0")

                dependency("org.overviewproject:mime-types:1.0.4")
                // TODO: not maintained any more, try to find an alternative
                dependency("com.github.ulisesbocchio:spring-boot-jar-resources:1.3")

                dependency("org.postgresql:postgresql:42.3.4")
                dependency("com.oracle.jdbc:ojdbc11:21.8.0.0")
                dependency("com.microsoft.sqlserver:mssql-jdbc:11.2.0.jre17")

                dependency("org.jsoup:jsoup:1.11.1")
                dependency("org.apache.poi:poi-ooxml:4.1.0")
                dependency("org.apache.poi:ooxml-schemas:1.4")
                dependency("org.wickedsource.docx-stamper:docx-stamper:1.4.0")
                dependency("org.docx4j:docx4j:6.1.2") {
                    exclude("org.slf4j:slf4j-log4j12")
                }

                dependency("io.kotest.extensions:kotest-extensions-spring:1.1.2")
                dependency("org.exparity:hamcrest-date:2.0.8")
                dependency("org.mockito.kotlin:mockito-kotlin:4.1.0")
                dependency("com.google.jimfs:jimfs:1.2")
            }
        }

        val kotlinVersion = "1.7.21"

        dependencies {
            implementation(kotlin("stdlib", kotlinVersion))
            implementation(kotlin("stdlib-jdk8", kotlinVersion))
            implementation(kotlin("reflect", kotlinVersion))
        }

        tasks.withType<Test> {
            useJUnitPlatform()

            // testLogging.showStandardStreams = true
            // testLogging.exceptionFormat = TestExceptionFormat.FULL

            // see https://github.com/jvm-profiling-tools/async-profiler/blob/master/src/arguments.cpp
            // val profilerPath = "/opt/async-profiler/build/libasyncProfiler.so"
            // val profileArgs = listOf(
            //     "-agentpath:$profilerPath=start",
            //     "event=wall",
            //     "file=profile.html",
            //     // "include=org/junit/jupiter/engine/descriptor/TestMethodTestDescriptor.invokeTestMethod",
            //     "include=org/junit/*",
            // ).joinToString(",")
            //
            // jvmArgs = jvmArgs!! + listOf(
            //     "-XX:+UnlockDiagnosticVMOptions",
            //     "-XX:+DebugNonSafepoints",
            //     profileArgs,
            // )
        }

        tasks.withType<KotlinCompile> {
            kotlinOptions {
                freeCompilerArgs = freeCompilerArgs + "-Xjsr305=strict"
                jvmTarget = "17"
            }
        }

        tasks.withType<BaseKtLintCheckTask> {
            workerMaxHeapSize.set("2048m")
        }

        configure<KtlintExtension> {
            version.set("0.45.2")
            // version.set("0.46.1") // nefunguje - unable to load KtLint$Params
        }
    }
}

// dependencies {
//     project(":qwazar-kb-backend")
//     project(":frontend")
// }
//
// tasks.register("build-kb") {
//     dependsOn(
//         project(":qwazar-kb-backend").tasks.assemble,
//         // project(":frontend").tasks.build,
//     )
//
//     println(project(":qwazar-kb-backend").artifacts)
//
//     // copy {
//     //     // from(project(":qwazar-kb-backend").layout.buildDirectory.dir("libs")) {
//     //     //     include("*.jar")
//     //     //     exclude("*-plain.*")
//     //     // }
//     //     // from(zipTree(project(":qwazar-kb-backend").layout.buildDirectory.dir("libs").get().file("qwazar-kb-backend-$version.jar")))
//     //     from(zipTree(project(":qwazar-kb-backend").artifacts.)
//     //     into(buildDir)
//     // }
//
//     // copy {
//     //     from(project(":frontend").buildDir)
//     //     into(zipTree())
//     // }
// }
