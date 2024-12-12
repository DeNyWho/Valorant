plugins {
    alias(libs.plugins.valorant.android.library)
    alias(libs.plugins.valorant.android.library.jacoco)
    alias(libs.plugins.valorant.android.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.valorant.domain"
}

dependencies {
    implementation(libs.paging.runtime)
    implementation(libs.kotlinx.serialization.json)
}