package com.joker.gw.juc;

import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class HomeWork04 {


    // 线程池提交callable任务阻塞get()
    public static void main1(String[] args) {

        long start = System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法

        Callable<Integer> resultCall = () -> sum();

        //这是得到的返回值
        try {
            int result = Executors.newCachedThreadPool().submit(resultCall).get();
            // 确保  拿到result 并输出
            System.out.println("异步计算结果为：" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");

        // 然后退出main线程
        return;
    }

    //CountDownLatch
    public static void main2(String[] args) {

        long start = System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        CountDownLatch countDownLatch = new CountDownLatch(1);
        final int[] result = {0};
        new Thread(() -> {
            result[0] = sum();
            countDownLatch.countDown();
        }).start();
        try {
            countDownLatch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为：" + result[0]);

        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
        // 然后退出main线程
        return;
    }

    //Semaphore 信号量
    public static void main3(String[] args) {

        long start = System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        Semaphore semaphore = new Semaphore(1);
        final int[] result = {0};

        new Thread(() -> {

            try {
                semaphore.acquire(1);
                result[0] = sum();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                semaphore.release(1);
            }
        }).start();

        try {
            //子线程创建需要时间
            TimeUnit.MILLISECONDS.sleep(100);
            semaphore.acquire(1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            semaphore.release(1);
        }

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为：" + result[0]);

        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");

        // 然后退出main线程
        return;
    }

    //读写共享变量
    public static void main4(String[] args) {

        long start = System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法

        final int[] result = {0};
        new Thread(() -> {
            try {
                result[0] = sum();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        while (result[0]== 0) {
            try {
                TimeUnit.MILLISECONDS.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 确保  拿到result 并输出
        System.out.println("异步计算结果为：" + result[0]);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
        // 然后退出main线程
        return;
    }

    //CyclicBarrier
    public static void main5(String[] args) {

        long start = System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        final int[] result = {0};
        Thread thread = new Thread(() -> {
            try {
                result[0] = sum();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        CyclicBarrier barrier = new CyclicBarrier(1, thread);
        try {
            while (barrier.await() == 1) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 确保  拿到result 并输出
        System.out.println("异步计算结果为：" + result[0]);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
        // 然后退出main线程
        return;
    }

    //子线程调用join
    public static void main6(String[] args) {

        long start = System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        final int[] result = {0};
        try {
            Thread childThread = new Thread(() -> {
                result[0] = sum();
            });
            childThread.start();
            childThread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为：" + result[0]);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
        // 然后退出main线程
        return;
    }

    //condition 条件等待
    public static void main7(String[] args) {

        long start = System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        ReentrantLock lock = new ReentrantLock(true);
        Condition condition = lock.newCondition();

        final int[] result = {0};
        try {
            new Thread(() -> {
                try {
                    while(lock.tryLock()){
                        result[0] = sum();
                        condition.signalAll();
                        break;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                }

            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        while(!lock.tryLock()){

        }
        if(result[0]==0){
            try{
                condition.await();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                lock.unlock();
            }

        }
        // 确保  拿到result 并输出
        System.out.println("异步计算结果为：" + result[0]);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
        // 然后退出main线程
        return;
    }

    //LockSupport
    public static void main8(String[] args) {

        long start = System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        Thread parent = Thread.currentThread();
        final int[] result = {0};

        new Thread(()->{
            result[0]=sum();
            LockSupport.unpark(parent);
        }).start();

        if(result[0]==0){
            LockSupport.park(parent);
        }

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为：" + result[0]);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
        // 然后退出main线程
        return;
    }

    //阻塞队列
    public static void main9(String[] args) throws InterruptedException {

        long start = System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        ArrayBlockingQueue<Integer> tasks = new ArrayBlockingQueue<>(1);
        new Thread(()->{
            try {
                tasks.put(sum());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为：" + tasks.take());
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
        // 然后退出main线程
        return;
    }

    //wait/notify
    public static void main10(String[] args) throws InterruptedException {

        long start = System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        final int[] result = {0};
        new Thread(()->{
            synchronized (result){
                result[0] = sum();
                result.notify();
            }
        }).start();
        synchronized (result){
            if(result[0]==0){
                result.wait();
            }
        }

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为：" + result[0]);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
        // 然后退出main线程
        return;
    }

    public static void main(String[] args) throws InterruptedException {

        long start = System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        final int[] result = {0};
        // 确保  拿到result 并输出
        System.out.println("异步计算结果为：" + result[0]);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
        // 然后退出main线程
        return;
    }


    private static int sum() {
        return fibo(36);
    }

    private static int fibo(int a) {
        if (a < 2)
            return 1;
        return fibo(a - 1) + fibo(a - 2);
    }

}
