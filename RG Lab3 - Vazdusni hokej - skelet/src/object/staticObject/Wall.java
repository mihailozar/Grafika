package object.staticObject;

import javafx.animation.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Translate;
import javafx.util.Duration;
import object.movableObject.MovableObject;

public class Wall extends StaticObject {
	private Type        type;
	private GameStopper gameStopper;
	private Timeline tranzicija;
	private Image slika;
	private Timeline pomeranje;
	private Translate translacija;

	private Box wall;
	
	public Wall ( double width, double height, double depth, Type type, GameStopper gameStopper ) {
		 wall = new Box ( width, height, depth );
		PhongMaterial material;
		if(gameStopper!=null){
			material=new PhongMaterial(Color.BLUE);
			wall.setMaterial(material);
		}else{
			material=new PhongMaterial();
			slika=new Image("slike/wall.jpg");
			material.setDiffuseMap(slika);

		wall.setMaterial ( material );
		tranzicija=new Timeline();
		tranzicija.getKeyFrames().addAll(
			new KeyFrame(Duration.ZERO,new KeyValue(material.diffuseColorProperty(),Color.RED)),
				new KeyFrame(Duration.seconds(2), new KeyValue(material.diffuseColorProperty(),Color.WHEAT)),
				new KeyFrame(Duration.seconds(2),new KeyValue(material.diffuseMapProperty(),slika))
		);
		}
		translacija=new Translate(0,0,0);
		wall.getTransforms().addAll(translacija);
		pomeranje=new Timeline();
		pomeranje.getKeyFrames().addAll(
				new KeyFrame(Duration.ZERO, new KeyValue(wall.translateZProperty(),-10)),
						new KeyFrame(Duration.seconds(2), new KeyValue(wall.translateZProperty(),0))
				);

		super.getChildren ( ).addAll ( wall );
		this.type = type;
		this.gameStopper = gameStopper;
	}
	
	public Wall ( double width, double height, double depth, Type type ) {
		this ( width, height, depth, type, null );
	}
	
	@Override public boolean collision ( MovableObject movableObject ) {
		boolean collisionDetected = super.collision ( movableObject );
		
		if ( collisionDetected ) {
			switch ( type ) {
				case UPPER:
				case LOWER: {
					movableObject.invertSpeedZ ( );
					tranzicija.play();
					pomeranje.play();
					break;
				}
				case LEFT:
				case RIGHT: {
					if ( this.gameStopper != null ) {
						this.gameStopper.stopGame ( );

					}else{
						movableObject.invertSpeedX();
						tranzicija.play();
						pomeranje.play();
					}
					break;
				}

			}
		}
		
		return collisionDetected;
	}
	
	public enum Type {
		UPPER, LOWER, LEFT, RIGHT,LEFTUPPER,LEFTLOWER,RIGHTUPPER,RIGHTLOWER;
	}
	
	
	public static interface GameStopper {
		void stopGame ( );
	}
}