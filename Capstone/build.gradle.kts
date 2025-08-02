import java.util.Properties
import java.io.FileInputStream

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
}

// Load secrets.properties file
val secretsPropertiesFile = rootProject.file("secrets.properties")
val secretProperties = Properties()

if (secretsPropertiesFile.exists()) {
    secretProperties.load(secretsPropertiesFile.inputStream())
} else {
    // Create default properties if file doesn't exist
    secretProperties["API_KEY"] = "your_default_api_key_here"
}

// Make secretProperties available to all modules
extra["secretProperties"] = secretProperties