plugins {
    java
}

group = "io.github.davidarthurcole"
version = "2.11"

repositories {
    mavenCentral()
}

subprojects {
    apply(plugin = "java")

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(22))
        }
    }

    repositories {
        mavenCentral()
        google()
        maven("https://oss.sonatype.org/content/repositories/snapshots")
        maven("https://maven.enginehub.org/repo/")
        maven("https://repo.dmulloy2.net/repository/public/")
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") {
            name = "spigot-repo"
        }
        maven("https://jitpack.io")
    }

    dependencies {
        compileOnly("org.spigotmc:spigot-api:1.21.8-R0.1-SNAPSHOT")
        compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.14-SNAPSHOT")
        compileOnly("com.sk89q.worldedit:worldedit-core:7.3.16-SNAPSHOT")
        compileOnly("com.sk89q.worldedit:worldedit-bukkit:7.3.16-SNAPSHOT") {
            isTransitive = false
        }
    }

    configurations.configureEach {
        resolutionStrategy {
            force("com.google.guava:guava:33.3.1-jre")
            force("com.google.code.gson:gson:2.11.0")
            force("org.apache.logging.log4j:log4j-bom:2.24.1")
        }
    }
}
