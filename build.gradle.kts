plugins {
    id("java")
    id("io.qameta.allure") version "3.0.0"

}

group = "app"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.3"))
    testImplementation("org.junit.jupiter:junit-jupiter")


    testImplementation("io.rest-assured:rest-assured:5.4.0")
    testImplementation("io.rest-assured:json-path:5.4.0")
    testImplementation("io.rest-assured:json-schema-validator:5.4.0")

    testImplementation("io.qameta.allure:allure-rest-assured:2.29.1")

    testImplementation("com.microsoft.playwright:playwright:1.50.0")

    testImplementation("org.aeonbits.owner:owner:1.0.12")

    testImplementation("com.deque.html.axe-core:playwright:4.10.2")

    testImplementation("io.qameta.allure:allure-okhttp3:2.29.1")

    testImplementation("com.github.tomakehurst:wiremock-jre8:2.35.0")

}

allure {
    version.set("2.34.1")
    adapter {
        frameworks { junit5 {} }
        autoconfigure.set(true)
        autoconfigureListeners.set(true)
        aspectjWeaver.set(true)
        allureJavaVersion.set("2.29.1")
        aspectjVersion.set("1.9.22.1")
    }

    report {
        reportDir.set(project.reporting.baseDirectory.dir("allure-report"))


        singleFile.set(true)
    }
}


tasks.test {
    useJUnitPlatform()

    ignoreFailures = true

    finalizedBy("allureReport")
}