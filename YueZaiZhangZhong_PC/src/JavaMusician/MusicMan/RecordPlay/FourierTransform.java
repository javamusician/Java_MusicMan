package JavaMusician.MusicMan.RecordPlay;
public class FourierTransform {
    static private int n, nu;

    static private int BitReverse(int j)
    {
        int j2;
        int j1 = j;
        int k = 0;
        for (int i = 1; i <= nu; i++)
        {
            j2 = j1 / 2;
            k = 2 * k + j1 - 2 * j2;
            j1 = j2;
        }
        return k;
    }

    static public double[] FFTDb(double[] x)
    {
        // Assume n is a power of 2
        n = x.length;
        nu = (int)(Math.log(n) / Math.log(2));
        int n2 = n / 2;
        int nu1 = nu - 1;
        double[] xre = new double[n];
        double[] xim = new double[n];
        double[] decibel = new double[n2];
        double tr, ti, p, arg, c, s;
        for (int i = 0; i < n; i++)
        {
            xre[i] = x[i];
            xim[i] = 0.0f;
        }
        int k = 0;
        for (int l = 1; l <= nu; l++)
        {
            while (k < n)
            {
                for (int i = 1; i <= n2; i++)
                {
                    p = BitReverse(k >> nu1);
                    arg = 2 * (double)Math.PI * p / n;
                    c = (double)Math.cos(arg);
                    s = (double)Math.sin(arg);
                    tr = xre[k + n2] * c + xim[k + n2] * s;
                    ti = xim[k + n2] * c - xre[k + n2] * s;
                    xre[k + n2] = xre[k] - tr;
                    xim[k + n2] = xim[k] - ti;
                    xre[k] += tr;
                    xim[k] += ti;
                    k++;
                }
                k += n2;
            }
            k = 0;
            nu1--;
            n2 = n2 / 2;
        }
        k = 0;
        int r;
        while (k < n)
        {
            r = BitReverse(k);
            if (r > k)
            {
                tr = xre[k];
                ti = xim[k];
                xre[k] = xre[r];
                xim[k] = xim[r];
                xre[r] = tr;
                xim[r] = ti;
            }
            k++;
        }
        for (int i = 0; i < n / 2; i++)
            decibel[i] = 10.0 * Math.log10((float)(Math.sqrt((xre[i] * xre[i]) + (xim[i] * xim[i]))));
        return decibel;
    }
}
