package object.movableObject;

import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Translate;

public class Puck extends MovableObject {
	
	private static final double SPEED_X_MIN = 90;
	private static final double SPEED_X_MAX = 120;
	private static final double SPEED_Z_MIN = 30;
	private static final double SPEED_Z_MAZ = 60;
	
	public Puck ( double radius, double height ) {
		super ( new Translate ( 0, 0, 0 ), Puck.getRandomSpeed ( ) );
		
		Cylinder puck = new Cylinder ( radius, height );
		puck.setMaterial ( new PhongMaterial ( Color.GRAY ) );
		super.getChildren ( ).addAll (
				puck
		);
	}
	
	private static Point3D getRandomSpeed ( ) {
		double x = Math.random ( ) * ( Puck.SPEED_X_MAX - Puck.SPEED_X_MIN ) + SPEED_X_MIN;
		double z = Math.random ( ) * ( Puck.SPEED_Z_MAZ - Puck.SPEED_Z_MIN ) + SPEED_Z_MIN;
		return new Point3D ( x, 0, z );
	}
}
