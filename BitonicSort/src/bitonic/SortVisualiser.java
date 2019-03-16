package bitonic;

import bitonic.helpers.HelperFunctions;
import bitonic.helpers.NormalizationFuncs;
import bitonic.sorters.BitonicSorterForArbitraryN;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class SortVisualiser implements ICallBack {

    private double canvasHeight, canvasWidth;
    private double rectWidth;
    private int[] array;
    private GraphicsContext g;
    private Runnable sorter;

    public SortVisualiser(Canvas canvas) {
        this.g = canvas.getGraphicsContext2D();
        this.canvasHeight = canvas.getHeight();
        this.canvasWidth = canvas.getWidth();
        this.g.setFill(Color.GREEN);
        this.sort();
    }

    private void sort() {
        //Create array
        //int logn = 12, n = 1 << logn;
        int n = (int)this.canvasWidth;
        System.out.println("Array size: " + n);

        this.rectWidth = (this.canvasWidth / n);
        g.setLineWidth(this.rectWidth);

        this.array = new int[n];
        for(int i = 0; i < n; i++) {
            this.array[i] = i;
        }
        HelperFunctions.shuffleArray(this.array);

        System.out.println("Rect width: " + this.rectWidth);

        this.clear();

        sorter = new BitonicSorterForArbitraryN(this, this.array, 0, 0, true);
        Thread t2 = new Thread(sorter);
        t2.start();
    }

    private void clear() {
        this.g.clearRect(0, 0, this.canvasWidth, this.canvasHeight);

        for (int i = 0; i < this.array.length; i++) {
            this.drawArrayIndex(i);
        }
    }

    public synchronized void callBack(int a, int b) {
        Platform.runLater(() -> {
            this.clearArrayIndex(a);
            this.drawArrayIndex(a);
            this.clearArrayIndex(b);
            this.drawArrayIndex(b);
        });
    }

    private void clearArrayIndex(int i) {
        //this.g.clearRect(i*this.rectWidth, 0, this.rectWidth, this.canvasHeight);

        //g.setFill(Color.WHITE);
        //this.g.fillRect(i*this.rectWidth, 0, this.rectWidth, this.canvasHeight);
        g.setStroke(Color.WHITE);
        g.strokeLine(i*this.rectWidth, 0, i*this.rectWidth, this.canvasHeight);
    }

    private void drawArrayIndex(int i) {
        //g.setFill(Color.RED);
        g.setStroke(Color.GREEN);
        double rectHeight = NormalizationFuncs.remapToRange(array[i], 0, array.length, 0, this.canvasHeight);
        //this.g.fillRect(i*this.rectWidth, this.canvasHeight-rectHeight, this.rectWidth, rectHeight);
        /*this.g.moveTo(i*this.rectWidth, this.canvasHeight-rectHeight);
        this.g.lineTo(i*this.rectWidth, this.canvasHeight);
        this.g.stroke();*/

        g.strokeLine(i*this.rectWidth, this.canvasHeight-rectHeight, i*this.rectWidth, this.canvasHeight);

    }

    public void stop() {
        ((Thread)sorter).interrupt();
    }
}
