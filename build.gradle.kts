plugins {
    kotlin("jvm") version "1.4.30"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("io.reactivex:rxjava:1.3.8")
    implementation("io.reactivex:rxnetty-http:0.5.3")
    implementation("io.reactivex:rxnetty-common:0.5.3")
    implementation("io.netty:netty-buffer:4.1.5.Final")
    implementation("io.netty:netty-codec-http:4.1.5.Final")
    implementation("org.reflections:reflections:0.9.12")
    implementation("org.mongodb:mongodb-driver-rx:1.5.0")
}

sourceSets {
    main {
        java.srcDir("main/kotlin")
    }

    test {
        java.srcDir("test/kotlin")
    }
}

tasks {
    test {
        useJUnitPlatform()
    }
}

configure<ApplicationPluginConvention> {
    mainClassName = "by.knisht.http.MainKt"
}
