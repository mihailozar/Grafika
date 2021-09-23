package object.staticObject;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Translate;
import object.movableObject.MovableObject;

public class Paddle extends StaticObject {
	private Translate translate;
	private Box base;
	
	public Paddle ( double width, double height, double depth ) {
//		Box paddle = new Box ( width, height, depth );
//		paddle.setMaterial ( new PhongMaterial ( Color.GREEN ) );
//		super.getChildren ( ).addAll ( paddle );

		base=new Box(0.4*width,height,depth);
		PhongMaterial material=new PhongMaterial(Color.GREEN);
		base.setMaterial(material);
		base.getTransforms().addAll(new Translate(0,-0.3*height,0));

		float w=(float) width;
		float h=(float) height;
		float d=(float) depth;

		float firstPoint[]={
				0.2f*w,0.2f*h,-0.5f*d,
				0.5f*w,0.2f*h,-0.5f*d,
				0.2f*w,-0.2f*h,-0.5f*d,

				0.2f*w,0.2f*h,0.5f*d,
				0.5f*w,0.2f*h,0.5f*d,
				0.2f*w,-0.2f*h,0.5f*d
		};

		float firstTexCoords[]={
				0f,0f
		};

		int firstFaces[]={
				0,0,1,0,2,0,
				0,0,2,0,1,0,

				5,0,1,0,2,0,
				5,0,2,0,1,0,

				5,0,4,0,1,0,
				5,0,1,0,4,0,

				5,0,4,0,3,0,
				5,0,3,0,4,0,

				4,0,1,0,0,0,
				4,0,0,0,1,0,

				4,0,0,0,3,0,
				4,0,3,0,0,0


		};

		TriangleMesh firstTriangleMash=new TriangleMesh();
		firstTriangleMash.getPoints().addAll(firstPoint);
		firstTriangleMash.getTexCoords().addAll(firstTexCoords);
		firstTriangleMash.getFaces().addAll(firstFaces);
		MeshView firstMeshView=new MeshView(firstTriangleMash);
		firstMeshView.setMaterial(material);

		super.getChildren().addAll(base,firstMeshView);
		this.translate = new Translate ( );
		float secondPoint[]={
				-0.2f*w,0.2f*h,-0.5f*d,
				-0.5f*w,0.2f*h,-0.5f*d,
				-0.2f*w,-0.2f*h,-0.5f*d,

				-0.2f*w,0.2f*h,0.5f*d,
				-0.5f*w,0.2f*h,0.5f*d,
				-0.2f*w,-0.2f*h,0.5f*d
		};

		float secondTexCoords[]={
				0f,0f
		};

		int secondFaces[]={
				0,0,1,0,2,0,
				0,0,2,0,1,0,

				5,0,1,0,2,0,
				5,0,2,0,1,0,

				5,0,4,0,1,0,
				5,0,1,0,4,0,

				5,0,4,0,3,0,
				5,0,3,0,4,0,

				4,0,1,0,0,0,
				4,0,0,0,1,0,

				4,0,0,0,3,0,
				4,0,3,0,0,0


		};

		TriangleMesh secondTriangleMash=new TriangleMesh();
		secondTriangleMash.getPoints().addAll(secondPoint);
		secondTriangleMash.getTexCoords().addAll(secondTexCoords);
		secondTriangleMash.getFaces().addAll(secondFaces);
		MeshView secondMeshView=new MeshView(secondTriangleMash);
		secondMeshView.setMaterial(material);

		super.getChildren().addAll(secondMeshView);


		super.getTransforms ( ).addAll (
				this.translate
		);
	}
	
	@Override public boolean collision ( MovableObject movableObject ) {
		boolean collisionDetected = super.collision ( movableObject );
		
		if ( collisionDetected ) {
			movableObject.invertSpeedX ( );
			if(base.localToScene(base.getBoundsInLocal()).getCenterX()>0){
				movableObject.energijaDesno=movableObject.energijaDesno>=120?movableObject.energijaDesno-20:100;
				movableObject.udario=true;
			}else{
				movableObject.energijaLevo=movableObject.energijaLevo>=120?movableObject.energijaLevo-20:100;
				movableObject.udario=false;
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
