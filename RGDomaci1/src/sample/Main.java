package sample;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;
import meta.Meta;
import meta.TextPoena;
import nisan.Nisan;
import javafx.scene.text.Text;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Main extends Application {

    private Nisan nisan=new Nisan();
    private Scene scene;
    private int nivo=1;
    private Group root;
    private Text nivoText;
    private Text poeniText;
    private Text municijaText;
    private double poeni=0.;
    private LinkedList <TextPoena> listaPoena=new LinkedList<>();
    private LinkedList <Meta> listaAktivnihMeta=new LinkedList<>();
    private int municija;
    private Rectangle pozadina;
    private HashMap<Integer,Integer> statistika=new HashMap<>();
    private SubScene menu;
    private Text statistikaText;
    private AnimationTimer timer;


    private Scene getGame(double width,double height){
        root=new Group();

        nivoText=new Text(String.format("Trenutni nivo %d",nivo));
        nivoText.setFont(Font.font(20));
        nivoText.getTransforms().addAll(new Translate(width/3,20));

        poeniText=new Text(String.format("Trenutni poeni %.2f",poeni));
        poeniText.setFont(Font.font(20));
        poeniText.getTransforms().addAll(new Translate(0,20));

        municijaText=new Text(String.format("Municija: %d",nivo*3));
        municijaText.setFont(Font.font(20));
        municijaText.getTransforms().addAll(new Translate(0,50));

        pozadina=new Rectangle(0,0,width,height);
        pozadina.setFill(new ImagePattern(new Image(String.format("slike/%d.jpg",0))));
        Group root1=new Group();
        Rectangle pozadineSub=new Rectangle(width/2,height/2);
        pozadineSub.setFill(Color.LIGHTGRAY);

        statistikaText=new Text();
        statistikaText.setFont(Font.font(15));
        statistikaText.getTransforms().addAll(new Translate(0,20));
        root1.getChildren().addAll(pozadineSub,statistikaText);

        menu=new SubScene(root1,width/2,height/2);
        menu.getTransforms().addAll(new Translate(width/4,height/4,-50));
        menu.setOnMouseClicked(e->ponovoPokreniIgru(e));

        root.getChildren().addAll(pozadina,nisan, nivoText,poeniText, municijaText);

        this.inicijalizuj(statistika);

         timer=new AnimationTimer() {
            @Override
            public void handle(long l) {
                if(listaAktivnihMeta.isEmpty() ){
                    if (nivo==6){
                        this.stop();
                        nivoText.setText("Zavrsena igra");
                        root.getChildren().addAll(menu);
                        postaviTextStatistike(statistikaText);
                        return;
                    }
                    municija=nivo*3;
                    municijaText.setText(String.format("Municija: %d",municija));
                    for( int i=0;i<nivo*2;i++){
                        int br=(int)(Math.random()*4) +3;
                        Meta meta=new Meta(br);

                        meta.setPosition(new Point2D(Math.random()*(width/2)+150,Math.random()*(height/2)+150));
                        root.getChildren().addAll(meta);
                        listaAktivnihMeta.add(meta);
                    }
                    nivoText.setText(String.format("Trenutni nivo %d",nivo));

                    pozadina.setFill(new ImagePattern(new Image(String.format("slike/%d.jpg",nivo%3))));
                    nivo++;

                }

                for (Meta meta:listaAktivnihMeta) {
                    meta.update(l,width,height, nivo);
                }
                LinkedList<TextPoena> lista=new LinkedList<>();
                for (TextPoena text: listaPoena) {
                    if(text.tranzicija.getCurrentRate()==0){
                        lista.add(text);
                    }
                }
                listaPoena.removeAll(lista);

                for (Meta meta:listaAktivnihMeta) {
                    if(meta.prozirnost.getCurrentRate()==0){
                        nivoText.setText(String.format("Zavrsena igra na nivou %d",nivo-1));

                        root.getChildren().addAll(menu);
                        postaviTextStatistike(statistikaText);
                        this.stop();
                        return;
                    }
                }
            }
        };
        timer.start();

        scene= new Scene(root,width,height, true, SceneAntialiasing.BALANCED);
        scene.setOnMouseMoved(e->PomeranjeMisa(e));
        scene.setOnMouseClicked(e->PucanjeMis(e));
        scene.setOnKeyPressed(e->PomeranjeTastatura(e));



        return scene;

    }

    private void PomeranjeMisa(MouseEvent e){
        nisan.pomeranje.xProperty().setValue(e.getSceneX());
        nisan.pomeranje.yProperty().setValue(e.getSceneY());
    }

    private void PucanjeMis(MouseEvent e){
//        System.out.println("usao u pogresan");
        if(listaAktivnihMeta.isEmpty()) return;
        if(municija>0){
            municija--;
            municijaText.setText(String.format("Municija: %d",municija));
        }else return;
        ArrayList <Meta> lista=new ArrayList<>();
        for (Meta meta:listaAktivnihMeta) {
            Bounds metaKord=meta.localToScene(meta.getBoundsInLocal());
            if(metaKord.contains(e.getSceneX(),e.getSceneY()) &&
                    meta.prozirnost.getCurrentRate()>0){
                updateScene(meta, lista);
                updatePoeni(meta, nisan);
            }
        }
        listaAktivnihMeta.removeAll(lista);

    }

    private void PomeranjeTastatura(KeyEvent e){
        switch (e.getCode()){
            case UP:nisan.pomeranje.yProperty().setValue(
                    nisan.pomeranje.yProperty().get()-2
            );
            break;
            case DOWN:
                nisan.pomeranje.yProperty().setValue(
                        nisan.pomeranje.yProperty().get()+2
                );
            break;
            case LEFT:
                nisan.pomeranje.xProperty().setValue(
                        nisan.pomeranje.xProperty().get()-2
                );
            break;
            case RIGHT:
                nisan.pomeranje.xProperty().setValue(
                        nisan.pomeranje.xProperty().get()+2
                );
            break;
            case SPACE:
                if(listaAktivnihMeta.isEmpty())return;
                if(municija>0){
                    municija--;
                    municijaText.setText(String.format("Municija: %d",municija));
                }else return;
                Bounds nisanKord=nisan.localToScene(nisan.getBoundsInLocal());
                ArrayList <Meta> lista=new ArrayList<>();
                for (Meta meta:listaAktivnihMeta) {
                    Bounds metaKord=meta.localToScene(meta.getBoundsInLocal());

                    if(metaKord.contains(nisanKord.getCenterX(),nisanKord.getCenterY()) &&
                            meta.prozirnost.getCurrentRate()>0) {
                        updateScene(meta, lista);
                        updatePoeni(meta, nisan);
                    }
                }
                listaAktivnihMeta.removeAll(lista);


        }
    }

    private void updateScene(Meta meta, ArrayList<Meta> lista){
        lista.add(meta);
        root.getChildren().removeAll(meta);

    }

    private void updatePoeni(Meta meta ,Nisan nisan){
        Bounds pozicijaPogotka=nisan.localToScene(nisan.getBoundsInLocal());
        Bounds pozicijaMeta=meta.localToScene(meta.getBoundsInLocal());
        double xStranica=pozicijaPogotka.getCenterX()-pozicijaMeta.getCenterX();
        double yStranica=pozicijaPogotka.getCenterY()-pozicijaMeta.getCenterY();

        double hipotenuza=Math.sqrt(xStranica*xStranica+yStranica*yStranica);

        double podeoci=(pozicijaMeta.getMaxX()-pozicijaMeta.getCenterX())/meta.br;
        int umnozak=(int)(hipotenuza/podeoci);
        if(umnozak==0){
            TextPoena pogodjeniPoeni=new TextPoena(150);
            pogodjeniPoeni.getTransforms().
                    addAll(new Translate(pozicijaPogotka.getCenterX(),pozicijaPogotka.getCenterY()));
            root.getChildren().addAll(pogodjeniPoeni);
            listaPoena.add(pogodjeniPoeni);
            poeni+=150;
            int vrednost=statistika.remove(150);
            statistika.put(150,vrednost+1);
        }else{
            umnozak=meta.br-umnozak;
            if(umnozak==0)umnozak=1;
            umnozak=Math.abs(umnozak);
            poeni+=umnozak*20;
            TextPoena pogodjeniPoeni=new TextPoena(umnozak*20);
            pogodjeniPoeni.getTransforms().
                    addAll(new Translate(pozicijaPogotka.getCenterX(),pozicijaPogotka.getCenterY()));
            root.getChildren().addAll(pogodjeniPoeni);
            listaPoena.add(pogodjeniPoeni);
            //System.out.println(umnozak*20);
            int vrednost=statistika.remove((Integer) (umnozak*20));

            statistika.put((Integer) (umnozak*20),vrednost+1);
        }
        poeniText.setText(String.format("Trenutni poeni %.2f",poeni));


    }

    private void ponovoPokreniIgru(MouseEvent e){
        System.out.println("ulazi");
        nivo=1;
        inicijalizuj(statistika);
        root.getChildren().removeAll(menu);
        listaAktivnihMeta.removeAll(listaAktivnihMeta);

        poeni=0;
        timer.start();


    }

    private  void inicijalizuj(HashMap statistika){
        statistika.put(20,0);
        statistika.put(40,0);
        statistika.put(60,0);
        statistika.put(80,0);
        statistika.put(100,0);
        statistika.put(150,0);
    }

    private void postaviTextStatistike(Text statistikaText){

        statistikaText.setText(String.format(
                "20: %d\n" +
                        "40: %d\n" +
                        "60: %d\n" +
                        "80: %d\n" +
                        "100: %d\n" +
                        "150: %d\n",statistika.remove(20),statistika.remove(40),
                statistika.remove(60),statistika.remove(80),statistika.remove(100),
                statistika.remove(150)));
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        primaryStage.setScene(this.getGame(600,600));
        primaryStage.setTitle("Domaci");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
