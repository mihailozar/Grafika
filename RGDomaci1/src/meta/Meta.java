package meta;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.util.Duration;


public class Meta extends Group {
    private Point2D position;

    private Point2D speed;
    private double maxRadius;
    public int br;
    public Scale scale;
    private boolean flag=false;
    private double second;
    public FadeTransition prozirnost;



    long then;
    public Meta(int br){
        this.br=br;
        position=new Point2D(0,0);
        maxRadius=RADIUS*br;
        speed=new Point2D(Math.random()*80-40,Math.random()*80-40);
        Group pom=new Group();
        this.getChildren().addAll(pom);
        Color bojaCentar= boje[(int)(Math.random()*3)];
        int pomBr=br;
        for (int i=1;i<=pomBr;pomBr--){
           // Group pom1=new Group();
           // pom.getChildren().addAll(pom1);
            if(pomBr==1){

                pom.getChildren().addAll( new Circle(RADIUS*pomBr,bojaCentar));
                Text broj=new Text(0,0,String.format("%d",150));
                broj.setFill(Color.BLACK);
                pom.getChildren().addAll(broj);

            }else if(pomBr%2==0){

                Circle krug=new Circle(RADIUS*pomBr,Color.WHITE);
                krug.setStroke(Color.BLACK);
                Text broj=new Text(RADIUS*(pomBr-1),0,String.format("%d",(br-pomBr+1)*20));
                broj.setFill(bojaCentar);
                pom.getChildren().addAll(krug,broj);



            }else if(pomBr%2==1){

                pom.getChildren().addAll( new Circle(RADIUS*pomBr,Color.BLACK));
                Text broj=new Text(RADIUS*(pomBr-1),0,String.format("%d",(br-pomBr+1)*20));
                broj.setFill(bojaCentar);
                pom.getChildren().addAll(broj);


            }
//            pom=pom1;


        }

        prozirnost=new FadeTransition(Duration.seconds(10),this);
        prozirnost.setFromValue(1.0);
        prozirnost.setToValue(0);
        prozirnost.play();

        scale=new Scale(0.5,0.5);
        pom.getTransforms().addAll(scale);

//        ScaleTransition skaliranje=new ScaleTransition(Duration.seconds(5),this);
//        skaliranje.setFromX(0.5);
//        skaliranje.setFromY(0.5);
//        skaliranje.setToY(1.5);
//        skaliranje.setToX(1.5);
//        skaliranje.setCycleCount(ScaleTransition.INDEFINITE);
//        skaliranje.setAutoReverse(true);
//        skaliranje.play();


    }


    private static  Color boje[]={
            Color.YELLOW,
            Color.RED,
            Color.GREEN,
            Color.BLUE
    };



    private static final double RADIUS=25;

    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    public Point2D getSpeed() {
        return speed;
    }

    public void setSpeed(Point2D speed) {
        this.speed = speed;
    }

    public double getMaxRadius() {
        return maxRadius;
    }

    public void setMaxRadius(double maxRadius) {
        this.maxRadius = maxRadius;
    }

    public void update(long now,double width, double height, int nivo){
        if(this.then==0){this.then=now;
        second=now;
        }
        double dt=(now-this.then)/1e9;//dobijene sekunde
        double dt1=(now-second)/1e9;
        if(dt1>0.2){
            if(flag==false){
                scale.setX(scale.getX()+0.01);
                scale.setY(scale.getY()+0.01);
                if(scale.getX()>1.5)flag=true;
            }else if(flag==true){
                scale.setX(scale.getX()-0.01);
                scale.setY(scale.getY()-0.01);
                if(scale.getX()<0.5)flag=false;
            }
            second=now;
        }
        this.then=now;//now je tekuce treve u nano sekundama;



        if(this.localToScene(this.getBoundsInLocal()).getMaxX()>=width ||
                this.localToScene(this.getBoundsInLocal()).getMinX()<=0
        ){
            this.speed=new Point2D(-this.speed.getX(),this.speed.getY());
        }
        if(this.localToScene(this.getBoundsInLocal()).getMaxY()>=height  ||
                this.localToScene(this.getBoundsInLocal()).getMinY()<=0){
            this.speed=new Point2D(this.speed.getX(),-this.speed.getY());
        }

        this.position=this.position.add(this.speed.multiply(dt*nivo*2));

            super.getTransforms().setAll(

                    new Translate(this.position.getX(), this.position.getY()));


    }
}
