package com.emreditor;

public class Test {
    private static User u = new User();

    public static void main(String[] args) {
        Thread t1=new Thread(() -> {
            while (true){
                if (Thread.currentThread().isInterrupted()){
                    System.out.println("终断线程他");
                    break;
                }
                synchronized (u) {
                    u.setId("id: 1");
                    u.setName("name: 1");
                    System.out.println("1: "+u.getId()+" ; "+u.getName());
                    try {
                        u.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t1.start();
        new Thread(() -> {
            int i = 0;
            while (true)
                synchronized (u) {
                    u.setId("id: 2");
                    u.setName("name: 2");
                    System.out.println("2: "+u.getId()+" ; "+u.getName());
                    try {
                        u.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    t1.interrupt();
                }
        }).start();
        new Thread(() -> {
            while (true)
                synchronized (u) {
                    try {
                        Thread.sleep(130);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("3: "+u.getId()+" ; "+u.getName());
                    u.notifyAll();
                    Thread.yield();
                }
        }).start();
    }
}

class User {

    private String name;
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}