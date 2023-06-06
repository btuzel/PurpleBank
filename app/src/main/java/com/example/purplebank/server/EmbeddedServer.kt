package com.example.purplebank.server

import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.util.pipeline.PipelineContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.concurrent.thread

enum class Response {
    NETWORK_ERROR, FAILURE, SUCCESS
}

@Singleton
class MockEnvironmentConfig @Inject constructor() {

    @Volatile
    var userAccountsResponse: Response = Response.SUCCESS

    @Volatile
    var sendMoneyResponse: Response = Response.SUCCESS
}

@Singleton
class EmbeddedServer @Inject constructor(
    private val mockEnvironmentConfig: MockEnvironmentConfig,
) {

    private var serverThread: Thread? = null

    var asd = 0
    private val list = listOf(successResponseMessageGetAccountVariantTwo,successResponseMessageGetAccountVariantOne, successResponseMessageGetAccountVariantTwo)
    fun start() {
        if (serverThread != null) {
            return
        }
        serverThread = thread {
            embeddedServer(Netty, 8080) {
                routing {
                    get("/user-account") {
                        handleRequest(
                            mockEnvironmentConfig.userAccountsResponse,
                            successResponseMessage = list[asd]
                        )
                        asd++
                    }
                    post("/send-money") {
                        handleRequest(
                            mockEnvironmentConfig.sendMoneyResponse,
                            successResponseMessage = successResponseMessageSendMoney
                        )
                    }
                }
            }.start(wait = true)
        }
    }

    private suspend fun PipelineContext<Unit, ApplicationCall>.handleRequest(
        response: Response,
        successResponseMessage: String
    ) {
        when (response) {
            Response.SUCCESS -> call.respond(HttpStatusCode.OK, successResponseMessage)
            Response.FAILURE -> call.respond(HttpStatusCode.BadRequest)
            Response.NETWORK_ERROR -> call.respond(HttpStatusCode.BadRequest)
        }
    }
}