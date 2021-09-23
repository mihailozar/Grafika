import javafx.animation.AnimationTimer;
import javafx.scene.text.Text;
import object.movableObject.MovableObject;
import object.staticObject.StaticObject;

import java.util.Arrays;
import java.util.List;

public class Timer extends AnimationTimer {
	private MovableObject      movableObject;
	private List<StaticObject> staticObjects;
	private long               previous;
	private Text levi;
	private Text desni;

	
	public Timer (MovableObject movableObject, Text levi, Text desni, StaticObject... staticObjects ) {
		this.movableObject = movableObject;
		this.staticObjects = Arrays.asList ( staticObjects );
		this.levi=levi;
		this.desni=desni;


	}
	
	@Override public void handle ( long now ) {
		if ( this.previous == 0 ) {
			this.previous = now;
		}
		
		double dt = ( now - this.previous ) / 1e9;
		this.previous = now;
		
		this.movableObject.update ( dt );

		desni.setText(String.format(" energija: %d",movableObject.energijaDesno));
		levi.setText(String.format(" energija: %d", movableObject.energijaLevo));
		this.staticObjects.forEach ( staticObject -> staticObject.collision ( this.movableObject ) );
	}
	public void setMovableObject(MovableObject movableObject){
		this.movableObject=movableObject;
	}
}
