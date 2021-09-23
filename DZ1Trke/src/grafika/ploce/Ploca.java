package grafika.ploce;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

public class Ploca  extends Group {
    public static final double VELICINA = 100;
    protected Group koren = new Group();
    public int rednibr=-1;
    public Rectangle kvadar;
    public Bounds pozicija;
    public int orijentacija;

    public Ploca()
    {
        getChildren().add(koren);
    }

    public Group dohvKoren(){
        return koren;
    }

    public void  setOrder(int i){
        rednibr=i;
    }

    public void setPosition(){
        pozicija=kvadar.localToScene(kvadar.getBoundsInLocal());
    }
    public void setOrijentacija(int  i){
        orijentacija=i;
    }
}
