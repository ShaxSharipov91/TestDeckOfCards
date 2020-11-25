package Tests;

import Models.BrandNewDeckModel;
import Models.DrawCardModel;
import Services.BrandNewDeckServices;
import Services.DrawCardServices;
import org.junit.Assert;
import org.junit.Test;


public class DeckOfCardsTests extends BaseTest {

    @Test
    public void testCreateNewDeck(){
        String url = BaseTest.url + "new/";
        //Create a new deck with jokers_enabled=true
        var newDeckResponse = BrandNewDeckServices.GetBrandNewDeck(url, true);

        //Verify response is successful
        Assert.assertTrue("Request was unsuccessful",newDeckResponse.getStatusCode() == 200);

        //Deserialize to Java Objects
        var responseBody = newDeckResponse.getBody().as(BrandNewDeckModel.class);

        //Fetch some of the response body
        boolean success = responseBody.success;
        String deckId = responseBody.deck_id;
        boolean shuffled = responseBody.shuffled;
        int remaining = responseBody.remaining;

        //Verify fields
        Assert.assertTrue("Request was unsuccessful", success);
        Assert.assertNotNull("Deck ID is null", deckId);
        Assert.assertFalse("Deck was shuffled", shuffled);
        Assert.assertNotNull("Deck contains no cards", remaining);

        //Verify Deck is equal to 54 (two jokers)
        Assert.assertEquals("Deck contains more than 54 cards", 54, remaining);

    }

    @Test
    public void testDrawCards(){
        String getDeckUrl = BaseTest.url + "new/";
        int countOfCardsToDraw = 5;
        int expectedRemainingCards = 54-countOfCardsToDraw;
        //Get a valid deck id
        String deckId = BrandNewDeckServices.GetDeckID(getDeckUrl, true);
        String drawCardUrl = BaseTest.url + deckId + "/draw/";

        //Use deck id to draw cards from
        var drawDeckResponse = DrawCardServices.DrawCards(drawCardUrl, countOfCardsToDraw);

        //Verify response is successful
        Assert.assertTrue("Request was unsuccessful",drawDeckResponse.getStatusCode() == 200);

        //Deserialize to Java Objects
        var responseBody = drawDeckResponse.getBody().as(DrawCardModel.class);

        //Verify fields
        boolean success = responseBody.success;
        int remaining = responseBody.remaining;
        Assert.assertTrue("Request was unsuccessful", success);
        responseBody.cards.forEach(Assert::assertNotNull);

        //Verify number of remaining cards
        Assert.assertEquals("Deck does not contain "+expectedRemainingCards+" cards, It has: "+remaining+" cards", expectedRemainingCards, remaining);
    }

}
