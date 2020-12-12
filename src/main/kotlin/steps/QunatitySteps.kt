package steps

import io.cucumber.java8.En
import io.cucumber.java8.Scenario
import io.restassured.RestAssured
import io.restassured.response.Response
import utils.Serializer
import com.fasterxml.jackson.module.kotlin.readValue
import models.ResponseModel

class QuantitySteps(serializer: Serializer) : En {
    private var localSerializer = serializer.mapper

    init {
        runSteps()
    }

    private fun runSteps() {
        var path: String? = null
        var response: Response? = null
        var model : ResponseModel? = null
        Before { scenario: Scenario -> println("Before scenario : " + scenario.name) }

        After { scenario: Scenario -> println("After scenario : " + scenario.name) }

        Given("^list of decks$"){
            RestAssured.baseURI = "https://www.keyforgegame.com"
            path = "/api/decks"
        }

        When("^I pick a random deck$"){
            response = RestAssured.given()
                .param("page_size", 1)
                .get(path)
                .then().extract().response()

            assert(response != null)
            model = localSerializer.readValue(response!!.body.asString())
        }

        Then("it should contain {int} cards") { quantity: Int ->
            assert(model!!.data.count() == 1)
            assert(model!!.data[0].cards.count() == 36)
        }

        Then("the cards should be from {int} houses") { quantity: Int ->
            assert(model!!.data[0]._links.houses.count() == 3)
        }

    }
}
