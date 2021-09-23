package geometrija;



public class Vektor
{
    private double	x, y, z;

    public Vektor()
    {
        this(0, 0, 0);
    }

    public Vektor(double x, double y, double z)
    {
        this.x = x; this.y = y; this.z = z;
    }

    public Vektor zbir(Vektor v)
    {
        return new Vektor(x + v.x, y + v.y, z + v.z);
    }

    public Vektor dodaj(Vektor v)
    {
        x += v.x; y += v.y; z += v.z;
        return this;
    }

    public Vektor proizvod(double f)
    {
        return new Vektor(x * f, y * f, z * f);
    }

    public Vektor mnozi(double f)
    {
        x *= f; y *= f; z *= f;
        return this;
    }

    public double x() { return x; }
    public double y() { return y; }
    public double z() { return z; }

    public void postaviX(double _x) { x = _x; }
    public void postaviY(double _y) { y = _y; }
    public void postaviZ(double _z) { z = _z; }

    public void postavi(Vektor v)
    {
        x = v.x;
        y = v.y;
        z = v.z;
    }

    public double razdaljina(Vektor v)
    {
        return Math.sqrt( razdaljina2(v) );
    }

    public double razdaljina2(Vektor v)
    {
        return Math.pow(x-v.x, 2) + Math.pow(y-v.y, 2) + Math.pow(z-v.z, 2);
    }

    public double intenzitet()
    {
        return Math.sqrt(intenzitet2());
    }

    public double intenzitet2()
    {
        return Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2);
    }

    public Vektor rotirajOkoZ(double ugao)
    {
        double xprim = x*Math.cos(ugao)+y*Math.sin(ugao);
        double yprim = -x*Math.sin(ugao)+y*Math.cos(ugao);
        x = xprim;
        y = yprim;
        return this;
    }

    public double azimut()
    {
        return Math.atan2(x, y);
    }

    public double azimutDeg()
    {
        return azimut()*180.0/Math.PI;
    }
}