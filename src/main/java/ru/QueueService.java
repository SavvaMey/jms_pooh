package ru;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {
    ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> queue = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        if (req.method().equals("GET")) {
            var ans = queue.get(req.theme());
            if (ans != null) {
                return !ans.isEmpty() ? new Resp(ans.poll(), 200) : new Resp("theme: " + req.theme() + " is empty", 200);
            } else {
                return  new Resp("theme" + req.theme() + "not found", 404);
            }
        } else {
            queue.putIfAbsent(req.theme(), new ConcurrentLinkedQueue<>());
            queue.get(req.theme()).offer(req.message());
            return new Resp("", 200);
        }
    }
}
