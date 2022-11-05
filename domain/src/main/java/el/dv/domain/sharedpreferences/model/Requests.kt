package el.dv.domain.sharedpreferences.model

data class SaveStringRequest(val key: String, val value: String)

data class SaveBooleanRequest(val key: String, val value: Boolean)

data class LoadValueRequest(val key: String)
