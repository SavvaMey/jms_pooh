package job4j;

import java.util.Arrays;

public class Req {
    private final String method;
    private final String mode;
    private final String theme;
    private final String message;
    private final int id;

    private Req(String method, String mode, String theme, String message, int id) {
        this.method = method;
        this.mode = mode;
        this.theme = theme;
        this.message = message;
        this.id = id;
        System.out.println(method + " " + mode + " " + theme + " " + message + " " + id);
    }
// POST /queue/weather -d "temperature=18"
// GET /queue/weather
    public static Req of(String content) {
        int id = -1;
        String mode = "";
        String method = "";
        String theme = "";
        String message = "";
        String[] ar = content.replaceAll("\\s-d\\s", "/").replaceAll("(\"|\\s)", "")
                .split("/");
        Arrays.stream(ar).forEach(System.out::println);
        mode = ar[1];
        method = ar[0];
        theme = ar[2];
        if (ar.length > 3) {
            if (method.equals("GET")) {
                id = Integer.parseInt(ar[3]);
            } else {
                message = ar[3];
            }
        }


        return new Req(method, mode, theme, message, id);
    }

    public String method() {
        return method;
    }

    public String mode() {
        return mode;
    }

    public String theme() {
        return theme;
    }

    public String message() {
        return message;
    }
    public int id() {
        return id;
    }
    

    public static void main(String[] args) {
         Req.of("GET /queue/weather");
    }
}
