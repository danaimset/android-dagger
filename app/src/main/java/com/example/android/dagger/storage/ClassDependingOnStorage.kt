package com.example.android.dagger.storage

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Qualifier

/**
 * You can achieve the same functionality as qualifiers with the @Named annotation, however qualifiers are recommended because:
 * They can be stripped out from Proguard or R8
 * You don't need to keep a shared constant for matching the names
 * They can be documented
 */

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class RegistrationStorage

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class LoginStorage

@Module
class StorageModule {

    @RegistrationStorage
    @Provides
    fun provideRegistrationStorage(context: Context): Storage {
        return SharedPreferencesNamedStorage("registration", context)
    }

    @LoginStorage
    @Provides
    fun provideLoginStorage(context: Context): Storage {
        return SharedPreferencesNamedStorage("login", context)
    }

    /*
    @Binds
    abstract fun provideRegistrationStorage(storage: SharedPreferencesStorage): Storage
    */

    /*
    // @Provides tell Dagger how to create instances of the type that this function
    // returns (i.e. Storage).
    // Function parameters are the dependencies of this type (i.e. Context).
    @Provides
    fun provideStorage(context: Context): Storage {
        // Whenever Dagger needs to provide an instance of type Storage,
        // this code (the one inside the @Provides method) will be run.
        return SharedPreferencesStorage(context)
    }
    */
}

// class ClassDependingOnStorage(@RegistrationStorage private val storage: Storage)

class ClassDependingOnStorage {

    @Inject
    @field:RegistrationStorage
    lateinit var storage: Storage
}

class SharedPreferencesNamedStorage @Inject constructor(name: String, context: Context) : Storage {

    private val sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)

    override fun setString(key: String, value: String) {
        with(sharedPreferences.edit()) {
            putString(key, value)
            apply()
        }
    }

    override fun getString(key: String): String {
        return sharedPreferences.getString(key, "")!!
    }
}