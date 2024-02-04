package el.dv.dvproperties.feature.propertydetails.view.viewreducer

import el.dv.domain.core.DefaultUseCase
import el.dv.domain.dvproperties.propertydetails.model.PropertyDetails
import el.dv.dvproperties.feature.propertydetails.state.PropertyDetailsViewState
import el.dv.dvproperties.feature.propertydetails.state.PropertyDetailsViewUpdate
import el.dv.dvproperties.feature.propertydetails.view.PropertyDetailsViewModel

class GetPropertyDetailsViewReducer : DefaultUseCase<GetPropertyDetailsViewReducer.Request, PropertyDetailsViewUpdate> {
    override fun run(request: Request): PropertyDetailsViewUpdate {
        return PropertyDetailsViewUpdate(
            request.internalState.copy(
                viewState = request.internalState.viewState.copy(
                    propertyDetailsViewState = PropertyDetailsViewState.Show(request.propertyDetails)
                )
            )
        )
    }

    data class Request(
        val internalState: PropertyDetailsViewModel.InternalState,
        val propertyDetails: PropertyDetails
    )
}
