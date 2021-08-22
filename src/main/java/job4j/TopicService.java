package job4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {
    private final ConcurrentHashMap<String, ConcurrentHashMap<Integer,
            ConcurrentLinkedQueue<String>>> queue = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        if (req.method().equals("POST")) {
            if (queue.putIfAbsent(req.theme(), new ConcurrentHashMap<>()) != null) {
                queue.get(req.theme()).forEach((k, v) -> v.offer(req.message()));
            } else {
                queue.remove(req.theme());
            }
            return new Resp("", 200);
        } else {
            var q = queue.putIfAbsent(req.theme(), new ConcurrentHashMap<>());
            if (q != null) {
                var s = q.putIfAbsent(req.id(), new ConcurrentLinkedQueue<>());
               if (s != null ) {
                   String text = q.get(req.id()).poll();
                   if (text == null) {
                       return new Resp("new messages are abscent", 200);
                   }
                  return new Resp(text, 200);
               }
               return new Resp("subscribed", 200);
            } else {
                queue.get(req.theme()).putIfAbsent(req.id(), new ConcurrentLinkedQueue<>());
                return new Resp("topic is created and  u are subscribed", 200);
            }
        }
    }
}
