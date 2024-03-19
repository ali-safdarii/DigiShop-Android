package com.example.digishop.domain.cart.usecase

import com.example.digishop.data.remote.datasource.CartRemoteDataSource
import com.example.digishop.data.remote.datasource.CartRemoteImpl
import com.example.digishop.data.remote.repository.CartRepositoryImpl
import com.example.digishop.data.remote.service.CartService
import com.example.digishop.data.remote.util.AsyncResult
import com.example.digishop.domain.cart.repository.CartRepository
import com.example.digishop.domain.session.IsUserLoggedInUseCase
import com.example.digishop.domain.token.usecase.BearerTokenUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import net.bytebuddy.utility.RandomString
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer

import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AddCartUseCaseTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    val testCoroutineDispatcher = UnconfinedTestDispatcher()


    private lateinit var mockWebServer: MockWebServer
    private lateinit var cartRemoteDataSource: CartRemoteDataSource
    private lateinit var cartRepository: CartRepository
    private lateinit var isUserLoggedInUseCase: IsUserLoggedInUseCase
    private lateinit var bearerTokenUseCase: BearerTokenUseCase
    private lateinit var addCartUseCase: AddCartUseCase


    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val baseUrl = mockWebServer.url("/").toString()

        val cartService = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CartService::class.java)



        cartRemoteDataSource =
            CartRemoteImpl(cartService = cartService, ioDispatcher = testCoroutineDispatcher)
        cartRepository = CartRepositoryImpl(cartRemoteDataSource)
        isUserLoggedInUseCase = mock()
        bearerTokenUseCase = mock()
        addCartUseCase = AddCartUseCase(
            cartRepository = cartRepository,
            isUserLoggedInUseCase = isUserLoggedInUseCase,
            bearerTokenUseCase = bearerTokenUseCase
        )
    }





    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }


    @Test
    fun `success response when adding to cart`() = runBlocking {

        val mockResponse = MockResponse().setResponseCode(201).setBody(
            """
                  {
                      "message": "محصول مورد نظر به سبد خرید شما اضافه شد",
                      "status_code": 201,
                      "success": true
                  }

              """.trimIndent()
        )

        mockWebServer.enqueue(mockResponse)
        val token = RandomString.make()

        `when`(isUserLoggedInUseCase.isUserLoggedIn()).thenReturn(flowOf(true))
        `when`(bearerTokenUseCase.bearerToken()).thenReturn(flowOf(token))

        val result = addCartUseCase.invoke(1, 1, 200, 1)


        val expected = (result as AsyncResult.Success).data.message

        assertEquals(expected, "محصول مورد نظر به سبد خرید شما اضافه شد")


    }


    // eXample 2


    /*   @Mock
       lateinit var cartRepository: CartRepository

       @Mock
       lateinit var isUserLoggedInUseCase: IsUserLoggedInUseCase

       @Mock
       lateinit var bearerTokenUseCase: BearerTokenUseCase

       private lateinit var addCartUseCase: AddCartUseCase

       @Before
       fun setup() {
           MockitoAnnotations.openMocks(this)
           addCartUseCase = AddCartUseCase(cartRepository, isUserLoggedInUseCase, bearerTokenUseCase)
       }

       @Test
       fun `test adding item to cart when user is logged in`() = runBlocking {
           // Mock necessary dependencies
           val productId = 1
           val colorId = 2
           val finalPrice = 100
           val qty = 1
           val token = "fakeToken"

           `when`(isUserLoggedInUseCase.isUserLoggedIn()).thenReturn(flowOf(true))
           `when`(bearerTokenUseCase.bearerToken()).thenReturn(flowOf(token))
           `when`(cartRepository.insert(productId, colorId, finalPrice, qty, token)).thenReturn(
               NoDataResponse(
                   message = "محصول مورد نظر به سبد خرید شما اضافه شد",
                   statusCode = 201,
                   success = true
               )
           )

           // Invoke the use case
           val result = addCartUseCase.invoke(productId, colorId, finalPrice, qty)

           // Verify the result
           assertTrue(result is AsyncResult.Success)
           assertEquals(
               "محصول مورد نظر به سبد خرید شما اضافه شد",
               (result as AsyncResult.Success<NoDataResponse>).data.message
           )
       }

       @Test
       fun `test adding item to cart when user is not logged in`() = runBlocking {
           // Mock necessary dependencies
           val productId = 1
           val colorId = 2
           val finalPrice = 100
           val qty = 1

           `when`(bearerTokenUseCase.bearerToken()).thenReturn(flowOf(null))
           `when`(isUserLoggedInUseCase.isUserLoggedIn()).thenReturn(flowOf(false))

           // Invoke the use case
           val result = addCartUseCase.invoke(productId, colorId, finalPrice, qty)

           assertTrue(result is AsyncResult.Failure)
           val errorType = (result as AsyncResult.Failure).error

           assertTrue(errorType is CustomErrorType.CustomMessage)

           assertEquals(
               AUTH_ERROR_MESSAGE,
               (errorType as CustomErrorType.CustomMessage).message
           )
       }
   */


    /// EXAMPLE 3


    /* private lateinit var cartRemoteImpl: CartRemoteImpl
     private lateinit var mockWebServer: MockWebServer

     @Before
     fun setup() {
         mockWebServer = MockWebServer()
         mockWebServer.start()

         val baseUrl = mockWebServer.url("/").toString()
         val cartService = Retrofit.Builder()
             .baseUrl(baseUrl)
             .addConverterFactory(GsonConverterFactory.create())
             .build()
             .create(CartService::class.java)

         cartRemoteImpl = CartRemoteImpl(cartService,testDispatcher)
     }

     @After
     fun tearDown() {
         mockWebServer.shutdown()
     }

     @Test
     fun testInsertCartItem() = runBlocking {
         // Arrange
         val productId = 1
         val colorId = 2
         val finalPrice = 100
         val qty = 3
         val token = "your_token"

         val expectedResponse = NoDataResponse(success = true, statusCode = 201, message = "محصول مورد نظر به سبد خرید شما اضافه شد")

         val responseJson = """
               {
                   "message": "محصول مورد نظر به سبد خرید شما اضافه شد",
                   "status_code": 201,
                   "success": true
               }
           """.trimIndent()

         mockWebServer.enqueue(MockResponse().setBody(responseJson))

         // Act
         val result = cartRemoteImpl.insert(productId, colorId, finalPrice, qty, token)

         // Assert
         assert(result == expectedResponse)

         // Verify the request sent to the server
         val recordedRequest = mockWebServer.takeRequest()
         assert(recordedRequest.path == "/your_api_path") // Adjust the path based on your API

         // Additional assertions can be made on the recordedRequest if needed
     }

     @Test
     fun testInsertCartItem2() = runBlocking {
         // Arrange
         val productId = 1
         val colorId = 2
         val finalPrice = 100
         val qty = 3
         val token = "your_token"

         val mockResponse = MockResponse()
             .setResponseCode(201)
             .setBody("{\"message\": \"محصول مورد نظر به سبد خرید شما اضافه شد\", \"status_code\": 201, \"success\": true}")

         mockWebServer.enqueue(mockResponse)

         // Act
         val result = cartRemoteImpl.insert(productId, colorId, finalPrice, qty, token)

         // Assert
         assert(result.success)
         assert(result.message == "محصول مورد نظر به سبد خرید شما اضافه شد")
     }



       @Test
       fun testInsertCartItem3() = runBlocking {
           // Arrange
           val productId = 1
           val colorId = 2
           val finalPrice = 100
           val qty = 3
           val token = "your_token"

           val responseJson = """
               {
                   "message": "محصول مورد نظر به سبد خرید شما اضافه شد",
                   "status_code": 201,
                   "success": true
               }
           """.trimIndent()

           mockWebServer.enqueue(MockResponse().setBody(responseJson))

           // Act
           val result = cartRemoteImpl.insert(productId, colorId, finalPrice, qty, token)

           // Assert
           assert(result.success)
           assert(result.message == "محصول مورد نظر به سبد خرید شما اضافه شد")
       }*/

}





















