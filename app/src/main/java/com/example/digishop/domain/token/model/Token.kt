package com.example.digishop.domain.token.model

@JvmInline
value class JwtToken(val value: String)



/**
 * Contains the information necessary for authenticating network requests.
 *
 * @property[jwt] The current token used to validate a user's request.

 */
data class Token(
    val jwt: JwtToken,
)
