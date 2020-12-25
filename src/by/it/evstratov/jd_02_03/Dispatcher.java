package by.it.evstratov.jd_02_03;

import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Dispatcher {

    static Semaphore buyersChooseGoods = new Semaphore(20);
    static private final BlockingDeque<Basket> dequeBasket = new LinkedBlockingDeque<>(10);
    static final int K_SPEED = 100;
    static final int PLAN = 100;
    static final AtomicInteger buyersInMarket = new AtomicInteger(0);
    static final AtomicInteger buyersCompleted = new AtomicInteger(0);
    static final AtomicInteger total = new AtomicInteger(0);

    private static final Map<Integer, Boolean> numbers = new ConcurrentHashMap<>();

    static {
        numbers.put(1,true);
        numbers.put(2,true);
        numbers.put(3,true);
        numbers.put(4,true);
        numbers.put(5,true);

        for (int i = 0; i < 10; i++) {
            try {
                dequeBasket.putLast(new Basket());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static void addBuyer(){
        buyersInMarket.getAndIncrement();
    }

    static void addTotal(int sum){
        total.addAndGet(sum);
    }

    static void completeBuyer(){
        buyersInMarket.getAndDecrement();
        buyersCompleted.getAndIncrement();
    }

    static boolean marketIsOpened(){
        return buyersInMarket.get() + buyersCompleted.get() < PLAN;
    }

    static boolean marketIsClosed(){
        return buyersCompleted.get() == PLAN;
    }

    public static int getTotal() {
        return total.get();
    }

    public static BlockingDeque<Basket> getBasket() {
        return dequeBasket;
    }

    public static synchronized void needToOpenNewCashiers(int sizeDeque){
        int openCashiers = Cashier.getOpenCashiers();
        int needToOpenCashiers =(int) Math.ceil((double) sizeDeque / 5.0);
        int needToCloseCashiers = openCashiers - needToOpenCashiers;
        if(openCashiers < needToOpenCashiers && !QueueCashiers.getWaitCashiers().isEmpty()){
            for (int i = 0; i < needToOpenCashiers - openCashiers; i++) {
                Cashier cashier = QueueCashiers.getWaitCashiers().poll();
                System.out.println(cashier + "начала работать");
                QueueCashiers.getOpenCashiers().remove(cashier);
                if (cashier != null) {
                    synchronized (cashier){
                        cashier.notify();
                    }
                }
            }
        }
        if(needToCloseCashiers > 0){
            for (int i = 0; i < needToCloseCashiers; i++) {
                Cashier cashier = QueueCashiers.getOpenCashiers().poll();
                System.out.println(cashier + " завершает работать - нет очереди");
                QueueCashiers.getWaitCashiers().remove(cashier);
                if (cashier != null) {
                    synchronized (cashier){
                        try {
                            cashier.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}