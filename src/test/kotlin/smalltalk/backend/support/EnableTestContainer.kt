package smalltalk.backend.support

import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import smalltalk.backend.support.testcontainer.TestContainerInitializer

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@ContextConfiguration(initializers = [TestContainerInitializer::class])
@ActiveProfiles("test")
@DirtiesContext
annotation class EnableTestContainer