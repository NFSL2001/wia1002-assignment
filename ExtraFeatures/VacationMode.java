package ExtraFeatures;

import Functions.Post;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;

public class VacationMode {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println(checkPost("you shit, my email is mohamedtaha@gmail.com visit this website google.com"));
    }
    // returns HashMap with true values if the post does not contain rude words or personal information
    // like email, phone number, or does not contain any URL.
    public static HashMap<String, Boolean> checkPost(String comment) throws IOException, InterruptedException {
        api apiObject = apiCall(comment);
        HashMap<String, Boolean> checkMap = new HashMap<>();
        checkMap.put("polite", isPolite(apiObject));
        checkMap.put("noPersonalInfo", doesNotContainPersonalInfo(apiObject));
        checkMap.put("noURLs", doesNotContainsURL(apiObject));

        return checkMap;
    }
    public static boolean isPolite(api apiObject){
            return apiObject.profanity.matches.size() == 0;
    }

    public static boolean doesNotContainsURL(api apiObject){
        return apiObject.link.matches.size() ==0;
    }

    public static boolean doesNotContainPersonalInfo(api apiObject){
        return apiObject.link.matches.size() == 0;
    }

    public static api apiCall(String postContent) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.sightengine.com/1.0/text/check.json?text="
                        +postContent.replace(" ", "-").replace("\n", "-")+
                        "&lang=en&mode=standard"))
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());


        Gson gson = new Gson();
       return gson.fromJson(response.body(), api.class);

    }
}
class api {
    String status;
    Request request;
    Profanity profanity;
    Personal personal;
    Link link;

    @Override
    public String toString() {
        return "api{" +
                "status='" + status + '\'' +
                ", request=" + request +
                ", profanity=" + profanity +
                ", personal=" + personal +
                ", link=" + link +
                '}';
    }
}
class Profanity {
    ArrayList<Matches> matches;

    @Override
    public String toString() {
        return matches.toString();
    }
}

class Matches {
    String type;
    String intensity;
    String match;
    int start;
    int end;

    @Override
    public String toString() {
        return "Matches{" +
                "type='" + type + '\'' +
                ", intensity='" + intensity + '\'' +
                ", match='" + match + '\'' +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}

class Request {
    String id;
    double timestamp;
    int operations;

    @Override
    public String toString() {
        return "Request{" +
                "id='" + id + '\'' +
                ", timestamp=" + timestamp +
                ", operations=" + operations +
                '}';
    }
}
class Personal {
    ArrayList<Matches> matches = new ArrayList<>();

    @Override
    public String toString() {
        return "personal{" +
                "matches=" + matches +
                '}';
    }
}
class Link {
    ArrayList<LinkMatch> matches = new ArrayList<LinkMatch>();

    @Override
    public String toString() {
        return "Link{" +
                "links=" + matches.toString() +
                '}';
    }
}

class LinkMatch {
    String type;
    String category;
    String match;
    int start;
    int end;

    @Override
    public String toString() {
        return "LinkMatch{" +
                "type='" + type + '\'' +
                ", category='" + category + '\'' +
                ", match='" + match + '\'' +
                ", start='" + start + '\'' +
                ", end='" + end + '\'' +
                '}';
    }
}
//class data


