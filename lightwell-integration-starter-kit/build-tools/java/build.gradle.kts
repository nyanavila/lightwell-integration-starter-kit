// Gradle Kotlin DSL - Red Hat Lightwell Network Repository Integration
repositories {
    mavenCentral()

    // Lightwell Remediated Repository (Surgical drop-in patches)
    maven {
        name = "RedHatLightwellRemediated"
        url = uri("https://packages.example.com/lightwell/maven/remediated/")
        credentials {
            username = System.getenv("LIGHTWELL_SERVICE_ACCOUNT_ID") ?: ""
            password = System.getenv("LIGHTWELL_REGISTRY_TOKEN") ?: ""
        }
    }

    // Lightwell Validated Repository (Zero-CVE upstream packages)
    maven {
        name = "RedHatLightwellValidated"
        url = uri("https://packages.example.com/lightwell/maven/validated/")
        credentials {
            username = System.getenv("LIGHTWELL_SERVICE_ACCOUNT_ID") ?: ""
            password = System.getenv("LIGHTWELL_REGISTRY_TOKEN") ?: ""
        }
    }
}
