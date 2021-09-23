package grafika.ploce;

import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class PlocaSaStazomKrivina extends PlocaSaStazom{
    public static final int prva = 0;
    public static final int druga = 1;
    public static final int treca = 2;
    public static final int cetvrta = 3;

    private static Path getArc(double unutrasnjeR, double spoljasnjeR){
        MoveTo m = new MoveTo(0, unutrasnjeR);
        QuadCurveTo c1 = new QuadCurveTo(unutrasnjeR, unutrasnjeR, unutrasnjeR, 0);
        HLineTo l1 = new HLineTo(spoljasnjeR);
        QuadCurveTo c2 = new QuadCurveTo(spoljasnjeR, spoljasnjeR, 0, spoljasnjeR);
        VLineTo l2 = new VLineTo(unutrasnjeR);
        Path p=new Path();
        p.getElements().addAll(m,c1,l1,c2,l2);
        return p;

    }

    public PlocaSaStazomKrivina(int red )
    {
        for(int i = 0; i < red; i++)
        {
            Translate t = new Translate(VELICINA, 0);
            Rotate r = new Rotate(90);
            koren.getTransforms().addAll(t, r);
        }

        double r = pomerajTrake();
        double R = r + sirinaTrake();




        Path p =getArc(r,R);

        p.setFill( bojaStaze );
        p.setStroke(null);
        double trakar=r+(R-r)*0.45;
        double trakaR=r+(R-r)*0.55;
        Path t=getArc(trakar,trakaR);
        t.setFill(Color.LIGHTGRAY);
        t.setStroke(null);

        double doleSpolja=pomerajTrake()-sirinaTrake()*0.1/2;
        double doleUnutra=pomerajTrake()+sirinaTrake()*0.1/2;
        Path ugao=getArc(doleSpolja, doleUnutra);
        ugao.setFill(Color.LIGHTGRAY);
        ugao.setStroke(null);

        double goreSpolja=r + sirinaTrake()+sirinaTrake()*0.1/2;
        double goreUnutra=r + sirinaTrake()-sirinaTrake()*0.1/2;
        Path gornjiUgao=getArc(goreUnutra,goreSpolja);
        gornjiUgao.setFill(Color.LIGHTGRAY);
        gornjiUgao.setStroke(null);

        koren.getChildren().addAll(p,t,ugao,gornjiUgao);
    }
}
