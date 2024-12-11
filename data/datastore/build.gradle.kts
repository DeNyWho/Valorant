plugins {
    alias(libs.plugins.valorant.android.library)
    alias(libs.plugins.valorant.android.library.jacoco)
    alias(libs.plugins.valorant.android.hilt)
}

android {
    namespace = "com.example.valorant.data.datastore"
}

dependencies {
    implementation(projects.domain)
    implementation(libs.kotlinx.serialization.protobuf)
    implementation(libs.androidx.dataStore.core)
    implementation(libs.androidx.dataStore.preferences)
    testImplementation(libs.junit)
}