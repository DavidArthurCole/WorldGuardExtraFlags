plugins {
    id("java")
    id("com.gradleup.shadow") version "9.0.0-beta4"
    id("xyz.jpenilla.run-paper") version "2.3.0"
    id("net.kyori.indra.git") version "3.1.3"
    id("io.papermc.paperweight.userdev") version "1.7.2"
}

group = "io.github.invvk"
version = parent?.version ?: "1.0.0"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.essentialsx.net/snapshots")
    maven("https://repo.extendedclip.com/releases/")
}

dependencies {
    compileOnly(paperweight.paperDevBundle("1.21.1-R0.1-SNAPSHOT"))

    compileOnly("net.essentialsx:EssentialsX:2.21.0-SNAPSHOT")
    compileOnly("me.clip:placeholderapi:2.11.6")

    implementation(project(":WGEF-Abstraction"))
    implementation(project(":WG7"))
}

tasks.shadowJar {
    archiveBaseName.set("WGEF-REBORN")
    archiveClassifier.set("")
    archiveVersion.set("")

    destinationDirectory.set(file("C:\\Users\\david\\Desktop\\plugins"))

    exclude("META-INF/**")
    exclude("LICENSE")

    relocate("org.bstats", "io.github.davidarthurcole.wgef.metrics")

    finalizedBy("clean", ":WG7:clean", ":WGEF-Abstraction:clean")
}

tasks.processResources {
    filesMatching("paper-plugin.yml") {
        expand(
                "version" to "${parent?.version}+${indraGit.commit()?.abbreviate(7)?.name() ?: "local"}"
        )
    }
}
