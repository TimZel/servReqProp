import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Request {
    String method;
    String path;
    String http;
    String exe;
    Map<String, String> mapHeadersValues;
    List<String> bodyList;

    public static final Pattern patternHeaderValue = Pattern.compile("(\\p{Lu}{1,2}.*(\\:))(\\s.*)");
    public static final Pattern patternExe = Pattern.compile("(\\.[^\s]*)");
    public Request(String method, String path, String http, String exe, Map<String, String> mapHeadersValues, List bodyList) {
        this.method = method;
        this.path = path;
        this.http = http;
        this.exe = exe;
        this.mapHeadersValues = mapHeadersValues;
        this.bodyList = bodyList;
    }

    public Request(String method, String path, String http, String exe) {
        this.method = method;
        this.path = path;
        this.http = http;
        this.exe = exe;
    }
    private static Request parseRequestLine( BufferedReader reader ) throws IOException {
        String[] methodPathHttp = reader.readLine().split(" ");
        String method = methodPathHttp[0];
        String path = methodPathHttp[1];
        String http = methodPathHttp[2];

        String exe  = null;
        Matcher matcherExe = patternExe.matcher(path);
        if(matcherExe.find()) {
            exe = matcherExe.group();
        }

        return new Request( method, path, http, exe );
    }

    private static Map<String, String> parseHeaders( BufferedReader reader ) throws IOException {
        Map<String, String> result = new HashMap<>();
        while (reader.ready()) {
            String line = reader.readLine();
            if ( line.trim().isEmpty() ) {
                break;
            }

            Matcher matcher = patternHeaderValue.matcher(line);
            if (matcher.find()) {
                result.put(matcher.group(1), matcher.group(3));
            }
        }
        return result;
    }

    private static List<String> parseBody( BufferedReader reader ) throws IOException {
        List<String> result = new ArrayList<>();
        while ( reader.ready() ) {
            String line = reader.readLine();
            if ( line.trim().isEmpty() ) {
                break;
            }
            result.add( line );
        }
        return result;
    }

    public static Request parse(InputStream isr) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(isr));
            Request result = parseRequestLine(reader);
            result.mapHeadersValues = parseHeaders(reader);
            result.bodyList = parseBody(reader);
            return result;
        }
        catch ( Throwable th ) {
            return null;
        }
    }
}





