package com.example.digishop.domain.session

/**
 * This defines the various states of the user's session in the app. Specifically
 * with regards to their authentication status.
 */
enum class SessionState {
    /**
     * This state is shown when the app first starts, and we have no information
     * about the user's authentication state.
     */
    UNINITIALIZED,

    /**
     * This state occurs when the user has been authenticated to use the app.
     */
    AUTHENTICATED,

    /**
     * This state occurs when the user is not authenticated, such as using it
     * for the first time, or if their authentication information expired.
     */
    UN_AUTHENTICATED,
}
