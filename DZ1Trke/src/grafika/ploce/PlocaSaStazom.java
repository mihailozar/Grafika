package grafika.ploce;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class PlocaSaStazom extends Ploca{
    public static final Paint bojaStaze = Color.GRAY;
    public static final Paint bojaOznake = Color.GRAY.brighter();
    public static final Paint bojaIvice_bela = Color.WHITE;
    public static final Paint bojaIvice_crvena = Color.RED;


    public double sirinaTrake()
    {
        return 6*VELICINA/8;
    }

    public double pomerajTrake()
    {
        return VELICINA/8;
    }
}
