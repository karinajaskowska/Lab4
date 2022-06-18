package pong1;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.NamedArg;
import javafx.fxml.FXMLLoader;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.util.EventListener;

import javafx.application.Application;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.event.Event;
import java.util.Random;

public class Pong1 extends Application {

    private static final double WIDTH = 800;
    private static final double HEIGHT = 600;
    private static final double MARGIN = 50;
    private static final double ARENAWIDTH = WIDTH - 2 * MARGIN;
    private static final double ARENAHEIGHT = HEIGHT - 2 * MARGIN;
    private static final double ARENAX1 = MARGIN;
    private static final double ARENAY1 = MARGIN;
    private static final double ARENAX2 = ARENAX1 + ARENAWIDTH;
    private static final double ARENAY2 = ARENAY1 + ARENAHEIGHT;
    private double[] x = new double[LICZBAKULEK];
    private double[] y = new double[LICZBAKULEK];
    private double[] vx = new double[LICZBAKULEK];
    private double[] vy = new double[LICZBAKULEK];
    private static final double R = 10;
    private static final int LICZBAKULEK = 10;
    private Kulka[] kulki = new Kulka[LICZBAKULEK/2];
    private Rugby[] rugby = new Rugby[LICZBAKULEK/2];


    public void KeyFrame(@NamedArg("time") Duration time, @NamedArg("onFinished") EventHandler<ActionEvent> onFinished) {
    }

    @FunctionalInterface
    public interface EventHandler<T extends Event> extends EventListener {
        void handle(T event);
    }

    private void initKula() {
        Random lott = new Random();
        for (int i = 0; i < LICZBAKULEK; i++) {
            x[i] = lott.nextDouble() * ARENAWIDTH + ARENAX1;
            y[i] = lott.nextDouble() * ARENAHEIGHT + ARENAY1;
            vx[i] = 5 + lott.nextDouble() * 20;
            vy[i] = 5 + lott.nextDouble() * 20;
        }
    }

    /*private void run(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(ARENAX1, ARENAY1, ARENAWIDTH, ARENAHEIGHT);
        for (int i = 0; i < LICZBAKULEK; i++) {

            if ((x[i] <= ARENAX1) || ((x[i] >= ARENAX2 - 20))) vx[i] = -vx[i];
            if ((y[i] <= ARENAY1) || ((y[i] >= ARENAY2 - 20))) vy[i] = -vy[i];
        }

        for (int i = 0; i < LICZBAKULEK; i++) {
            x[i] += vx[i];
            y[i] += vy[i];
        }

        for (int i = 0; i < LICZBAKULEK; i++) {
            gc.setFill(Color.WHITESMOKE);
            gc.fillOval(x[i], y[i], 2 * R, 2 * R);
        }
    }*/

    @Override
    public void start(Stage stage) {
        initKula();
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Timeline t = new Timeline(new KeyFrame(Duration.millis(100), e -> run(gc)));
        t.setCycleCount(Timeline.INDEFINITE);

        stage.setTitle("Kulki!");
        stage.setScene(new Scene(new StackPane(canvas)));
        stage.show();

        gc.setFill(Color.BLACK);
        gc.fillRect(ARENAX1, ARENAY1, ARENAWIDTH, ARENAHEIGHT);

        gc.setFill(Color.WHITESMOKE);
        gc.fillOval(ARENAX1 + ARENAWIDTH / 2, ARENAY1 + ARENAHEIGHT / 2, 2 * R, 2 * R);
        gc.fillRect(ARENAX1, ARENAY1, ARENAWIDTH, ARENAHEIGHT);

        t.play();
    }


    public static void main(String[] args) {
        launch(args);

    }

    private void initKule() {
        Random lott = new Random();
        for (int i = 0; i < LICZBAKULEK/2; i++) {
            kulki[i] = new Kulka(
                    lott.nextDouble() * ARENAWIDTH + ARENAX1,
            lott.nextDouble() * ARENAHEIGHT + ARENAY1,
            5 + lott.nextDouble() * 20,
                    5 + lott.nextDouble() * 20,
                    10,
                    Color.WHITESMOKE);
        }
        for (int i = 0; i < LICZBAKULEK/2; i++){
            rugby[i] = new Rugby(
                    lott.nextDouble() * ARENAWIDTH + ARENAX1,
                    lott.nextDouble() * ARENAHEIGHT + ARENAY1,
                    5 + lott.nextDouble() * 20,
                    5 + lott.nextDouble() * 20,
                    10,
                    15,
                    2);
        }
    }

    private void run(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(ARENAX1, ARENAY1, ARENAWIDTH, ARENAHEIGHT);

        for (int i = 0; i < LICZBAKULEK/2; i++) {
            kulki[i].checkBoundaryCollision(ARENAX1, ARENAY1, ARENAX2, ARENAY2);
            rugby[i].checkBoundaryCollision(ARENAX1, ARENAY1, ARENAX2, ARENAY2);
            kulki[i].update();
            rugby[i].update();
            kulki[i].draw(gc);
            rugby[i].draw(gc);
        }
    }

}
