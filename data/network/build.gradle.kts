plugins {
    alias(libs.plugins.valorant.android.library)
    alias(libs.plugins.valorant.android.library.jacoco)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.valorant.android.hilt)
}

android {
    namespace = "com.example.valorant.data.network"
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(projects.domain)
    implementation(projects.data.datastore)

    implementation(libs.kotlinx.serialization.json)

    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.json)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.logging.jvm)
    implementation(libs.ktor.client.serialization)
    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.apache)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.slf4j.simple)

    testImplementation(libs.junit)
}