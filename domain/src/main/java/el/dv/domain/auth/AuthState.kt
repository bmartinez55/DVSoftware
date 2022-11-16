package el.dv.domain.auth

enum class AuthState {
    Authenticated,
    NotAuthenticated,
    TokenExpired
}

enum class AuthTokenRefreshResult {
    Success,
    RefreshNotRequired,
    Failed
}
