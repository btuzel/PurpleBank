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
    var generatePreSignedUrlResponse: Response = Response.SUCCESS

    @Volatile
    var metadataResponse: Response = Response.SUCCESS
}

@Singleton
class EmbeddedServer @Inject constructor(
    private val mockEnvironmentConfig: MockEnvironmentConfig,
) {

    private var serverThread: Thread? = null

    fun start() {
        if (serverThread != null) {
            return
        }
        serverThread = thread {
            embeddedServer(Netty, 8080) {
                routing {
                    get("/user-account") {
                        handleRequest(
                            mockEnvironmentConfig.generatePreSignedUrlResponse,
                            successResponseMessage = """{
	                        "id": "000000-000000-000000-000000-000000",
	                        "name": "Jane",
	                        "myBalance": {
		                    "currency": "GBP",
		                    "amount": {
	                		"units": 4293,
	                		"subUnits": 76
	                        	}
	                        },
	                    "myMostRecentTransactions": [
		                    {
			                    "sender": "Rasha Hamdan",
			                    "reference": "Pizza slice 🍕",
			                    "date": "20230602T21:45:00",
			                    "direction": "outgoing",
			                    "amount": {
		                		"currency": "GBP",
		                		"amount": {
			            	    	"units": 4,
			            	    	"subUnits": 63
			            	    }
			            }
		                },
		                    {
			                    "sender": null,
			                    "reference": "INTEREST MAY 23 @ 4%",
		                    	"date": "20230601T00:00:01",
		                    	"direction": "incoming",
		                    	"amount": {
			                	"currency": "GBP",
			                	"amount": {
			                		"units": 14,
				                	"subUnits": 59
				                }
		            	}
		                },
		                {
		                	"sender": "Acme Corp.",
		                	"reference": "SALARY MAY 23",
			                "date": "20230531T00:04:00",
			                "direction": "incoming",
		                	"amount": {
			                	"currency": "GBP",
			                	"amount": {
			                		"units": 3000,
			                		"subUnits": 0
			                	}
		                	}
	   	                },
 {
		                	"sender": "Acme Corp.",
		                	"reference": "SALARY MAY 23",
			                "date": "20230531T00:04:00",
			                "direction": "incoming",
		                	"amount": {
			                	"currency": "GBP",
			                	"amount": {
			                		"units": 3000,
			                		"subUnits": 0
			                	}
		                	}
	   	                },
 {
		                	"sender": "Acme Corp.",
		                	"reference": "SALARY MAY 23",
			                "date": "20230531T00:04:00",
			                "direction": "incoming",
		                	"amount": {
			                	"currency": "GBP",
			                	"amount": {
			                		"units": 3000,
			                		"subUnits": 0
			                	}
		                	}
	   	                },
	                	{
		                	"sender": "Porkys Bar and Grill",
		                	"reference": "🐷",
		                	"date": "20230529T17:07:00",
		                	"direction": "outgoing",
		                	"amount": {
			                	"currency": "GBP",
		                		"amount": {
			                		"units": 13,
			                		"subUnits": 50
		                		}
		                	}
	                	}
	                ]
                }"""
                        )
                    }
                    post("/send-money") {
                        handleRequest(
                            mockEnvironmentConfig.generatePreSignedUrlResponse,
                            successResponseMessage = """{

	"result": "ok",
	"newBalance": {
		"currency": "GBP",
		"amount": {
			"units": 4,
			"subUnits": 1
		}
	}
}
                            }"""
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