pluginManagement {
    plugins {
        kotlin("jvm") version "2.1.20"
    }
}
rootProject.name = "commerce-demo"

include("common")
include("core:core-domain")
include("core:core-api")
include("core:core-application")
include("infrastructure:db-core")
include("external:exchange")
