package com.akoufatzis.weatherappclean.search.mvvm.viewmodel

import com.akoufatzis.weatherappclean.di.scopes.PerActivity
import com.akoufatzis.weatherappclean.domain.usecases.GetCityWeatherUseCase
import com.akoufatzis.weatherappclean.domain.usecases.GetCityWeatherUseCase.Params
import com.akoufatzis.weatherappclean.search.model.CityWeatherModel
import com.akoufatzis.weatherappclean.search.model.mapToCityWeatherModel
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by alexk on 07.05.17.
 */
@PerActivity
class SearchViewModel @Inject constructor(val useCase: GetCityWeatherUseCase) {

    private val loadingRelay = PublishRelay.create<Boolean>()

    val compositeDisposable = CompositeDisposable()

    fun search(textChanges: Observable<CharSequence>): Observable<CityWeatherModel> {
        return textChanges
                .map {
                    Params(it.toString())
                }
                .compose(useCase.execute())
                .compose(mapToCityWeatherModel())
    }

    fun loading(): Observable<Boolean> {

        return loadingRelay
    }

    fun dispose() {
        compositeDisposable.dispose()
    }
}