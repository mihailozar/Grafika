import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import kamere.OriginalnaKamera;
import object.movableObject.Puck;
import object.staticObject.Paddle;
import object.staticObject.Wall;
import javafx.scene.text.Text;
import svetla.Kupa;

public class Main extends Application implements Wall.GameStopper, EventHandler<KeyEvent> {
	
	private static final double WIDTH            = 800;
	private static final double HEIGHT           = 800;
	private static final double NEAR_CLIP        = 0.1;
	private static final double FAR_CLIP         = 5000;
	private static final double CAMERA_Z         = -2500;
	private static final double PUCK_RADIUS      = 20;
	private static final double PUCK_HEIGHT      = 20;
	private static final double LONG_WALL_WIDTH  = 1000;
	private static final double SHORT_WALL_WIDTH = 500;
	private static final double WALL_HEIGHT      = PUCK_HEIGHT;
	private static final double WALL_DEPTH       = 20;
	private static final double PADDLE_WIDTH     = 100;
	private static final double PADDLE_HEIGHT    = 100;
	private static final double PADDLE_DEPTH     = 20;
	private static final double STEP             = 10;
	private              Timer  timer;
	private Paddle leftPaddle, rightPaddle;
	private OriginalnaKamera camera;
	private PerspectiveCamera birdWiewCamera;
	private OriginalnaKamera levaCemara;
	private OriginalnaKamera desnaCamera;
	private Scene scene;
	private Puck puck;
	private Group root;
	private int leviZivoti=5;
	private int desniZivoti=5;
	private Text leviEnergijaText;
	private Text desniEnergijaText;
	private Text leviZivotiText;
	private Text desniZivotiText;
	private SubScene Scenaigra;
	private Group korena=new Group();
	private  SubScene leviRezultat;
	private SubScene desniRezultat;
	private Text Poruka;
	private Kupa levoSvetlo;
	private Kupa desnoSvetlo;
	private int energijaLevi=100;
	private int energijaDesni=100;
	private long vreme=0;
	private int vremeIspis=0;
	private AnimationTimer tajmer;
	private Text tajmerText;

	public static void main ( String[] arguments ) {
		launch ( arguments );
	}
	
	private void createCameras ( ) {
		this.camera=new OriginalnaKamera(true);
		camera.rotacijaX=new Rotate(-45, Rotate.X_AXIS);
		camera.translacija=new Translate(0, 0, CAMERA_Z );
		camera.rotacijaY=new Rotate(0,Rotate.Y_AXIS);
		this.camera.setNearClip ( NEAR_CLIP );
		this.camera.setFarClip ( FAR_CLIP );
		this.camera.getTransforms ( ).addAll (
				camera.rotacijaX,
				camera.rotacijaY,
				camera.translacija
		);
	}

	private void CreateLeftCamera(){
		this.levaCemara=new OriginalnaKamera(true);
		levaCemara.rotacijaX=new Rotate(-15, Rotate.X_AXIS);
		levaCemara.rotacijaX.setPivotY(WIDTH/2);
		levaCemara.rotacijaX.setPivotY(HEIGHT/2);
		levaCemara.translacija=new Translate(0, 0, CAMERA_Z );
		levaCemara.rotacijaY=new Rotate(90,Rotate.Y_AXIS);
		levaCemara.rotacijaZ=new Rotate(0,Rotate.Z_AXIS);
		levaCemara.rotacijaZ.setPivotY(WIDTH/2);
		levaCemara.rotacijaZ.setPivotY(HEIGHT/2);
		this.levaCemara.setNearClip ( NEAR_CLIP );
		this.levaCemara.setFarClip ( FAR_CLIP );
		this.levaCemara.getTransforms ( ).addAll (
				levaCemara.rotacijaY,
				levaCemara.rotacijaX,
			levaCemara.translacija
		);
	}
	private void CreateRightCamera(){
		this.desnaCamera=new OriginalnaKamera(true);
		desnaCamera.rotacijaX=new Rotate(-15, Rotate.X_AXIS);
		desnaCamera.rotacijaX.setPivotY(WIDTH/2);
		desnaCamera.rotacijaX.setPivotY(HEIGHT/2);
		desnaCamera.translacija=new Translate(0, 0, CAMERA_Z );
		desnaCamera.rotacijaY=new Rotate(-90,Rotate.Y_AXIS);
		desnaCamera.rotacijaZ=new Rotate(0,Rotate.Z_AXIS);
		desnaCamera.rotacijaZ.setPivotY(WIDTH/2);
		desnaCamera.rotacijaZ.setPivotY(HEIGHT/2);
		this.desnaCamera.setNearClip ( NEAR_CLIP );
		this.desnaCamera.setFarClip ( FAR_CLIP );
		this.desnaCamera.getTransforms ( ).addAll (
				desnaCamera.rotacijaY,
				desnaCamera.rotacijaX,
				desnaCamera.translacija
		);
	}


	private void createBirdWievCamera ( ) {
		this.birdWiewCamera = new PerspectiveCamera ( true );
		this.birdWiewCamera.setNearClip ( NEAR_CLIP );
		this.birdWiewCamera.setFarClip ( FAR_CLIP );
		this.birdWiewCamera.getTransforms ( ).addAll (

				new Translate ( 0, -2000, 0 ),
				new Rotate ( -90, Rotate.X_AXIS )
		);
	}

	private Scene getScene ( ) {
		 this.root = new Group ( );
		Scenaigra=new SubScene(root,WIDTH,HEIGHT,true, SceneAntialiasing.BALANCED);
		leviEnergijaText =new Text(String.format("energija: %d", energijaLevi));
		leviEnergijaText.setFill(Color.RED);
		leviEnergijaText.setFont(Font.font(20));
		leviZivotiText=new Text(String.format("zivoti: %d",leviZivoti));
		leviZivotiText.setFill(Color.RED);
		leviZivotiText.setFont(Font.font(20));
		tajmerText=new Text(Integer.toString((int)vreme));
		tajmerText.setFill(Color.RED);
		tajmerText.setFont(Font.font(20));
		tajmerText.getTransforms().addAll(new Translate(WIDTH/5,HEIGHT/5));
		Group pom=new Group(leviEnergijaText,leviZivotiText,tajmerText);
		leviZivotiText.getTransforms().addAll(new Translate(0,30));
		pom.getTransforms().addAll(new Translate(10,20));
		leviRezultat=new SubScene(pom,WIDTH/3,HEIGHT/3);
		leviRezultat.setCamera(new ParallelCamera());

		desniEnergijaText =new Text(String.format("zivoti: %d\n energija: %d",desniZivoti, energijaDesni));
		desniEnergijaText.setFill(Color.RED);
		desniEnergijaText.setFont(Font.font(20));
		desniZivotiText=new Text(String.format("zivoti: %d",desniZivoti));
		desniZivotiText.setFill(Color.RED);
		desniZivotiText.setFont(Font.font(20));
		pom=new Group(desniEnergijaText,desniZivotiText);
		pom.getTransforms().addAll(new Translate(150,20));
		desniZivotiText.getTransforms().addAll(new Translate(0,30));
		desniRezultat=new SubScene(pom,WIDTH/3,HEIGHT/3);
		desniRezultat.getTransforms().addAll(new Translate(WIDTH/3*2,0));
		desniRezultat.setCamera(new ParallelCamera());



		Poruka=new Text();
		Poruka.getTransforms().addAll(new Translate(WIDTH/2-100,HEIGHT/2));
		Poruka.setFont(Font.font(50));
		Poruka.setFill(Color.RED);

		korena.getChildren().addAll(Scenaigra,leviRezultat,desniRezultat,Poruka);

		this.scene = new Scene ( korena, WIDTH, HEIGHT, true,SceneAntialiasing.BALANCED );
		scene.setFill(Color.BLACK);

		this.puck = new Puck ( PUCK_RADIUS, PUCK_HEIGHT );
		puck.energijaDesno=100;
		puck.energijaLevo=100;
		this.root.getChildren ( ).addAll ( this.puck );
		
		Wall upperWall = new Wall ( LONG_WALL_WIDTH, WALL_HEIGHT, WALL_DEPTH, Wall.Type.UPPER );
		Wall lowerWall = new Wall ( LONG_WALL_WIDTH, WALL_HEIGHT, WALL_DEPTH, Wall.Type.LOWER );
		Wall leftWall  = new Wall ( SHORT_WALL_WIDTH/2, WALL_HEIGHT, WALL_DEPTH, Wall.Type.LEFT, this );
		Wall rightWall = new Wall ( SHORT_WALL_WIDTH/2, WALL_HEIGHT, WALL_DEPTH, Wall.Type.RIGHT, this );

		Wall leftWallUpper=new Wall(SHORT_WALL_WIDTH/4, WALL_HEIGHT, WALL_DEPTH, Wall.Type.LEFT);
		Wall rightWallUpper=new Wall(SHORT_WALL_WIDTH/4, WALL_HEIGHT, WALL_DEPTH, Wall.Type.RIGHT);
		Wall leftWallLower=new Wall(SHORT_WALL_WIDTH/4, WALL_HEIGHT, WALL_DEPTH, Wall.Type.LEFT);
		Wall rightWallLower=new Wall(SHORT_WALL_WIDTH/4, WALL_HEIGHT, WALL_DEPTH, Wall.Type.RIGHT);
		Rectangle pod=new Rectangle(-LONG_WALL_WIDTH/2,-SHORT_WALL_WIDTH/2,LONG_WALL_WIDTH,SHORT_WALL_WIDTH);
		pod.getTransforms().addAll(new Rotate(-90,Rotate.X_AXIS));
		pod.setFill(Color.LIGHTCYAN);
		Rectangle sredina=new Rectangle(-5,-SHORT_WALL_WIDTH/2,10,SHORT_WALL_WIDTH);
		sredina.setFill(Color.RED);
		sredina.getTransforms().addAll(new Rotate(-90,Rotate.X_AXIS));

		Circle krug=new Circle(0,0,100);
		krug.setFill(null);
		krug.setStrokeWidth(10);
		krug.setStroke(Color.RED);
		krug.getTransforms().addAll(new Translate(0,-1),new Rotate(-90,Rotate.X_AXIS));

		Arc leviPolukrug=new Arc(0,0,125,125,-90,180);
		leviPolukrug.setStrokeWidth(10);
		leviPolukrug.setStroke(Color.RED);
		leviPolukrug.setFill(null);
		leviPolukrug.getTransforms().addAll(new Translate(-(LONG_WALL_WIDTH/2-WALL_DEPTH),-1),new Rotate(-90,Rotate.X_AXIS));

		Arc desniPolukrug=new Arc(0,0,125,125,90,180);
		desniPolukrug.setStrokeWidth(10);
		desniPolukrug.setStroke(Color.RED);
		desniPolukrug.setFill(null);
		desniPolukrug.getTransforms().addAll(new Translate((LONG_WALL_WIDTH/2-WALL_DEPTH),-1),new Rotate(-90,Rotate.X_AXIS));

		leftWallUpper.getTransforms().addAll(
				new Translate ( -( LONG_WALL_WIDTH / 2 - WALL_DEPTH / 2 ), 0, SHORT_WALL_WIDTH/8*3 ),
				new Rotate ( 90, Rotate.Y_AXIS )
		);
		rightWallUpper.getTransforms().addAll(
				new Translate ( LONG_WALL_WIDTH / 2 - WALL_DEPTH / 2, 0, SHORT_WALL_WIDTH/8*3 ),
				new Rotate ( 270, Rotate.Y_AXIS )
		);
		leftWallLower.getTransforms().addAll(
				new Translate ( -( LONG_WALL_WIDTH / 2 - WALL_DEPTH / 2 ), 0, -SHORT_WALL_WIDTH/8*3 ),
				new Rotate ( 90, Rotate.Y_AXIS )
		);
		rightWallLower.getTransforms().addAll(
				new Translate ( LONG_WALL_WIDTH / 2 - WALL_DEPTH / 2, 0, -SHORT_WALL_WIDTH/8*3 ),
				new Rotate ( 270, Rotate.Y_AXIS )
		);
		upperWall.getTransforms ( ).addAll (
				new Translate ( 0, 0, SHORT_WALL_WIDTH / 2 + WALL_DEPTH / 2 )
				,new Rotate(180,Rotate.Y_AXIS)
		);
		lowerWall.getTransforms ( ).addAll (
				new Translate ( 0, 0, -( SHORT_WALL_WIDTH / 2 + WALL_DEPTH / 2 ) )
		);
		leftWall.getTransforms ( ).addAll (
				new Translate ( -( LONG_WALL_WIDTH / 2 - WALL_DEPTH / 2 ), 0, 0 ),
				new Rotate ( 90, Rotate.Y_AXIS )
		);
		rightWall.getTransforms ( ).addAll (
				new Translate ( LONG_WALL_WIDTH / 2 - WALL_DEPTH / 2, 0, 0 ),
				new Rotate ( 90, Rotate.Y_AXIS )
		);
		levoSvetlo=new Kupa();
		desnoSvetlo=new Kupa();
		this.root.getChildren ( ).addAll ( upperWall, lowerWall, leftWall, rightWall,
				leftWallUpper,rightWallUpper,rightWallLower,leftWallLower,pod ,sredina,
				leviPolukrug,desniPolukrug,krug,levoSvetlo,desnoSvetlo);
		
		this.leftPaddle = new Paddle ( PADDLE_WIDTH, PADDLE_HEIGHT, PADDLE_DEPTH );
		this.rightPaddle = new Paddle ( PADDLE_WIDTH, PADDLE_HEIGHT, PADDLE_DEPTH );
		this.leftPaddle.getTransforms ( ).addAll (
				new Translate ( -LONG_WALL_WIDTH / 2 * 0.8, -10, 0 ),
				new Rotate ( 90, Rotate.Y_AXIS )
		);
		this.rightPaddle.getTransforms ( ).addAll (
				new Translate ( LONG_WALL_WIDTH / 2 * 0.8, -10, 0 ),
				new Rotate ( 90, Rotate.Y_AXIS )
		);


		levoSvetlo.getTransforms().addAll(new Translate(leftPaddle.localToScene(leftPaddle.getBoundsInLocal()).getCenterX(),-300,0));
		desnoSvetlo.getTransforms().addAll(new Translate(rightPaddle.localToScene(rightPaddle.getBoundsInLocal()).getCenterX(),-300,0));
		
		this.root.getChildren ( ).addAll ( this.leftPaddle, this.rightPaddle );
		
		this.createCameras ( );
		this.createBirdWievCamera();
		this.CreateLeftCamera();
		this.CreateRightCamera();
		this.Scenaigra.setCamera ( this.camera );
		
		this.timer = new Timer ( this.puck, leviEnergijaText, desniEnergijaText, upperWall, lowerWall, leftWall, rightWall,
				leftWallUpper,rightWallUpper,rightWallLower,leftWallLower,
				this.rightPaddle, this.leftPaddle );
		this.timer.start ( );
		this.tajmer=new AnimationTimer() {
			@Override
			public void handle(long l) {
				if ( vreme == 0 ) {
					vreme = l;
				}

				double dt = ( l - vreme ) / 1e9;

				if(dt>1){
					vremeIspis+=1;
					tajmerText.setText(Integer.toString(vremeIspis));
					vreme=l;
				}

				if(vremeIspis==60){

					this.stop();
					timer.stop();
					
				}
			}
		};
		tajmer.start();
		
		this.scene.addEventHandler ( KeyEvent.KEY_PRESSED, keyEvent -> handle(keyEvent) );
		this.scene.setOnMouseClicked(e->inicXY(e));
		this.scene.setOnMouseDragged(e->pomeranjeKamere(e));
		this.scene.setOnScroll(e->zumiranje(e));
		
		return scene;
	}
	private double x,y;
	private  void pomeranjeKamere(MouseEvent e){
		double dx=e.getSceneX()-this.x;
		double dy=e.getSceneY()-this.y;

		this.x=e.getSceneX();
		this.y= e.getSceneY();

		camera.rotacijaX.setAngle(camera.rotacijaX.getAngle()+dy*0.05);
		camera.rotacijaY.setAngle(camera.rotacijaY.getAngle()+dx*0.05);


	}
	private void inicXY(MouseEvent e){
		this.x=e.getSceneX();
		this.y=e.getSceneY();
	}

	private void zumiranje(ScrollEvent e){
		final double STEP=10;
		double y=e.getDeltaY();
		double step=STEP*(y>0?1:-1);
		camera.translacija.setZ(camera.translacija.getZ()+step);
	}
	
	@Override public void start ( Stage primaryStage ) throws Exception {
		primaryStage.setScene ( this.getScene ( ) );
		primaryStage.setTitle ( "Vazdusni hokej" );
		primaryStage.show ( );
	}

	
	@Override public void stopGame ( ) {
		Bounds kordinatePuck=puck.localToScene(puck.getBoundsInLocal());
		azuriranjeRezultata(kordinatePuck.getCenterX()>0?1:-1);

		this.root.getChildren().remove(this.puck);
		this.puck=new Puck(PUCK_RADIUS,PUCK_HEIGHT);
		puck.energijaDesno=energijaDesni;
		puck.energijaLevo=energijaLevi;
		this.root.getChildren().addAll(this.puck);
		this.timer.setMovableObject(this.puck);
//	this.timer.stop ( );
	}
	private void azuriranjeRezultata(int i){
		if(i==1){
			desniZivoti--;
			puck.energijaLevo=200;
			energijaLevi=200;
			desniEnergijaText.setText(String.format("energija: %d",puck.energijaDesno));
			desniZivotiText.setText(String.format("zivoti: %d",desniZivoti));
			leviEnergijaText.setText(String.format("energija: %d", puck.energijaLevo));

		}else{
			leviZivoti--;
			puck.energijaDesno= 200;
			energijaDesni=200;
			leviEnergijaText.setText(String.format("energija: %d", puck.energijaLevo));
			desniEnergijaText.setText(String.format("energija: %d",puck.energijaDesno));
			leviZivotiText.setText(String.format("zivoti: %d",leviZivoti));
		}

		if(desniZivoti==0){
			Poruka.setText("Left Win");
			timer.stop();
			this.tajmer.stop();
		}else if(leviZivoti==0){
			Poruka.setText("Right Win");
			timer.stop();
			this.tajmer.stop();
		}
	}
	@Override public void handle ( KeyEvent event ) {
		if ( event.getCode ( ).equals ( KeyCode.W ) ) {
			double newZ = this.leftPaddle.getZ ( ) + STEP;
			
			if ( ( newZ + PADDLE_WIDTH / 2 ) <= SHORT_WALL_WIDTH / 2 ) {
				this.leftPaddle.move ( 0, 0, STEP );
				levaCemara.translacija.xProperty().setValue(
						levaCemara.translacija.xProperty().get()- STEP);
			}
		} else if ( event.getCode ( ).equals ( KeyCode.S ) ) {
			double newZ = this.leftPaddle.getZ ( ) - STEP;
			
			if ( ( newZ - PADDLE_WIDTH / 2 ) >= -SHORT_WALL_WIDTH / 2 ) {
				this.leftPaddle.move ( 0, 0, -STEP );
				levaCemara.translacija.xProperty().setValue(
						levaCemara.translacija.xProperty().get()+STEP);
			}
		} else if ( event.getCode ( ).equals ( KeyCode.UP ) ) {
			double newZ = this.rightPaddle.getZ ( ) + STEP;
			
			if ( ( newZ + PADDLE_WIDTH / 2 ) <= SHORT_WALL_WIDTH / 2 ) {
				this.rightPaddle.move ( 0, 0, STEP );
				desnaCamera.translacija.xProperty().setValue(
						desnaCamera.translacija.xProperty().get()+ STEP);
			}
		} else if ( event.getCode ( ).equals ( KeyCode.DOWN ) ) {
			double newZ = this.rightPaddle.getZ ( ) - STEP;
			
			if ( ( newZ - PADDLE_WIDTH / 2 ) >= -SHORT_WALL_WIDTH / 2 ) {
				this.rightPaddle.move ( 0, 0, -STEP );
				desnaCamera.translacija.xProperty().setValue(
						desnaCamera.translacija.xProperty().get()- STEP);
			}
		}else if(event.getCode().equals(KeyCode.DIGIT2)){
			this.Scenaigra.setCamera(this.birdWiewCamera);
		}else if(event.getCode().equals(KeyCode.DIGIT1)){
			this.Scenaigra.setCamera(this.camera);
		}else if(event.getCode().equals(KeyCode.ESCAPE)){
			this.timer.stop();
		}else if(event.getCode().equals(KeyCode.DIGIT3)){
			this.Scenaigra.setCamera(this.levaCemara);
		}else if(event.getCode().equals(KeyCode.DIGIT4)){
			this.Scenaigra.setCamera(this.desnaCamera);
		}else if(event.getCode().equals(KeyCode.DIGIT9)){
			this.levoSvetlo.svetlo.setLightOn(!levoSvetlo.svetlo.isLightOn());
		}else if(event.getCode().equals(KeyCode.DIGIT0)){
			this.desnoSvetlo.svetlo.setLightOn(!desnoSvetlo.svetlo.isLightOn());		}
	}
}
