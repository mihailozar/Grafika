package grafika.objekti;

import geometrija.Vektor;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class Formula2 extends FormulaNad{


    public Formula2() {
        Path trougao=new Path(
                new MoveTo(0,-20),
                new HLineTo(15),
                new LineTo(0,30),
                new LineTo(-15,-20),
                new ClosePath()
        );
        trougao.setFill(Color.BLUE);
        Circle vozac=new Circle(3);

        Rectangle prednjiSpojler=new Rectangle(-12,15,24,5);
        prednjiSpojler.setFill(Color.DARKBLUE);
        prednjiSpojler.getTransforms().addAll(new Translate(0,12));

        Rectangle prednji=new Rectangle(-10,15,20,10);
        prednji.setFill(Color.BLACK);

        Rectangle zadnjiSpojler=new Rectangle(-15,-20,32,10);
        zadnjiSpojler.setFill(Color.DARKBLUE);
        zadnjiSpojler.getTransforms().addAll(new Translate(-1,-10));

        Rectangle zadnji=new Rectangle(-20,-20,40,10);
        zadnji.setFill(Color.BLACK);

        Arc vetrobran=new Arc(0,4,5,3,0,-180);
        vetrobran.setFill(Color.DARKRED);

        getChildren().addAll(prednji,zadnji,trougao,vozac,vetrobran,zadnjiSpojler,prednjiSpojler);
        getTransforms().setAll(trans, rot);
    }


}
