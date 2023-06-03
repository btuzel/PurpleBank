package com.example.purplebank.server

const val successResponseMessageGetAccountVariantOne = """{
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

const val successResponseMessageGetAccountVariantTwo = """{
	              
		                	"direction": "outgoing"
		                
                }"""


const val successResponseMessageSendMoney = """{
	"result": "ok",
	"newBalance": {
		"currency": "GBP",
		"amount": {
			"units": 4,
			"subUnits": 1
		}
	}

                            }"""

const val failResponseMessageSendMoney = """{

	"result": "failed",
	"failureReason": "transaction_flagged_as_possibly_fraudulent"

                            }"""