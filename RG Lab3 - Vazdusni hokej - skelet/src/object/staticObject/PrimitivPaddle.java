package object.staticObject;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Translate;
import object.movableObject.MovableObject;

public class PrimitivPaddle extends StaticObject{
    private Translate translate;
    Box paddle;
    public PrimitivPaddle ( double width, double height, double depth ) {
		 paddle = new Box( width, height, depth );
		paddle.setMaterial ( new PhongMaterial( Color.GREEN ) );
		super.getChildren ( ).addAll ( paddle );
    }

    @Override public boolean collision ( MovableObject movableObject ) {
        boolean collisionDetected = super.collision ( movableObject );

        if ( collisionDetected ) {
            movableObject.invertSpeedX ( );
            if(paddle.localToScene(paddle.getBoundsInLocal()).getCenterX()>0){
                movableObject.energijaDesno=movableObject.energijaDesno>20?movableObject.energijaDesno-20:0;
            }else{
                movableObject.energijaLevo=movableObject.energijaLevo>20?movableObject.energijaLevo-20:0;
            }
        }

        return collisionDetected;
    }

    public void move ( double dx, double dy, double dz ) {
        this.translate.setX ( this.translate.getX ( ) + dx );
        this.translate.setY ( this.translate.getY ( ) + dy );
        this.translate.setZ ( this.translate.getZ ( ) + dz );
    }

    public double getZ ( ) {
        return this.translate.getZ ( );
    }
}
