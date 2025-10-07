plugins {
    application
    java
    id("org.jetbrains.kotlin.jvm") version "1.7.20"
    id("com.cinnober.gradle.semver-git") version "2.5.0"
    id("com.rikonardo.papermake") version "1.0.6"
}

group = "org.tamasoft"

repositories {
    mavenCentral()
    maven {
        name = "papermc-repo"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
    maven {
        name = "codemc-repo"
        url = uri("https://repo.codemc.org/repository/maven-public/")
    }
    /*
    As Spigot-API depends on the BungeeCord ChatComponent-API,
    we need to add the Sonatype OSS repository, as Gradle,
    in comparison to Maven, doesn't want to understand the ~/.m2
    directory unless added using mavenLocal(). Maven usually just gets
    it from there, as most people have run the BuildTools at least once.
    This is therefore not needed if you're using the full Spigot/CraftBukkit,
    or if you're using the Bukkit API.
    */
    maven {
        name = "sonatype-repo"
        url = uri("https://oss.sonatype.org/content/groups/public/")
    }
}

val jacksonVersion = "2.13.4"
dependencies {
    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("de.tr7zw:item-nbt-api-plugin:2.10.0")
    implementation("com.fasterxml.jackson.core:jackson-core:${jacksonVersion}")
    implementation("com.fasterxml.jackson.core:jackson-databind:${jacksonVersion}")
    implementation("com.fasterxml.jackson.core:jackson-annotations:${jacksonVersion}")
    compileOnly("io.papermc.paper:paper-api:1.19.2-R0.1-SNAPSHOT")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.7.20")
}

val targetJavaVersion = 17
java {
    val javaVersion = JavaVersion.toVersion(targetJavaVersion)
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
    if (JavaVersion.current() < javaVersion) {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(targetJavaVersion))
        }
    }
}

tasks {
    withType<JavaCompile>().configureEach {
        if (targetJavaVersion >= 10 || JavaVersion.current().isJava10Compatible) {
            options.release.set(targetJavaVersion)
        }
    }
    withType<ProcessResources>().configureEach {
        val props = mapOf(version to version)
        @Suppress("UNCHECKED_CAST")
        inputs.properties(props as Map<String, *>)
        filteringCharset = "UTF-8"
        filesMatching("plugin.yml") {
            @Suppress("UNCHECKED_CAST")
            expand(props as Map<String, *>)
        }
    }
    withType<Jar> {
        // Otherwise you'll get a "No main manifest attribute" error
        manifest {
            attributes["Main-Class"] = "org.tamasoft.creativegate.CreativeGate"
        }

        // To add all of the dependencies
        from(sourceSets.main.get().output)

        duplicatesStrategy = DuplicatesStrategy.EXCLUDE

        dependsOn(configurations.runtimeClasspath)
        from(configurations.runtimeClasspath.get().map { if (it.isDirectory) { it } else { zipTree((it)) }})

    }

    test {
        useJUnitPlatform()
    }

    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
}