package kamere;

import javafx.scene.PerspectiveCamera;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class OriginalnaKamera extends PerspectiveCamera {
    public Rotate rotacijaX;
    public Rotate rotacijaY;
    public Translate translacija;
    public  Rotate rotacijaZ;

    public OriginalnaKamera( boolean arg){
        super(arg);
    }
}
