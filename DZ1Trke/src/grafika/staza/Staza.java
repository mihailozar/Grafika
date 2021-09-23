package grafika.staza;

import grafika.ploce.Ploca;
import grafika.ploce.PlocaSaStazomKrivina;
import grafika.ploce.RaskrsnicaSaStazom;
import grafika.ploce.Start;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.transform.Translate;

public class Staza extends Group {
    private Ploca[][]	matricaPloca;
    private Ploca start;
    private int vrsta;
    private int kolona;
    private int poslednjaPloc;

    public  Ploca getStart(){return start;}
    public Staza(int velX, int velY)
    {
        matricaPloca = new Ploca[velX][velY];
        this.vrsta=velX;
        this.kolona=velY;
    }

    public Staza()
    {
        this(20, 20);
    }

    public void postaviPoslednjuPlocicu(int i){
        poslednjaPloc=i;
    }
    public int dohvatiPoslednjuPoz(){
        return poslednjaPloc;
    }

    public void setPosition (){
        for(int i=0;i<vrsta;i++){
            for(int j=0;j<0;j++){
                if(matricaPloca[i][j]!=null)
                    matricaPloca[i][j].setPosition();
            }
        }
    }

    public void postaviPlocu(int vrsta, int kolona, Ploca p, int i)
    {

        p.rednibr=i;
        if(p instanceof Start){
            start=p;

        }
        if( matricaPloca[vrsta][kolona] != null )
            getChildren().remove( matricaPloca[vrsta][kolona] );

        matricaPloca[vrsta][kolona] = p;
        getChildren().add(p);
        p.getTransforms().setAll( new Translate(kolona*Ploca.VELICINA, vrsta*Ploca.VELICINA) );
    }

    public Ploca getByOrder(int i){
        for(int v=0;v<vrsta;v++){
            for (int k=0;k<kolona;k++){
                if(matricaPloca[v][k]!=null && matricaPloca[v][k].rednibr==i){
                    return matricaPloca[v][k];
                }else if(matricaPloca[v][k] instanceof RaskrsnicaSaStazom && ((RaskrsnicaSaStazom) matricaPloca[v][k]).drugiBr==i){
                    return matricaPloca[v][k];
                }
            }
        }
        return  null;
    }

    public boolean izasao(Bounds formula){
//vertikalna 2 horizontalna 1

        for(int i=0;i<vrsta;i++){
            for(int j=0;j<kolona;j++){
                if(matricaPloca[i][j]!=null){
                    Bounds ploca=matricaPloca[i][j].localToScene(matricaPloca[i][j].getBoundsInLocal());
                    if(ploca.contains(formula)){
                        return false;
                    }else if(ploca.intersects(formula) ){
                            if(matricaPloca[i][j].orijentacija==2){
                                if(ploca.getMinX()>formula.getMinX() || ploca.getMaxX()<formula.getMaxX()){
                                    return true;
                                }else{
                                    return  false;
                                }
                            }else if(matricaPloca[i][j].orijentacija==1) {
                                if(ploca.getMinY()>formula.getMinY() || ploca.getMaxY()<formula.getMaxY()){
                                    return true;
                                }else{
                                    return false;
                                }
                            }else if(matricaPloca[i][j] instanceof PlocaSaStazomKrivina
                                    || matricaPloca[i][j] instanceof RaskrsnicaSaStazom ){
                                return false;
                            }
                    }
                }
            }
        }
        return true;
    }
}
