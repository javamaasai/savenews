package com.savenews

import dagger.Module
import dagger.Provides

@Module
class AppModule constructor(newsApplication: NewsApplication){
    var newsApplication:NewsApplication = newsApplication

    @Provides
    fun provideMyRetroApplication():NewsApplication{
        return newsApplication
    }
}