package by.it.soldatenko.jd02_03;

import java.util.ArrayDeque;
import java.util.Deque;

public class QueueBuyers {

//    private static final Deque<Buyer> deque= new LinkedList<>(); //для пенсионеров
private static final Deque<Buyer> deque = new ArrayDeque<>();

    static synchronized void add(Buyer buyer){
        deque.addLast(buyer);

    }
    static synchronized Buyer extract(){
        return deque.pollFirst();

    }
}
