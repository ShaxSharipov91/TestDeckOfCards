package Services;

import Models.BrandNewDeckModel;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class BrandNewDeckServices {

    public static Response GetBrandNewDeck(String url, boolean jokersEnable) {
        var response = given().queryParam("jokers_enabled",jokersEnable)
            .when().get(url);
        return response;
    }

    public static String GetDeckID(String url, boolean jokersEnable) {
        var response = GetBrandNewDeck(url, jokersEnable).getBody().as(BrandNewDeckModel.class);
        return response.deck_id;
    }
}
