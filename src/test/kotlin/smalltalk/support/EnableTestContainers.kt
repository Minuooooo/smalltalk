package smalltalk.support

import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import smalltalk.support.testcontainers.TestContainersInitializer

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@ContextConfiguration(initializers = [TestContainersInitializer::class])
@ActiveProfiles("test")
@DirtiesContext
annotation class EnableTestContainers