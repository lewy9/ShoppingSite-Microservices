package com.example.demodeal.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.*;

@Component
public class ThreadPoolManager implements BeanFactoryAware {

    private static Logger log = LoggerFactory.getLogger(ThreadPoolManager.class);
    private BeanFactory factory;
    // Minimum # of threads in pool
    private final static int CORE_POOL_SIZE = 2;
    // Maximum # of threads in pool
    private final static int MAX_POOL_SIZE = 10;
    // Maximum # of time 
    private final static int KEEP_ALIVE_TIME = 0;
    // Maximum # of work queue size
    private final static int WORK_QUEUE_SIZE = 50;
    // Message cache queue
    Queue<Object> msgQueue = new LinkedList<Object>();

    // Store all the orders in a cache map
    Map<String, Object> cacheMap = new ConcurrentHashMap<>();

    // Handle when size or time or number exceeds limit
    final RejectedExecutionHandler handler = new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            msgQueue.offer(((DBThread) r).getMsg());
        }
    };

    // Order pool
    final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
            CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME,
            TimeUnit.SECONDS, new ArrayBlockingQueue(WORK_QUEUE_SIZE), this.handler);

    // Scheduler pool
    final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);

    final ScheduledFuture taskHandler = scheduler.scheduleAtFixedRate(new Runnable() {
        @Override
        public void run() {
            if (!msgQueue.isEmpty()) {
                if (threadPool.getQueue().size() < WORK_QUEUE_SIZE) {
                    System.out.print("Schedule：");
                    String orderId = (String) msgQueue.poll();
                    DBThread accessDBThread = (DBThread) factory.getBean("dBThread");
                    accessDBThread.setMsg(orderId);
                    threadPool.execute(accessDBThread);
                }
                // while (msgQueue.peek() != null) {
                // }
            }
        }
    }, 0, 1, TimeUnit.SECONDS);

    public void shutdown() {
        System.out.println(taskHandler.cancel(false));
        scheduler.shutdown();
        threadPool.shutdown();
    }

    public Queue<Object> getMsgQueue() {
        return msgQueue;
    }


    public void processOrders(String objString) {
        if (cacheMap.get(objString) == null) {
            cacheMap.put(objString,new Object());
            DBThread accessDBThread = (DBThread) factory.getBean("DBThread");
            accessDBThread.setMsg(objString);
            threadPool.execute(accessDBThread);
        }
    }


    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        factory = beanFactory;
    }
}