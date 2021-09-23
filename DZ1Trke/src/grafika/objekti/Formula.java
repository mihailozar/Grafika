package grafika.objekti;

import geometrija.Vektor;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class Formula extends FormulaNad {


    public Formula() {
        Path trougao=new Path(
                new MoveTo(0,-20),
                new HLineTo(15),
                new LineTo(0,30),
                new LineTo(-15,-20),
                new ClosePath()
        );
        trougao.setFill(Color.RED);
        Circle vozac=new Circle(3);

        Rectangle prednji=new Rectangle(-10,15,20,10);
        prednji.setFill(Color.BLACK);

        Rectangle zadnji=new Rectangle(-20,-20,40,10);
        zadnji.setFill(Color.BLACK);

        Arc vetrobran=new Arc(0,4,5,3,0,-180);
        vetrobran.setFill(Color.DARKRED);

        getChildren().addAll(prednji,zadnji,trougao,vozac,vetrobran);
        getTransforms().setAll(trans, rot);
    }

}
