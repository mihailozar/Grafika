import javafx.scene.PerspectiveCamera;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class PerspektivnaKamera extends PerspectiveCamera {
    public Translate t1;

    public Rotate rx;
    public Rotate ry;
    public Rotate rz;

    public PerspektivnaKamera(){
        super(true);
         t1=new Translate(0,0,-800);

         rx=new Rotate(0,Rotate.X_AXIS);
         ry=new Rotate(0,Rotate.Y_AXIS);
         rz=new Rotate(0,Rotate.Z_AXIS);

        this.getTransforms().addAll(ry,rx,rz,t1);
    }
}
