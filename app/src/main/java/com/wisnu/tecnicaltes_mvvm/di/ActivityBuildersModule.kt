package com.wisnu.tecnicaltes_mvvm.di

import com.wisnu.tecnicaltes_mvvm.view.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule{

    @ContributesAndroidInjector

    abstract fun constributeMainActivity(): MainActivity
}