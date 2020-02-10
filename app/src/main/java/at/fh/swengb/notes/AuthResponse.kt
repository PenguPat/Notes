package at.fh.swengb.notes

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class AuthResponse(val token: String) {
}