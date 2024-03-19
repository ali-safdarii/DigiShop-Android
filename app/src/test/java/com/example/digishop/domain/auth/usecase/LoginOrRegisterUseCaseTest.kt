package com.example.digishop.domain.auth.usecase

import com.example.digishop.data.remote.datasource.AuthRemoteDataSource
import com.example.digishop.data.remote.datasource.AuthRemoteImpl
import com.example.digishop.data.remote.responses.auth.AuthResponse
import com.example.digishop.data.remote.responses.auth.UserApi
import com.example.digishop.data.remote.service.AuthService
import com.example.digishop.data.remote.util.AsyncResult
import com.example.digishop.data.remote.util.BaseResponse
import com.example.digishop.domain.auth.model.Email
import com.example.digishop.domain.auth.model.LoginRegisterRequest
import com.example.digishop.domain.auth.model.Password
import com.example.digishop.domain.auth.model.ValidationType
import com.example.digishop.domain.auth.repository.AuthRepository
import com.example.digishop.domain.error.CustomErrorType
import com.example.digishop.domain.token.repository.TokenRepository
import com.example.digishop.domain.user.model.User

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class LoginOrRegisterUseCaseTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    val testDispatcher = UnconfinedTestDispatcher()


    private var authService: AuthService = mock()
    private lateinit var authRemoteDataSource: AuthRemoteDataSource


    private lateinit var authRepository: AuthRepository
    private lateinit var tokenRepository: TokenRepository
    private lateinit var validationInputsUseCase: ValidationInputsUseCase
    private lateinit var loginOrRegisterUseCase: LoginOrRegisterUseCase

    @Before
    fun setUp() {
        authRemoteDataSource = AuthRemoteImpl(authService, ioDispatcher = testDispatcher)

        authRepository = mock()
        tokenRepository = mock()
        validationInputsUseCase = mock()
        loginOrRegisterUseCase =
            LoginOrRegisterUseCase(authRepository, tokenRepository, validationInputsUseCase)
    }


    @Test
    fun `loginOrRegister success test`() = runBlocking {
        // Mocking
        val request = LoginRegisterRequest(Email("test@example.com"), Password("password"))
        `when`(validationInputsUseCase.invoke(request)).thenReturn(ValidationType.Valid)
        `when`(authRepository.loginOrRegister(request)).thenReturn(
            BaseResponse(
                data = AuthResponse(
                    jwt = "dummyToken",
                    userApi = mockUserApi
                ), 200, true, "Login or registration successful"
            )
        )


        val result = loginOrRegisterUseCase.invoke(request)


        assertTrue(result is AsyncResult.Success)
        val user = (result as AsyncResult.Success<User>).data
        assertEquals("dummyToken", user.jwtToken)
        assertEquals("test@example.com", result.data.email)
    }

    @Test
    fun `loginOrRegister failure `() = runBlocking {

        val request = LoginRegisterRequest(Email("test@example.com"), Password("password"))
        `when`(validationInputsUseCase.invoke(request)).thenReturn(ValidationType.Valid)
        `when`(authRepository.loginOrRegister(request)).thenReturn(any())


        val result = loginOrRegisterUseCase.invoke(request)


        assertTrue(result is AsyncResult.Failure)
        val errorType = (result as AsyncResult.Failure).error
        assertTrue(errorType is CustomErrorType.Unknown)

    }


    @Test
    fun `loginOrRegister validation failure test`() = runBlocking {
        // Mocking
        val request = LoginRegisterRequest(Email("invalid_email"), Password("password"))
        `when`(validationInputsUseCase.invoke(request)).thenReturn(ValidationType.InvalidEmail)

        // Test
        val result = loginOrRegisterUseCase.invoke(request)


        assertTrue(result is AsyncResult.Failure)
        val error = (result as AsyncResult.Failure).error
        assertTrue(error is CustomErrorType.CustomMessage)
        assertEquals(
            "Validation Input is Failed ....",
            (error as CustomErrorType.CustomMessage).message
        )
    }


    /* SIMPLE EXAMPLE 2 */

    @Test
    fun `test loginOrRegister success`() {
        // Mock data
        val loginRegisterRequest =
            LoginRegisterRequest(Email("test@example.com"), Password("password"))
        val authResponse = AuthResponse(jwt = "dummyToken", userApi = mockUserApi)

        // Mock behavior
        runBlocking {
            `when`(authService.loginOrRegister(loginRegisterRequest))
                .thenReturn(
                    BaseResponse(
                        data = authResponse,
                        statusCode = 200,
                        success = true,
                        message = "Success"
                    )
                )
        }

        // Call the method under test
        val result = runBlocking { authRemoteDataSource.loginOrRegister(loginRegisterRequest) }

        // Verify the result
        assertEquals(true, result.success)
        assertEquals("dummyToken", result.data.jwt)
        assertEquals("test@example.com", result.data.userApi.email)
    }

    @Test
    fun `test loginOrRegister failure`() {
        // Mock data
        val loginRegisterRequest =
            LoginRegisterRequest(Email("test@example.com"), Password("password"))

        // Mock behavior
        runBlocking {
            `when`(authService.loginOrRegister(loginRegisterRequest))
                .thenReturn(
                    BaseResponse(
                        data = AuthResponse("", mockUserApi),
                        statusCode = 400,
                        success = false,
                        message = "Bad Request"
                    )
                )
        }

        // Call the method under test
        val result = runBlocking { authRemoteDataSource.loginOrRegister(loginRegisterRequest) }

        // Verify the result
        assertEquals(false, result.success)
        assertEquals("Bad Request", result.message)
    }




    companion object {

        // Mock response body and response
       /* val responseBodyMock = ResponseBody.create("application/json".toMediaTypeOrNull(), "")
        val mockResponse = Response.error<Any>(400, responseBodyMock)*/

        // Mock userApi data
        private val mockUserApi = UserApi(
            activationDate = null,
            createdAt = "2024-02-03T12:00:00",
            currentTeamId = null,
            deletedAt = null,
            email = "test@example.com",
            emailVerifiedAt = null,
            firstName = "Test",
            id = 1,
            image = null,
            lastName = "User",
            mobile = null,
            mobileVerifiedAt = null,
            nationalCode = null,
            profilePhotoUrl = "",
            slug = null,
            status = 1,
            twoFactorConfirmedAt = null,
            updatedAt = "2024-02-03T12:00:00",
            userType = 1,
            username = null
        )

        // Mock coroutine dispatcher

    }


}




















