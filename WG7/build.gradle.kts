plugins {
    java
}

group = "io.github.davidarthurcole"
version = parent!!.version

repositories {
    mavenCentral()
    google()
    maven("https://jitpack.io")
}

dependencies {
    implementation(project(":WGEF-Abstraction"))
}
