package grafika.objekti;

import geometrija.Vektor;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public abstract class FormulaNad extends Group implements Orijentisan {
    protected final Translate trans = new Translate();
    protected final Rotate rot = new Rotate();
    private final Vektor pozicija = new Vektor();
    private final Vektor brzina = new Vektor();
    private final Vektor orijentacija = new Vektor(0, 1, 0);
    final protected Paint bojaTockova = Color.BLACK;
    final protected Paint bojaSasije = Color.BLUE;
    final public double maxBrzina = 15;
    private final double ubrzanje = 2;
    private final double brzinaSkretanja = 0.1;
    private double smerSkretanja = 0;
    private double smerUbrzanja = 0;

    @Override
    public Vektor pozicija() {
        return pozicija;
    }

    public Vektor brzina() {
        return brzina;
    }

    @Override
    public Vektor orijentacija() {
        return orijentacija;
    }

    public void pomeri(double t) {
        double koeficijentUbrzanja = 1 - brzina.intenzitet() / maxBrzina;
        brzina.dodaj(orijentacija.proizvod(t * smerUbrzanja * ubrzanje * koeficijentUbrzanja));
        pozicija.dodaj(brzina.proizvod(t));

        orijentacija.rotirajOkoZ(t * brzinaSkretanja * smerSkretanja);
        brzina.postavi(orijentacija.proizvod(brzina.intenzitet()));

        trans.setX(pozicija.x());
        trans.setY(pozicija.y());
        rot.setAngle(-orijentacija.azimutDeg());

    }

    public void ubrzaj(double i) {
        smerUbrzanja = i;
    }

    public void skretanje(double i) {
        smerSkretanja = i;
    }
}
