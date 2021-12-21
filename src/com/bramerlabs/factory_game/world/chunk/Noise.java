package com.bramerlabs.factory_game.world.chunk;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Noise {

    private final short[] permMod12;
    private final short[] perm;

    public Noise(int seed) {
        ArrayList<Short>temp = new ArrayList<>();
        for (short i = 0; i < 512; i++) {
            temp.add((short) (i % 256));
        }
        Random random = new Random(seed);
        Collections.shuffle(temp, random);
        short[] p = new short[512];
        perm = new short[512];
        permMod12 = new short[512];
        for (int i = 0; i < 512; i++) {
            p[i] = temp.get(i);
            perm[i] = p[i & 255];
            permMod12[i] = (short) (perm[i] % 12);
        }
    }

    public float noise(float xin, float yin) {
        float n0, n1, n2; // noise contributions from the three corners
        // skew inputs to determine which simplex cell we're in
        float s = (xin + yin) * F2; // Hairy factor for 2D
        int i = fastfloor(xin + s);
        int j = fastfloor(yin + s);
        float t = (i + j) * G2;
        float X0 = i - t; // unskew cell back to normalized (x, y) space
        float Y0 = j - t;
        float x0 = xin - X0; // the x, y distances from the cell origin
        float y0 = yin - Y0;
        // determine which simplex we're in
        int i1, j1; // offsets for second corner of simplex in (i, j) coordinates
        if (x0 > y0) {
            i1 = 1; j1 = 0; // lower triangle: (0, 0) -> (1, 0) -> (1, 1)
        } else {
            i1 = 0; j1 = 1; // upper triangle: (0, 0) -> (0, 1) -> (1, 1)
        }
        float x1 = x0 - i1 + G2; // offset for second corner in (x, y) coordinates
        float y1 = y0 - j1 + G2;
        float x2 = x0 - 1.0f + 2.0f * G2; // offsets for last corner in (x, y) coordinates
        float y2 = y0 - 1.0f + 2.0f * G2;
        // work out hashed gradient indices of simplex corners
        int ii = i & 255;
        int jj = j & 255;
        int gi0 = permMod12[ii + perm[jj]];
        int gi1 = permMod12[ii + i1 + perm[jj + j1]];
        int gi2 = permMod12[ii + 1  + perm[jj + 1]];
        // calculate contribution from the three corners
        float t0 = 0.5f - x0 * x0 - y0 * y0;
        if (t0 < 0) {
            n0 = 0.0f;
        } else {
            t0 *= t0;
            n0 = t0 * t0 * dot(grad3[gi0], x0, y0); // (x, y) of grad3 used for 2D gradient
        }
        float t1 = 0.5f - x1 * x1 - y1 * y1;
        if (t1 < 0) {
            n1 = 0.0f;
        } else {
            t1 *= t1;
            n1 = t1 * t1 * dot(grad3[gi1], x1, y1);
        }
        float t2 = 0.5f - x2 * x2 - y2 * y2;
        if (t2 < 0) {
            n2 = 0.0f;
        } else {
            t2 *= t2;
            n2 = t2 * t2 * dot(grad3[gi2], x2, y2);
        }
        // add contributions to get final noise value
        // result is scaled to return values in the interval [-1, 1]
        return 70.0f * (n0 + n1 + n2);
    }

    public static float fade(float t) {
        return t * t * t * (t * (t * 6 - 15) + 10);
    }

    public static float lerp(float t, float a, float b) {
        return a + t * (b - a);
    }

    public static float grad(int hash, float x, float y, float z) {
        int h = hash & 15;
        float u = h < 8 ? x : y;
        float v = h < 4 ? y : h == 12 || h == 14 ? x : z;
        return ((h & 1) == 0 ? u : -u) + ((h & 2) == 0 ? v : -v);
    }

    // Skewing and unskewing factors for 2, 3, and 4 dimensions
    private static final float F2 = (float) (0.5*(Math.sqrt(3.0)-1.0));
    private static final float G2 = (float) ((3.0-Math.sqrt(3.0))/6.0);
    private static final float F3 = (float) (1.0/3.0);
    private static final float G3 = (float) (1.0/6.0);
    private static final float F4 = (float) ((Math.sqrt(5.0)-1.0)/4.0);
    private static final float G4 = (float) ((5.0-Math.sqrt(5.0))/20.0);

    // This method is a *lot* faster than using (int)Math.floor(x)
    private static int fastfloor(float x) {
        int xi = (int)x;
        return x<xi ? xi-1 : xi;
    }

    private static float dot(Grad g, float x, float y) {
        return g.x*x + g.y*y;
    }

    private static Grad[] grad3 = {
            new Grad(1,1,0),new Grad(-1,1,0),new Grad(1,-1,0),new Grad(-1,-1,0),
            new Grad(1,0,1),new Grad(-1,0,1),new Grad(1,0,-1),new Grad(-1,0,-1),
            new Grad(0,1,1),new Grad(0,-1,1),new Grad(0,1,-1),new Grad(0,-1,-1)
    };

    private static class Grad
    {
        float x, y, z, w;

        Grad(float x, float y, float z)
        {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        Grad(float x, float y, float z, float w)
        {
            this.x = x;
            this.y = y;
            this.z = z;
            this.w = w;
        }
    }

}