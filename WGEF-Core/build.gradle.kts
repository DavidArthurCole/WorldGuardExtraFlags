plugins {
    id("java")
    id("com.gradleup.shadow") version "9.0.0-beta4"
    id("xyz.jpenilla.run-paper") version "2.3.0"
    id("net.kyori.indra.git") version "3.1.3"
}

group = "io.github.invvk"
version = parent?.version ?: "1.0.0"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.essentialsx.net/releases")
    maven("https://repo.extendedclip.com/releases/")
    maven("https://repo.helpch.at/releases")
}

dependencies {
    // https://mvnrepository.com/artifact/net.essentialsx/EssentialsX
    compileOnly("net.essentialsx:EssentialsX:2.21.2")
    // https://repo.helpch.at/#/releases/me/clip/placeholderapi
    compileOnly("me.clip:placeholderapi:2.11.7")

    implementation(project(":WGEF-Abstraction"))
    implementation(project(":WG7"))
    implementation("org.bstats:bstats-bukkit:3.1.0")
}

tasks.shadowJar {
    archiveBaseName.set("WGEF-REBORN")
    val mcVer = "1.21.11"
    archiveVersion.set("${project.version}" + mcVer.takeIf { it.isNotEmpty() }?.let { "-$it" }.orEmpty())
    archiveClassifier.set("")

    destinationDirectory.set(file("C:\\Users\\david\\Desktop\\plugins"))

    exclude("META-INF/**")
    exclude("LICENSE")

    relocate("org.bstats", "io.github.davidarthurcole.wgef.metrics")

    finalizedBy("clean", ":WG7:clean", ":WGEF-Abstraction:clean")
}

tasks.processResources {
    filesMatching("paper-plugin.yml") {
        expand("version" to project.version)
    }
}
