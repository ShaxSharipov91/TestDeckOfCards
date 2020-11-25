package Services;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class DrawCardServices {

    public static Response DrawCards(String url, int numberOfCards) {
        var response = given().queryParam("count",numberOfCards)
                .when().get(url);
        return response;
    }
}
