package me.immathan.stockersample.di

/**
 * Simple Implementation to achieve Dependency injection without using a dedicated library like
 * [Dagger].
 *
 * This is has taken advantage of Kotlin to achieve its simplicity
 * @author Mathan on 24/11/18
 */
interface AppComponent: AppModule, PinsListActivityModule