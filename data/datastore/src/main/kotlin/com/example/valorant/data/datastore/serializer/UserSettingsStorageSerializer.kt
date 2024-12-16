package com.example.valorant.data.datastore.serializer

import com.example.valorant.domain.model.user.settings.UserSettings
import kotlinx.serialization.ExperimentalSerializationApi
import androidx.datastore.core.Serializer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.encodeToByteArray
import kotlinx.serialization.protobuf.ProtoBuf
import java.io.InputStream
import java.io.OutputStream

internal class UserSettingsStorageSerializer: Serializer<UserSettings> {

    /**
     * Value to return if there is no data on disk.
     */
    override val defaultValue: UserSettings
        get() = UserSettings()

    /**
     * Unmarshal object from stream.
     *
     * @param input the InputStream with the data to deserialize
     */
    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun readFrom(input: InputStream): UserSettings {
        return ProtoBuf.decodeFromByteArray(input.readBytes())
    }

    /**
     * Marshal object to a stream. Closing the provided OutputStream is a no-op.
     *
     * @param t the data to write to output
     * @output the OutputStream to serialize data to
     */
    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun writeTo(t: UserSettings, output: OutputStream) {
        withContext(Dispatchers.IO) {
            output.write(ProtoBuf.encodeToByteArray(t))
        }
    }
}