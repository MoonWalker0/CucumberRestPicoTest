package steps

import io.cucumber.java8.En
import io.cucumber.java8.Scenario
import io.restassured.RestAssured
import io.restassured.response.Response
import utils.Serializer
import com.fasterxml.jackson.module.kotlin.readValue
import models.ResponseModel

class PowerLevelSteps(serializer: Serializer) : En {
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

        Given("^a list of decks$"){
            RestAssured.baseURI = "https://www.keyforgegame.com"
            path = "/api/decks"
        }

        When("I pick a deck with power level greater than {int}"){ level: Int ->
            response = RestAssured.given()
                .param("page_size", 1)
                .param("power_level", "${level + 1},24")
                .get(path)
                .then().extract().response()

            assert(response != null)
            model = localSerializer.readValue(response!!.body.asString())
        }

        Then("it should have more than {int} wins") { wins: Int ->
            assert(model!!.data.count() == 1)
            assert(model!!.data[0].wins.toInt() > wins)
        }

    }
}
