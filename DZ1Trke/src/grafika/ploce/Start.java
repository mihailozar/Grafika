package grafika.ploce;

import grafika.objekti.Orijentisan;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;

public class Start extends PlocaSaStazom{
    public static final int Horizontalna = 1;
    public static final int Vertikalna = 2;
    private Group grupa;


    public Start(int orijentacija){
        Rectangle staza = new Rectangle(0, pomerajTrake(), VELICINA, sirinaTrake());
        this.kvadar=staza;
        this.setOrijentacija(orijentacija);
        staza.setFill(bojaStaze);
        staza.setStroke(null);
        koren.getChildren().addAll(staza);

        double pomeraj=sirinaTrake()/10;
        for(int i=0; i<10;i++){
            Rectangle gornje;
            Rectangle donje;
            if(i%2==0) {
                gornje = new Rectangle(VELICINA / 3 , pomerajTrake()+(pomeraj * i), (VELICINA/3/2/2) , pomeraj );
                donje=new Rectangle(VELICINA/3+(VELICINA/3/2),pomerajTrake()+(pomeraj * i), (VELICINA/3/2/2) , pomeraj );

            }
            else {
                gornje=new Rectangle(VELICINA/3+(VELICINA/3/4),pomerajTrake()+(pomeraj * i),VELICINA/3/2/2,pomeraj);
                donje=new Rectangle(VELICINA/3*2-(VELICINA/3/4),pomerajTrake()+(pomeraj * i),(VELICINA/3/2)/2 , pomeraj);
            }
            gornje.setFill(bojaOznake);
            gornje.setStroke(null);
            donje.setStroke(null);
            donje.setFill(bojaOznake);


            koren.getChildren().addAll(gornje,donje);
        }
        Rectangle okvir=new Rectangle(VELICINA/3,pomerajTrake(),VELICINA/3,sirinaTrake());
        okvir.setFill(null);
        okvir.setStroke(bojaOznake);
        okvir.setStrokeWidth(2);
        grupa=new Group(okvir);
        koren.getChildren().addAll(grupa);
        for(int i=0;i<4;i++){
            Rectangle gornjaTraka=new Rectangle(0+VELICINA/4*i,pomerajTrake()+sirinaTrake()/2-sirinaTrake()*0.1/2,VELICINA/4,sirinaTrake()*0.1);
            koren.getChildren().addAll(gornjaTraka);
            gornjaTraka.getTransforms().addAll(new Translate(0,sirinaTrake()/2));

            Rectangle donjaTraka=new Rectangle(0+VELICINA/4*i,pomerajTrake()+sirinaTrake()/2-sirinaTrake()*0.1/2,VELICINA/4,sirinaTrake()*0.1);
            koren.getChildren().addAll(donjaTraka);
            donjaTraka.getTransforms().addAll(new Translate(0,-sirinaTrake()/2));
            if(i%2==0){
                gornjaTraka.setFill(Color.RED);
                donjaTraka.setFill(Color.RED);
            }else{
                gornjaTraka.setFill(Color.LIGHTGRAY);
                donjaTraka.setFill(Color.LIGHTGRAY);
            }
        }
    }

    @Override
    public Group dohvKoren(){

        return grupa;
    }
}
