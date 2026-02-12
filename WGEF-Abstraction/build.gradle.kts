plugins {
    java
}

group = "io.github.davidarthurcole"
version = parent!!.version

repositories {
    mavenCentral()
    google()
    maven("https://jitpack.io")
    maven("https://repo.essentialsx.net/releases")
}

dependencies {
    compileOnly("net.essentialsx:EssentialsX:2.21.2")
    compileOnly("com.github.NEZNAMY:TAB-API:5.5.0")
}
