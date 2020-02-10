package hu.pe.warwind.cashHolder

data class LoginResponse(
    val data: String,
    val errorText: String?

)

data class LoginRequest(
    val username: String,
    val password: String,
    val action: String
)