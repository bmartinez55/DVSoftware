package el.dv.dvpropertiesdata.network.core

import el.dv.domain.logging.AppLog
import okhttp3.Interceptor
import okhttp3.Response

class ClientInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        AppLog.d("Outgoing request to ${request.url()}")
        return chain.proceed(request)
    }
}
