package meta;

import javafx.animation.FadeTransition;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class TextPoena extends Text {

    public FadeTransition tranzicija;
    public TextPoena(int i){
        super(String.format("%d",i));
        tranzicija =new FadeTransition(Duration.seconds(5),this);
        tranzicija.setFromValue(1.0);
        tranzicija.setToValue(0);
        tranzicija.play();
    }
}
