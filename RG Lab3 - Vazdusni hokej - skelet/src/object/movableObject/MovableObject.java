package object.movableObject;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.transform.Translate;

public abstract class MovableObject extends Group {
	private Translate position;
	private Point3D   speed;
	public int energijaDesno;
	public int energijaLevo;
	public boolean udario=true;
	
	public MovableObject ( Translate position, Point3D speed ) {
		this.position = position;
		this.speed = speed;
		
		super.getTransforms ( ).addAll (
				this.position
		);
	}
	
	public void update ( double dt ) {
		double newX ;
		double newY ;
		double newZ;

		if(udario){
			 newX = position.getX ( ) + speed.getX ( ) * dt* ((double)energijaDesno/100);
			 newY = position.getY ( ) + speed.getY ( ) * dt* ((double)energijaDesno/100);
			 newZ = position.getZ ( ) + speed.getZ ( ) * dt* ((double)energijaDesno/100);

		}else{
		 newX = position.getX ( ) + speed.getX ( ) * dt*((double) energijaLevo/100);
		 newY = position.getY ( ) + speed.getY ( ) * dt*((double) energijaLevo/100);
		 newZ = position.getZ ( ) + speed.getZ ( ) * dt*((double) energijaLevo/100);

		}
		
		this.position.setX ( newX );
		this.position.setY ( newY );
		this.position.setZ ( newZ );
	}
	
	public void invertSpeedX ( ) {
		this.speed = new Point3D ( -this.speed.getX ( ), this.speed.getY ( ), this.speed.getZ ( ) );
	}
	
	public void invertSpeedZ ( ) {
		this.speed = new Point3D ( this.speed.getX ( ), this.speed.getY ( ), -this.speed.getZ ( ) );
	}
}
