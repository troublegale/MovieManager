package letsgo.lab6;

import java.util.ArrayDeque;
import java.util.Queue;

public class Main {
    public static void main(String[] args) {
        Queue<String> queue = new ArrayDeque<>();
        queue.add("hey");
        queue.add("helo");
        queue.add("what");
        while(!queue.isEmpty()) {
            System.out.println(queue.poll());
        }

    }
}