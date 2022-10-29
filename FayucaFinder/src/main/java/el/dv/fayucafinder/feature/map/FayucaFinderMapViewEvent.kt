package el.dv.fayucafinder.feature.map

sealed class FayucaFinderMapViewEvent {
    object Init : FayucaFinderMapViewEvent()
    object ViewLoaded : FayucaFinderMapViewEvent()
}
