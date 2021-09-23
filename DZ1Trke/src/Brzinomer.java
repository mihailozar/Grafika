import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

import javax.swing.*;

public class Brzinomer extends Group {
    public Rotate rotacija;

    public Brzinomer(){
        Arc veciUgao=new Arc(0,0,40,40,30,150);
        veciUgao.setFill(null);
        veciUgao.setStroke(Color.BLACK);
        veciUgao.setStrokeWidth(4);
        Arc manjiUgao=new Arc(0,0,40,40,0,30);
        manjiUgao.setFill(null);
        manjiUgao.setStroke(Color.RED);
        manjiUgao.setStrokeWidth(4);

        Rectangle kazaljka=new Rectangle(-40,-2,40,4);
        kazaljka.setFill(Color.BLACK);
        rotacija=new Rotate(0);
        kazaljka.getTransforms().addAll(rotacija);
        this.getChildren().addAll(veciUgao,manjiUgao,kazaljka);
    }
}
