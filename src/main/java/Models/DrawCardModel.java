package Models;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class DrawCardModel {
    public boolean success;
    public List<Card> cards;
    public String deck_id;
    public int remaining;

    public class Card {
        public String image;
        public String value;
        public String suit;
        public String code;
    }
}
