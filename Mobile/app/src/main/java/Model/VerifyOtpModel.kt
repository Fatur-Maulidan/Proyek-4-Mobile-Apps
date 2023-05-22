package Model

data class VerifyOtpRequest (val otp: String)

data class VerifyOtpResponse (val message: String, val email: String)