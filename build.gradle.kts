import net.labymod.labygradle.common.extension.model.labymod.ReleaseChannels

plugins {
    id("net.labymod.labygradle")
    id("net.labymod.labygradle.addon")
}

val versions = providers.gradleProperty("net.labymod.minecraft-versions").get().split(";")

group = "de.corvonn"
version = providers.environmentVariable("VERSION").getOrElse("1.0.2")

labyMod {
    defaultPackageName = "de.corvonn.labyklickmichaddon" //change this to your main package name (used by all modules)

    minecraft {
        registerVersion(versions.toTypedArray()) {
            runs {
                getByName("client") {
                    // When the property is set to true, you can log in with a Minecraft account
                    devLogin = true
                }
            }
        }
    }

    addonInfo {
        namespace = "labyklickmichaddon"
        displayName = "KlickMich.Net Serversupport"
        author = "Corvonn"
        description = "Adds support for the Minecraft network klickmich.net"
        minecraftVersion = "*"
        version = rootProject.version.toString()
        releaseChannel = ReleaseChannels.PRODUCTION
    }
}

subprojects {
    plugins.apply("net.labymod.labygradle")
    plugins.apply("net.labymod.labygradle.addon")

    group = rootProject.group
    version = rootProject.version
}