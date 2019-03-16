package bitonic.sorters;

import bitonic.ICallBack;

public class BitonicSorterForArbitraryN implements Runnable {
    private int[] a;
    private final static boolean ASCENDING=true;    // sorting direction
    private ICallBack callBack;

    public BitonicSorterForArbitraryN(ICallBack callback, int[] a, int lo, int n, boolean dir) {
        this.callBack = callback;
        this.a = a;
    }

    @Override
    public void run() {
        //double start = System.nanoTime();
        bitonicSort(0, a.length, ASCENDING);
        /*double end = System.nanoTime();

        double total = (end - start)/1000000000;
        System.out.println("total time: " + total + " sec");

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Tijd meting");
            alert.setHeaderText("De opgemeten tijd");
            alert.setContentText("totale tijd: " + total + " sec");
            alert.show();
        });*/
    }

    private void bitonicSort(int lo, int n, boolean dir)
    {
        if (n>1)
        {
            int m=n/2;

            Thread t1 = new Thread( new Thread(() -> bitonicSort(lo, m, !dir)));
            Thread t2 = new Thread( new Thread(() -> bitonicSort(lo+m, n-m, dir)));

            t1.start();
            t2.start();

            try {
                t1.join();
                t2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            bitonicMerge(lo, n, dir);
        }
    }

    private void bitonicMerge(int lo, int n, boolean dir)
    {
        if (n>1)
        {
            int m=greatestPowerOfTwoLessThan(n);
            for (int i=lo; i<lo+n-m; i++)
                compare(i, i+m, dir);

            Thread t1 = new Thread( new Thread(() -> bitonicMerge(lo, m, dir)));
            Thread t2 = new Thread( new Thread(() -> bitonicMerge(lo+m, n-m, dir)));

            t1.start();
            t2.start();

            try {
                t1.join();
                t2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            /*bitonicMerge(lo, m, dir);
            bitonicMerge(lo+m, n-m, dir);*/
        }
    }

    private void compare(int i, int j, boolean dir)
    {
        if (dir==(a[i]>a[j]))
            exchange(i, j);
    }

    private void exchange(int i, int j)
    {
        int t=a[i];
        a[i]=a[j];
        a[j]=t;
        if(callBack != null) {
            callBack.callBack(i, j);
        }
    }

    // n>=2  and  n<=Integer.MAX_VALUE
    private int greatestPowerOfTwoLessThan(int n)
    {
        int k=1;
        while (k>0 && k<n)
            k=k<<1;
        return k>>>1;
    }
}