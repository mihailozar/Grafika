package grafika.objekti;

import geometrija.Vektor;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class Formula1 extends FormulaNad {


    public Formula1() {
        Rectangle telo = new Rectangle(-10, -15, 20, 30);
        telo.setFill(bojaSasije);

        Rectangle tocak1 = new Rectangle(-15, 7, 5, 8);
        tocak1.setFill(bojaTockova);
        Rectangle tocak2 = new Rectangle(10, 7, 5, 8);
        tocak2.setFill(bojaTockova);
        Rectangle tocak3 = new Rectangle(-18, -15, 8, 10);
        tocak3.setFill(bojaTockova);
        Rectangle tocak4 = new Rectangle(10, -15, 8, 10);
        tocak4.setFill(bojaTockova);

        getChildren().addAll(telo, tocak1, tocak2, tocak3, tocak4);
        getTransforms().setAll(trans, rot);

    }

}
