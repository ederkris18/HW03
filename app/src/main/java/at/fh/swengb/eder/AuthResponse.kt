package at.fh.swengb.eder

import com.squareup.moshi.JsonClass

@JsonClass (generateAdapter = true)

class AuthResponse(val token: String) {
}