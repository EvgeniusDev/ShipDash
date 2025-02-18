plugins {
    java
}

group = "com.litesoft.shipdash"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven { url = uri("https://jogamp.org/deployment/maven") }
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.processing:core:4.3.2") // Проверьте совместимость версий!
    implementation("org.jogamp.gluegen:gluegen-rt:2.5.0")
    implementation("org.jogamp.jogl:jogl-all:2.5.0")
}

sourceSets {
    main {
        java { srcDirs("src/main/java") }
        resources { srcDirs("src/main/resources") }
    }
}

tasks.processResources {
    from("src/main/resources/natives") // Копировать нативные библиотеки
}

tasks.withType<JavaExec> {
    systemProperty("java.library.path", "src/main/resources/natives/windows-amd64")
}

tasks.test {
    useJUnitPlatform()
}