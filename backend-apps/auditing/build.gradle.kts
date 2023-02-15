group = "cz.sentica.qwazar.backend-apps"

plugins {
    id("java-library")
    id("java-test-fixtures")

    id("io.ebean")

    kotlin("kapt")
    kotlin("plugin.spring")
}

dependencies {
    api("io.ebean:ebean")

    // TODO: get rid of spring completely
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")

    testImplementation("io.ebean:ebean-test")
    testImplementation("io.kotest:kotest-assertions-core")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    testRuntimeOnly("com.h2database:h2")

    kapt("io.ebean:kotlin-querybean-generator")
    kaptTest("io.ebean:kotlin-querybean-generator")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
}

tasks.jar {
    archiveBaseName.set("backend-apps-auditing")
}

tasks.bootJar {
    enabled = false
}
