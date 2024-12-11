plugins {
    alias(libs.plugins.valorant.android.library)
    alias(libs.plugins.valorant.android.library.jacoco)
    alias(libs.plugins.valorant.android.hilt)
}

android {
    namespace = "com.example.valorant.data.source"
}

dependencies {
    implementation(projects.domain)
    implementation(projects.data.network)
    implementation(projects.data.datastore)
    implementation(projects.data.local)

    implementation(libs.paging.runtime)
    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.junit)
}