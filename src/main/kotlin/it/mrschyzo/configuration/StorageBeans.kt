package it.mrschyzo.configuration

import io.quarkus.arc.DefaultBean
import it.mrschyzo.utils.types.PosLong
import java.nio.file.Path
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Produces
import javax.inject.Inject

/***
 * This configuration class exists only to work around this issue: https://github.com/MrSchyzo/tts-rest-wrapper/issues/1#issue-1021557908
 */
@ApplicationScoped
class StorageBeans(
    @Inject val storageConfiguration: RawStorageConfiguration,
) {
    @Produces
    @DefaultBean
    fun storageConfig(): StorageConfiguration {
        return object : StorageConfiguration {
            override fun root(): Path =
                StringToPathConverter().convert(storageConfiguration.root())

            override fun depth(): PosLong =
                PositiveLongCoercionConverter().convert(storageConfiguration.depth().toString())
        }
    }
}
