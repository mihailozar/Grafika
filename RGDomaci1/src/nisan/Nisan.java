package nisan;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class Nisan extends Group {

    public Translate pomeranje=new Translate(0,0,-5);


    public Nisan (){
        Circle spolja=new Circle(0,0,20, Color.BLACK);
        spolja.setFill(null);
        spolja.setStroke(Color.BLACK);
        spolja.setStrokeWidth(2);
        Circle unutra=new Circle(0,0,2,Color.BLACK);
        for(int i=0;i<4;i++){
            Rectangle crtica=new Rectangle(0,-1,8,1);
            crtica.getTransforms().addAll(
                    new Rotate(i*90),
                    new Translate(16,0)
            );
            this.getChildren().addAll(crtica);
        }
        this.getChildren().addAll(spolja, unutra);
        this.getTransforms().addAll(pomeranje);
    }
}
