package com.rjp.leetcode;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * author: jinpeng.ren create at 2019/11/19 11:40
 * email: jinpeng.ren@11bee.com
 */
public class _752 {

    public int openLock(String[] deadends, String target) {
        Queue<String> queue = new LinkedList<>();
        queue.add("0000");
        queue.add("split");

        Set<String> seen = new HashSet<>();
        seen.add("0000");

        List<String> deads = Arrays.asList(deadends);

        int depth = 0;
        while (!queue.isEmpty()) {
            String poll = queue.poll();
            if ("split".equals(poll)) {
                depth++;
                if (queue.peek() != null) {
                    queue.offer("split");
                }
            } else if (target.equals(poll)) {
                return depth;
            } else if (!deads.contains(poll)) {
                for (int i = 0; i < 4; i++) {
                    for (int j = -1; j <= 1; j += 2) {
                        StringBuilder curBuilder = new StringBuilder(poll);
                        char modChar = poll.charAt(i);

                        curBuilder.setCharAt(i, modChar == '0' ? '9' : (char) (modChar - 1));
                        String temp1 = curBuilder.toString();
                        if (!seen.contains(temp1)) {
                            seen.add(temp1);
                            queue.add(temp1);
                        }

                        curBuilder.setCharAt(i, modChar == '9' ? '0' : (char) (modChar + 1));
                        String temp2 = curBuilder.toString();
                        if (!seen.contains(temp2)) {
                            seen.add(temp2);
                            queue.add(temp2);
                        }
                    }
                }
            }
        }

        return -1;
    }
}
