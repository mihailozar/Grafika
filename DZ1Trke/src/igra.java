import grafika.objekti.Formula;
import  grafika.objekti.Formula1;
import  grafika.objekti.Formula2;
import grafika.objekti.FormulaNad;
import grafika.ploce.*;
import grafika.staza.Staza;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.geometry.Bounds;

public class igra extends Application {

    private static final double WIDTH=800;
    private static final double HEIGHT=800;
    private Staza staza;
    private Staza staza2;
    private Staza staza3;
    private Group grupa1;
    private Group grupa2;
    private Group grupa3;
    private SubScene prvaTrka;
    private SubScene drugaTrka;
    private SubScene trecaTrka;
    private Formula1 formula;
    private Formula formula2;
    private Formula2 formula3;
    private int izabranaStaza=0;
    private int izabranaFromula=0;
    private Group root;
    private Rectangle pozadina=new Rectangle(WIDTH,HEIGHT);
    private Scene scene;
    private FormulaNad tekucaFormula;
    private Staza tekucaStaza;
    private Text brzina;
    private int tekucapozicij=1;
    private boolean pobeda=false;
    private boolean poraz=false;
    private int brKamera=1;
    private  Brzinomer brzinomer;
    private PerspektivnaKamera persKamera=new PerspektivnaKamera();
    private SubScene stazaIformula;
    private PerspektivnaKamera trecaKamera=new PerspektivnaKamera();



    private void daLiJeIzasao(Bounds formula){

        if(tekucaStaza.izasao(formula)){
            System.out.println("usao");
            tekucaFormula.skretanje(Math.random()*0.5-0.25);
            tekucaFormula.ubrzaj(0);
        }
    }


    private class MyTimer extends AnimationTimer
    {
        private long prev = 0;
        @Override
        public void handle(long now)
        {
            if( prev == 0 )
                prev = now;

            long delta = now - prev;

            Ploca sledeca=tekucaStaza.getByOrder(tekucapozicij);

            tekucaFormula.pomeri(delta*10e-9f);
            prev = now;
            brzina.setText(String.format("%.2f",tekucaFormula.brzina().intenzitet()));



            if(sledeca!=null){
                Bounds sledecaKord=sledeca.dohvKoren().localToScene(sledeca.getBoundsInLocal());

                Bounds kordFormula=tekucaFormula.localToScene(tekucaFormula.getBoundsInLocal());

                daLiJeIzasao(kordFormula);

                if(tekucapozicij==2 && pobeda==true){
                    brzina.setText("Uspesno zavrseno");
                    this.stop();
                }

                Ploca prethodna=tekucaStaza.getByOrder(tekucapozicij-1);
                Ploca tekuca;

                tekuca=tekucaStaza.getByOrder(tekucapozicij-1);



                if(sledecaKord.intersects(kordFormula)){


                    System.out.println("radi"+tekucapozicij);

                    tekucapozicij++;
                    if(tekucapozicij>tekucaStaza.dohvatiPoslednjuPoz()){
                        tekucapozicij=0;
                        pobeda=true;


                    }
                }


            }

            double maxBrzina=tekucaFormula.maxBrzina;

            double jedanProcenat=maxBrzina/100;
            double procanat=tekucaFormula.brzina().intenzitet()/jedanProcenat;

            double ugao=180.0/100*procanat;
            brzinomer.rotacija.setAngle(ugao);

            sledeca=tekucaStaza.getByOrder(0);
            if(sledeca!=null) {
                Bounds sledecaKord = sledeca.dohvKoren().localToScene(sledeca.getBoundsInLocal());

                Bounds kordFormula = tekucaFormula.localToScene(tekucaFormula.getBoundsInLocal());
                if (sledecaKord.intersects(kordFormula) && tekucapozicij>2) {
                    tekucapozicij=1;
                    poraz=true;
                }
            }

            if(poraz && tekucapozicij==2) {
                brzina.setText("Neuspesno zavrseno");
                this.stop();
            }

            if(brKamera==2){
                persKamera.t1.xProperty().setValue(
                        tekucaFormula.localToScene(tekucaFormula.getBoundsInLocal()).getCenterX()
                );
                persKamera.t1.yProperty().setValue(
                        tekucaFormula.localToScene(tekucaFormula.getBoundsInLocal()).getCenterY()
                );
            }else if(brKamera==3){
                trecaKamera.rz.setAngle(
                       180 -tekucaFormula.orijentacija().azimutDeg()
                );

            }

        }
    }

    private void postaviStazuIFromulu(Staza staza, FormulaNad formula){
        tekucaFormula=formula;

        root.getChildren().removeAll(trecaTrka,drugaTrka,prvaTrka);

        Group root1=new Group();
        brzinomer=new Brzinomer();
        brzinomer.getTransforms().addAll(new Translate(WIDTH/5/2, HEIGHT/5/2));

        Rectangle pravougaonik=new Rectangle(0,0,WIDTH/5,HEIGHT/5);
        pravougaonik.setFill(Color.LIGHTGRAY);
        brzina=new Text(Double.toString(tekucaFormula.brzina().intenzitet()));
        brzina.setFont(Font.font(15));
        brzina.setFill(Color.BLACK);
        brzina.getTransforms().addAll(new Translate(10,150));
        root1.getChildren().addAll(pravougaonik,brzinomer,brzina);

        SubScene novaPodscena=new SubScene(root1,WIDTH/5,HEIGHT/5);
        ParallelCamera kameraPod=new ParallelCamera();
        novaPodscena.setCamera(kameraPod);
        novaPodscena.getTransforms().addAll(new Translate(0,0));
        root.getChildren().addAll(novaPodscena);

            Group root2=new Group();
            Staza pomStaza=tekucaStaza;

                root2.getChildren().addAll(this.tekucaStaza);
                this.tekucaStaza.getTransforms().addAll(
                    new Translate(-150,0),
                    new Translate(WIDTH/2,HEIGHT/2),
                    new Scale(3,3));






                Ploca pom=pomStaza.getStart();

                Bounds granice=pom.kvadar.localToScene(pom.kvadar.getBoundsInLocal());


                formula.pozicija().postaviX(granice.getCenterX());
                formula.pozicija().postaviY(granice.getCenterY());
                formula.orijentacija().rotirajOkoZ(Math.PI*3/2);
                formula.pomeri(0);
                formula.getTransforms().addAll(new Scale(0.5,0.5));
                root2.getChildren().addAll(formula);





                 stazaIformula=new SubScene(root2,WIDTH,HEIGHT);
                root.getChildren().addAll(stazaIformula);

                tekucaStaza.setPosition();

                MyTimer timer =new MyTimer();
                timer.start();

    }

    private void napraviStzu(int vrsta){

        switch (vrsta){
            case 1:{
                staza =new Staza();
                staza.postaviPlocu(5,4, new Start(PlocaSaStazomRavna.Horizontalna),0);

                staza.postaviPlocu(1, 1, new PlocaSaStazomKrivina(PlocaSaStazomKrivina.treca),7);
                staza.postaviPlocu(1, 7, new PlocaSaStazomKrivina(PlocaSaStazomKrivina.cetvrta),13);
                staza.postaviPlocu(5, 1, new PlocaSaStazomKrivina(PlocaSaStazomKrivina.druga),3);
                staza.postaviPlocu(5, 7, new PlocaSaStazomKrivina(PlocaSaStazomKrivina.prva),17);


                staza.postaviPlocu(1, 2, new PlocaSaStazomRavna(PlocaSaStazomRavna.Horizontalna), 8);
                staza.postaviPlocu(1, 3, new PlocaSaStazomRavna(PlocaSaStazomRavna.Horizontalna), 9);
                staza.postaviPlocu(1, 4, new PlocaSaStazomRavna(PlocaSaStazomRavna.Horizontalna), 10);
                staza.postaviPlocu(1, 5, new PlocaSaStazomRavna(PlocaSaStazomRavna.Horizontalna), 11);
                staza.postaviPlocu(1, 6, new PlocaSaStazomRavna(PlocaSaStazomRavna.Horizontalna), 12);
                staza.postaviPlocu(5, 2, new PlocaSaStazomRavna(PlocaSaStazomRavna.Horizontalna), 2);
                staza.postaviPlocu(5, 3, new PlocaSaStazomRavna(PlocaSaStazomRavna.Horizontalna), 1);
                staza.postaviPlocu(5, 5, new PlocaSaStazomRavna(PlocaSaStazomRavna.Horizontalna), 19);
                staza.postaviPlocu(5, 6, new PlocaSaStazomRavna(PlocaSaStazomRavna.Horizontalna), 18);

                staza.postaviPlocu(2, 1, new PlocaSaStazomRavna(PlocaSaStazomRavna.Vertikalna), 6);
                staza.postaviPlocu(3, 1, new PlocaSaStazomRavna(PlocaSaStazomRavna.Vertikalna), 5);
                staza.postaviPlocu(4, 1, new PlocaSaStazomRavna(PlocaSaStazomRavna.Vertikalna), 4);
                staza.postaviPlocu(2, 7, new PlocaSaStazomRavna(PlocaSaStazomRavna.Vertikalna), 14);
                staza.postaviPlocu(3, 7, new PlocaSaStazomRavna(PlocaSaStazomRavna.Vertikalna), 15);
                staza.postaviPlocu(4, 7, new PlocaSaStazomRavna(PlocaSaStazomRavna.Vertikalna), 16);
                staza.postaviPoslednjuPlocicu(19);

            }break;
            case 2:{
                staza2=new Staza();

                staza2.postaviPlocu(3,4, new Start(PlocaSaStazomRavna.Horizontalna), 0);

                //krivine
                staza2.postaviPlocu(1, 5, new PlocaSaStazomKrivina(PlocaSaStazomKrivina.treca), 15);
                staza2.postaviPlocu(1, 7, new PlocaSaStazomKrivina(PlocaSaStazomKrivina.cetvrta), 17);
                staza2.postaviPlocu(3, 1, new PlocaSaStazomKrivina(PlocaSaStazomKrivina.treca), 3);
                staza2.postaviPlocu(3, 7, new PlocaSaStazomKrivina(PlocaSaStazomKrivina.prva), 19);
                staza2.postaviPlocu(5,1,new PlocaSaStazomKrivina(PlocaSaStazomKrivina.druga), 5);
                staza2.postaviPlocu(5,4,new PlocaSaStazomKrivina(PlocaSaStazomKrivina.cetvrta), 8);
                staza2.postaviPlocu(6,4,new PlocaSaStazomKrivina(PlocaSaStazomKrivina.druga), 9);
                staza2.postaviPlocu(6,5,new PlocaSaStazomKrivina(PlocaSaStazomKrivina.prva), 10);
                //horizontalne
                staza2.postaviPlocu(1,6,new PlocaSaStazomRavna(1), 16);
                staza2.postaviPlocu(3,2,new PlocaSaStazomRavna(1), 2);

                staza2.postaviPlocu(3,3,new PlocaSaStazomRavna(1), 1);
//                staza2.postaviPlocu(3,4,new PlocaSaStazomRavna(1));

                staza2.postaviPlocu(3,6,new PlocaSaStazomRavna(1), 20);
                staza2.postaviPlocu(5,2,new PlocaSaStazomRavna(1), 6);
                staza2.postaviPlocu(5,3,new PlocaSaStazomRavna(1), 7);
                //vertikalne
                staza2.postaviPlocu(2,5,new PlocaSaStazomRavna(2), 14);
                staza2.postaviPlocu(2,7,new PlocaSaStazomRavna(2), 18);
                staza2.postaviPlocu(4,1,new PlocaSaStazomRavna(2), 4);
                staza2.postaviPlocu(4,5,new PlocaSaStazomRavna(2), 12);
                staza2.postaviPlocu(5,5,new PlocaSaStazomRavna(2), 11);
                //raskrsnica
                RaskrsnicaSaStazom rask=new RaskrsnicaSaStazom(1);
                rask.setDrugi(21);
                staza2.postaviPlocu(3,5,rask, 13);
                staza2.postaviPoslednjuPlocicu(21);

            }break;
            case 3:{
                staza3=new Staza();
                staza3.postaviPlocu(1,7, new Start(PlocaSaStazomRavna.Horizontalna), 0);

                //krivine
                staza3.postaviPlocu(1, 4, new PlocaSaStazomKrivina(PlocaSaStazomKrivina.treca), 3);
                staza3.postaviPlocu(1, 8, new PlocaSaStazomKrivina(PlocaSaStazomKrivina.cetvrta), 29);
                staza3.postaviPlocu(3, 1, new PlocaSaStazomKrivina(PlocaSaStazomKrivina.treca), 10);
                staza3.postaviPlocu(3, 2, new PlocaSaStazomKrivina(PlocaSaStazomKrivina.cetvrta), 11);
                staza3.postaviPlocu(3,5,new PlocaSaStazomKrivina(PlocaSaStazomKrivina.treca), 20);
                staza3.postaviPlocu(3,9,new PlocaSaStazomKrivina(PlocaSaStazomKrivina.cetvrta), 24);
                staza3.postaviPlocu(4,1,new PlocaSaStazomKrivina(PlocaSaStazomKrivina.druga), 9);
                staza3.postaviPlocu(4,4,new PlocaSaStazomKrivina(PlocaSaStazomKrivina.prva), 6);
                staza3.postaviPlocu(4,8,new PlocaSaStazomKrivina(PlocaSaStazomKrivina.druga), 26);
                staza3.postaviPlocu(4,9,new PlocaSaStazomKrivina(PlocaSaStazomKrivina.prva), 25);
                staza3.postaviPlocu(6,2,new PlocaSaStazomKrivina(PlocaSaStazomKrivina.druga), 14);
                staza3.postaviPlocu(6,5,new PlocaSaStazomKrivina(PlocaSaStazomKrivina.prva), 17);
                //horizontalne
                staza3.postaviPlocu(1,5,new PlocaSaStazomRavna(1), 2);

                staza3.postaviPlocu(1,6,new PlocaSaStazomRavna(1), 1);
//                staza3.postaviPlocu(1,7,new PlocaSaStazomRavna(1));
                staza3.postaviPlocu(3,6,new PlocaSaStazomRavna(1), 21);
                staza3.postaviPlocu(3,7,new PlocaSaStazomRavna(1), 22);
                staza3.postaviPlocu(4,3,new PlocaSaStazomRavna(1), 7);
                staza3.postaviPlocu(6,3,new PlocaSaStazomRavna(1), 15);
                staza3.postaviPlocu(6,4,new PlocaSaStazomRavna(1), 16);
                //vertikalne
                staza3.postaviPlocu(2,4,new PlocaSaStazomRavna(2), 4);
                staza3.postaviPlocu(2,8,new PlocaSaStazomRavna(2), 28);
                staza3.postaviPlocu(3,4,new PlocaSaStazomRavna(2), 5);
                staza3.postaviPlocu(4,5,new PlocaSaStazomRavna(2), 19);
                staza3.postaviPlocu(5,2,new PlocaSaStazomRavna(2), 13);
                staza3.postaviPlocu(5,5,new PlocaSaStazomRavna(2), 18);
                //raskrsnice
                RaskrsnicaSaStazom rask= new RaskrsnicaSaStazom(1);
                rask.setDrugi(27);
                staza3.postaviPlocu(3,8,rask, 23);
                RaskrsnicaSaStazom rask1= new RaskrsnicaSaStazom(1);
                rask1.setDrugi(12);
                staza3.postaviPlocu(4,2,rask1, 8);
                staza3.postaviPoslednjuPlocicu(29);

            }break;
        }
    }
    private void ucitajFormule(){
        formula = new Formula1();

        Group pomGr=new Group();
        pomGr.getChildren().addAll(formula);
        pomGr.getTransforms().addAll(
                new Translate(WIDTH/3/2,HEIGHT/3/2)
                ,new Scale(2,2)
        );
        grupa1.getChildren().addAll(pomGr);

        formula2=new Formula();
        pomGr=new Group();
        pomGr.getChildren().addAll(formula2);
        pomGr.getTransforms().addAll(
                new Translate(WIDTH/3/2,HEIGHT/3/2),new Scale(2,2)
        );
        grupa2.getChildren().addAll(pomGr);

        formula3=new Formula2();
        pomGr=new Group();
        pomGr.getChildren().addAll(formula3);
        pomGr.getTransforms().addAll(
                new Translate(WIDTH/3/2,HEIGHT/3/2),new Scale(2,2)
        );
        grupa3.getChildren().addAll(pomGr);
    }

    private Scene pocetakIgre(double WIDTH, double HEIGHT){
         root=new Group();
        pozadina.setFill(Color.GREEN);
        root.getChildren().addAll(pozadina);
        Text t=new Text(WIDTH/2,30,"Izaberite stazu");
        t.setFont(new Font(30));
        t.setTextAlignment(TextAlignment.JUSTIFY );
        root.getChildren().addAll(t);
        //prva staza
        napraviStzu(1);
        staza.getTransforms().addAll(new Scale(0.25,0.25));
        staza.getTransforms().addAll(new Translate(WIDTH/3/2-40,40+HEIGHT/3/2));
        Rectangle pozadina1=new Rectangle(0,0,WIDTH/3,HEIGHT/3);
        pozadina1.setFill(Color.LIGHTGRAY);
        pozadina1.setStroke(Color.BLACK);
        pozadina1.setStrokeWidth(10);
        grupa1=new Group();
        grupa1.getChildren().addAll(pozadina1, staza);
        prvaTrka=new SubScene(grupa1,WIDTH/3,HEIGHT/3);
        prvaTrka.getTransforms().addAll(new Translate(0,100));

        //druga staza
        napraviStzu(2);
        staza2.getTransforms().addAll(new Scale(0.25,0.25));
        staza2.getTransforms().addAll(new Translate(WIDTH/3-200,HEIGHT/3/2));
        Rectangle pozadina2=new Rectangle(0,0,WIDTH/3,HEIGHT/3);
        pozadina2.setFill(Color.LIGHTGRAY);
        pozadina2.setStroke(Color.BLACK);
        pozadina2.setStrokeWidth(10);
        grupa2=new Group();
        grupa2.getChildren().addAll(pozadina2,staza2);
        drugaTrka=new SubScene(grupa2,WIDTH/3,HEIGHT/3);
        drugaTrka.getTransforms().addAll(new Translate(WIDTH/3,100));

        //treca staza
        napraviStzu(3);
        staza3.getTransforms().addAll(new Scale(0.25,0.25));
        staza3.getTransforms().addAll(new Translate(WIDTH/3-300,HEIGHT/3/2));
        Rectangle pozadina3=new Rectangle(0,0,WIDTH/3,HEIGHT/3);
        pozadina3.setFill(Color.LIGHTGRAY);
        pozadina3.setStroke(Color.BLACK);
        pozadina3.setStrokeWidth(10);
        grupa3=new Group();
        grupa3.getChildren().addAll(pozadina3,staza3);
        trecaTrka=new SubScene(grupa3,WIDTH/3,HEIGHT/3);

        prvaTrka.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if(izabranaStaza==0){
                izabranaStaza=1;
                grupa1.getChildren().remove(staza);
                grupa2.getChildren().remove(staza2);
                grupa3.getChildren().remove(staza3);
                tekucaStaza=staza;
                ucitajFormule();}
            else if(izabranaFromula==0){
                izabranaFromula=1;
                grupa1.getChildren().remove(formula);
                grupa2.getChildren().remove(formula2);
                grupa3.getChildren().remove(formula3);
                postaviStazuIFromulu(tekucaStaza,formula);

            }


        });
        drugaTrka.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if(izabranaStaza==0){izabranaStaza=2;
                grupa1.getChildren().remove(staza);
                grupa2.getChildren().remove(staza2);
                grupa3.getChildren().remove(staza3);
                tekucaStaza=staza2;
                ucitajFormule();
            }
            else if(izabranaFromula==0){
                izabranaFromula=2;
                grupa1.getChildren().remove(formula);
                grupa2.getChildren().remove(formula2);
                grupa3.getChildren().remove(formula3);
                postaviStazuIFromulu(tekucaStaza,formula2);

            }

        });

        trecaTrka.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if(izabranaStaza==0){
                izabranaStaza=3;
                grupa1.getChildren().remove(staza);
                grupa2.getChildren().remove(staza2);
                grupa3.getChildren().remove(staza3);
                tekucaStaza=staza3;
                ucitajFormule();
            }
            else if(izabranaFromula==0){
                izabranaFromula=3;
                grupa1.getChildren().remove(formula);
                grupa2.getChildren().remove(formula2);
                grupa3.getChildren().remove(formula3);
                postaviStazuIFromulu(tekucaStaza,formula3);


            }

        });
        trecaTrka.getTransforms().addAll(new Translate(WIDTH/3*2,100));

        root.getChildren().addAll(prvaTrka,drugaTrka,trecaTrka);










         scene=new Scene(root,WIDTH,HEIGHT, true, SceneAntialiasing.BALANCED);

        scene.setOnKeyPressed(e->onKeyPressed(e));
        scene.setOnKeyReleased(e->onKeyReleased(e));



        return scene;
    }

    private void onKeyPressed(KeyEvent e)
    {

        switch( e.getCode() )
        {
            case UP:
                tekucaFormula.ubrzaj(1);
                break;

            case DOWN:
                tekucaFormula.ubrzaj(-1);
                break;

            case RIGHT:
                tekucaFormula.skretanje(-1.5);
                break;

            case LEFT:
                tekucaFormula.skretanje(1.5);
                break;
            case DIGIT2:

                persKamera.setFarClip(5000);
                    persKamera.t1.xProperty().setValue(
                            tekucaFormula.localToScene(tekucaFormula.getBoundsInLocal()).getCenterX()
                    );
                    persKamera.t1.yProperty().setValue(
                            tekucaFormula.localToScene(tekucaFormula.getBoundsInLocal()).getCenterY()
                    );
                stazaIformula.setCamera(persKamera);
                brKamera=2;
                break;
            case DIGIT1:
                ParallelCamera parallelCamera=new ParallelCamera();
                stazaIformula.setCamera(parallelCamera);
                brKamera=1;
                break;
            case DIGIT3:
                trecaKamera.setFarClip(5000);
                trecaKamera.rz.setPivotX(WIDTH/2);
                trecaKamera.rz.setPivotY(HEIGHT/2);
                trecaKamera.rz.setAngle(270);
                trecaKamera.t1.xProperty().setValue(
                        WIDTH/2
                );
                trecaKamera.t1.yProperty().setValue(
                       HEIGHT/2
                );
                trecaKamera.t1.zProperty().setValue(-1500);

                stazaIformula.setCamera(trecaKamera);
                brKamera=3;
                break;
            case DIGIT8:
                if(brKamera==2){
                    persKamera.t1.zProperty().setValue((persKamera.t1.zProperty().get())+5);
                }else if(brKamera==3){
                    trecaKamera.t1.zProperty().setValue(trecaKamera.t1.zProperty().get()+5);
                }
                break;
            case DIGIT9:
                if(brKamera==2){
                    persKamera.t1.zProperty().setValue((persKamera.t1.zProperty().get())-5);
                }else if(brKamera==3){
                    trecaKamera.t1.zProperty().setValue(trecaKamera.t1.zProperty().get()-5);
                }
                break;

        }
    }

    private void onKeyReleased(KeyEvent e)
    {
        switch( e.getCode() )
        {
            case UP:
                tekucaFormula.ubrzaj(0);
                break;


            case RIGHT:
            case LEFT:
                tekucaFormula.skretanje(0);
                break;
        }
    }


    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(this.pocetakIgre(WIDTH,HEIGHT));
        stage.setTitle("Igra-Trke");
        stage.show();
    }
    public static void main(String[]args){
        launch(args);
    }
}
