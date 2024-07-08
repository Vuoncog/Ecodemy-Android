package com.kltn.ecodemy.di

import android.content.Context
import com.kltn.ecodemy.data.api.EcodemyApi
import com.kltn.ecodemy.data.impl.AuthenticationMongoDBImpl
import com.kltn.ecodemy.data.impl.CourseDataProcessImpl
import com.kltn.ecodemy.data.impl.DataStorePreferencesImpl
import com.kltn.ecodemy.data.impl.FirebaseDataProcessImpl
import com.kltn.ecodemy.data.impl.LearnDataProcessImpl
import com.kltn.ecodemy.data.impl.RegisterCourseDataProcessImpl
import com.kltn.ecodemy.data.impl.SearchResultDataProcessImpl
import com.kltn.ecodemy.data.impl.TeacherHomeDataProcessImpl
import com.kltn.ecodemy.data.impl.TextFieldValidationImpl
import com.kltn.ecodemy.data.impl.UploadAvatarProcessImpl
import com.kltn.ecodemy.data.impl.WishlistDataProcessImpl
import com.kltn.ecodemy.data.repository.AccessTokenImpl
import com.kltn.ecodemy.domain.repository.AccessToken
import com.kltn.ecodemy.domain.repository.AuthenticationMongoDB
import com.kltn.ecodemy.domain.repository.CourseDataProcess
import com.kltn.ecodemy.domain.repository.DataStorePreferences
import com.kltn.ecodemy.domain.repository.FirebaseDataProcess
import com.kltn.ecodemy.domain.repository.LearnDataProcess
import com.kltn.ecodemy.domain.repository.RegisterCourseDataProcess
import com.kltn.ecodemy.domain.repository.SearchResultDataProcess
import com.kltn.ecodemy.domain.repository.TeacherHomeDataProcess
import com.kltn.ecodemy.domain.repository.TextFieldValidation
import com.kltn.ecodemy.domain.repository.UploadAvatarProcess
import com.kltn.ecodemy.domain.repository.WishlistDataProcess
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun providesAuthenticationMongoDB(
        ecodemyApi: EcodemyApi,
        firebaseDataProcess: FirebaseDataProcess
    ): AuthenticationMongoDB = AuthenticationMongoDBImpl(
        ecodemyApi = ecodemyApi,
        firebaseDataProcess = firebaseDataProcess
    )

    @Provides
    @Singleton
    fun providesCourseDataProcess(
        ecodemyApi: EcodemyApi
    ): CourseDataProcess = CourseDataProcessImpl(ecodemyApi = ecodemyApi)

    @Provides
    @Singleton
    fun providesWishlistDataProcess(
        ecodemyApi: EcodemyApi
    ): WishlistDataProcess = WishlistDataProcessImpl(ecodemyApi = ecodemyApi)

    @Provides
    @Singleton
    fun providesLearnDataProcess(
        ecodemyApi: EcodemyApi
    ): LearnDataProcess = LearnDataProcessImpl(ecodemyApi = ecodemyApi)

    @Provides
    @Singleton
    fun providesTextFieldValidation(): TextFieldValidation = TextFieldValidationImpl()

    @Provides
    @Singleton
    fun providesSearchResultDataProcess(
        ecodemyApi: EcodemyApi
    ): SearchResultDataProcess = SearchResultDataProcessImpl(ecodemyApi = ecodemyApi)

    @Provides
    @Singleton
    fun providesRegisterCourseDataProcess(
        ecodemyApi: EcodemyApi
    ): RegisterCourseDataProcess = RegisterCourseDataProcessImpl(ecodemyApi = ecodemyApi)

    @Provides
    @Singleton
    fun providesFirebaseDataProcess(): FirebaseDataProcess = FirebaseDataProcessImpl()

    @Provides
    @Singleton
    fun providesDataStorePreferences(
        @ApplicationContext context: Context
    ): DataStorePreferences {
        return DataStorePreferencesImpl(context = context)
    }

    @Provides
    @Singleton
    fun providesTeacherHomeDataProcess(
        ecodemyApi: EcodemyApi
    ): TeacherHomeDataProcess = TeacherHomeDataProcessImpl(ecodemyApi = ecodemyApi)

    @Provides
    @Singleton
    fun providesAccessToken(
        firebaseDataProcess: FirebaseDataProcess
    ): AccessToken = AccessTokenImpl(firebaseDataProcess)

    @Provides
    @Singleton
    fun providesUploadAvatarProcess(
        ecodemyApi: EcodemyApi,
        firebaseDataProcess: FirebaseDataProcess
    ): UploadAvatarProcess =
        UploadAvatarProcessImpl(ecodemyApi = ecodemyApi, firebaseDataProcess = firebaseDataProcess)
}