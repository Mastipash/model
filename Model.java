
import java.io.*;

class Model {

    private final static double G = 9.8;
    private final static int n = 1000;
    static double t, rad, elast;
    private Body body;
    static int i;

    private void calculate() throws IOException {
        double x, y, dx, dy, Vx, Vy, dVx, dVy, dt;

        x = body.x;
        y = body.y;
        Vx = body.Vx;
        Vy = body.Vy;
        dt = t / n;

        for (i = 1; i <= n; i++) {
            dx = Vx * dt;
            dy = Vy * dt;
            //dVx = Vx * dt;
            dVy = (Vy - G) * dt;
            x += dx;
            //Vx += dVx;
            if (y + dy > rad) {
                y += dy;
                Vy += dVy;
            } else {
                y = Math.abs(rad - (y - rad));
                Vy = Math.abs(Vy + dVy) * elast;
            }
            body.set(x, y, Vx, Vy);
            body.get();
        }
        body.fw.close();
    }

    Model(double[] params) throws IOException {
        double x = params[0];
        double y = params[1];
        double Vx = params[2];
        double Vy = params[3];
        body = new Body(x, y, Vx, Vy);
        this.t = params[4];
        this.rad = params[5];
        this.elast = params[6];
        calculate();
    }

}

class Body {

    FileWriter fw;

    double x, y, Vx, Vy;

    double V() {
        return Math.sqrt(Math.pow(Vx, 2) + Math.pow(Vy, 2));
    }

    Body(double x, double y, double Vx, double Vy) throws IOException {
        set(x, y, Vx, Vy);
        fw = new FileWriter("output.txt");
    }

    void set(double x, double y, double Vx, double Vy) {
        this.x = x;
        this.y = y;
        this.Vx = Vx;
        this.Vy = Vy;
    }

    void get() throws IOException {
        fw.write(String.format("Время: %-4d      Высота: %5.2f      Скорость: %5.2f\n", Model.i, y, V()));
    }
}

class main {

    public static void main(String[] args) throws IOException {
        double X = 0, //Начальная координата X
                Y = 5, //Начальная координата Y
                dX = 0, //Начальная скорость по X
                dY = 0, //Начальная скорость по Y
                T = 2, //Время
                R = 1, //Радиус
                Elast = 0.5; //Эластичность мяча
        new Model(new double[]{X, Y, dX, dY, T, R, Elast});

    }
}
