package com.br.l7.credit_simulator_app.config
import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.caffeine.CaffeineCache
import org.springframework.cache.support.SimpleCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit

@Configuration
@EnableCaching
class CacheConfig {

    @Bean
    fun cacheManager(): CacheManager {
        val cache = CaffeineCache("simulateLoan", Caffeine.newBuilder()
            .expireAfterWrite(8, TimeUnit.HOURS)  // TTL de 2 horas
            .maximumSize(1000)
            .build())

        val manager = SimpleCacheManager()
        manager.setCaches(listOf(cache))
        return manager
    }
}