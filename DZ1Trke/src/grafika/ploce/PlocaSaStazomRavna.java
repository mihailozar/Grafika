package grafika.ploce;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class PlocaSaStazomRavna extends PlocaSaStazom{
    public static final int Horizontalna = 1;
    public static final int Vertikalna = 2;

    public PlocaSaStazomRavna(int orijentacija )
    {

        Rectangle staza = new Rectangle(0, pomerajTrake(), VELICINA, sirinaTrake());
        staza.setFill(bojaStaze);
        staza.setStroke(null);
        koren.getChildren().addAll(staza);

        final int n=4;
        for (int i=0;i<n;i++){
            if(i%2==0){
                Rectangle traka=new Rectangle(0+VELICINA/n*i,pomerajTrake()+sirinaTrake()/2-sirinaTrake()*0.1/2,VELICINA/n,sirinaTrake()*0.1);
                traka.setFill(Color.LIGHTGRAY);
                koren.getChildren().addAll(traka);
            }
        }

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






        switch(orijentacija)
        {
            case Horizontalna:
                this.setOrijentacija(1);
                break;
            case Vertikalna:
                this.setOrijentacija(2);
                Translate t = new Translate(VELICINA, 0);
                Rotate r = new Rotate(90);
                koren.getTransforms().addAll(t, r);
                break;
        }


    }
}
