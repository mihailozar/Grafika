package grafika.ploce;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class RaskrsnicaSaStazom extends Raskrsnica{

    public RaskrsnicaSaStazom(int orijentacija ){

        Rectangle horizontalna = new Rectangle(0, pomerajTrake(), VELICINA, sirinaTrake());
        Rectangle vertikalna=new Rectangle(0,pomerajTrake(),VELICINA,sirinaTrake());
        horizontalna.setFill(bojaStaze);
        vertikalna.setFill(bojaStaze);
        horizontalna.setStroke(null);
        vertikalna.setStroke(null);
        Rectangle traka2=new Rectangle(0,pomerajTrake()+sirinaTrake()/2-sirinaTrake()*0.1/2,VELICINA,sirinaTrake()*0.1);
        traka2.setFill(Color.LIGHTGRAY);
        Rectangle gornjaTraka=new Rectangle(0,pomerajTrake()+sirinaTrake()/2-sirinaTrake()*0.1/2,VELICINA,sirinaTrake()*0.1);
//        koren.getChildren().addAll(gornjaTraka);
        gornjaTraka.setFill(Color.LIGHTGRAY);
        gornjaTraka.getTransforms().addAll(new Translate(0,sirinaTrake()/2));

        Rectangle donjaTraka=new Rectangle(0,pomerajTrake()+sirinaTrake()/2-sirinaTrake()*0.1/2,VELICINA,sirinaTrake()*0.1);
//        koren.getChildren().addAll(donjaTraka);
        donjaTraka.setFill(Color.LIGHTGRAY);
        donjaTraka.getTransforms().addAll(new Translate(0,-sirinaTrake()/2));

        Rectangle dolemalo=new Rectangle(0,pomerajTrake()+sirinaTrake()/2-sirinaTrake()*0.1/2,VELICINA/6,sirinaTrake()*0.1);
        dolemalo.getTransforms().addAll(new Translate(0,-sirinaTrake()/2));
        dolemalo.setFill(Color.LIGHTGRAY);
        Rectangle dolemalo1=new Rectangle(0+VELICINA/6*5,pomerajTrake()+sirinaTrake()/2-sirinaTrake()*0.1/2,VELICINA/6,sirinaTrake()*0.1);
        dolemalo1.getTransforms().addAll(new Translate(0,-sirinaTrake()/2));
        dolemalo1.setFill(Color.LIGHTGRAY);

        Rectangle dolemalo2=new Rectangle(0,pomerajTrake()+sirinaTrake()/2-sirinaTrake()*0.1/2,VELICINA/6,sirinaTrake()*0.1);
        dolemalo2.getTransforms().addAll(new Translate(0,sirinaTrake()/2));
        dolemalo2.setFill(Color.LIGHTGRAY);
        Rectangle dolemalo3=new Rectangle(0+VELICINA/6*5,pomerajTrake()+sirinaTrake()/2-sirinaTrake()*0.1/2,VELICINA/6,sirinaTrake()*0.1);
        dolemalo3.getTransforms().addAll(new Translate(0,sirinaTrake()/2));
        dolemalo3.setFill(Color.LIGHTGRAY);

        Group pom=new Group();
        pom.getChildren().addAll(vertikalna,traka2,dolemalo,dolemalo1,dolemalo2,dolemalo3);
        Translate t = new Translate(VELICINA, 0);
        Rotate r = new Rotate(90);
        pom.getTransforms().addAll(t, r);
        koren.getChildren().addAll(horizontalna,gornjaTraka,donjaTraka,pom);


        Rectangle traka=new Rectangle(0,pomerajTrake()+sirinaTrake()/2-sirinaTrake()*0.1/2,VELICINA,sirinaTrake()*0.1);
        traka.setFill(Color.LIGHTGRAY);

        koren.getChildren().addAll(traka);






    }
}
