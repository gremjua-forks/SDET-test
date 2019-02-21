package automationpilot.sut_model;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Key;
import gherkin.deps.com.google.gson.Gson;
import gherkin.deps.com.google.gson.GsonBuilder;

import java.util.List;

public class CuriosityAPI {

    public Photo photos;
    public PhotoFeed photoFeed;
    public Rover rover;
    public ApiUrl apiUrl;

    public HttpRequestFactory requestFactory;

    private static HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private static JsonFactory JSON_FACTORY = new JacksonFactory();

    public CuriosityAPI() {
        requestFactory =
                HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {
                    @Override
                    public void initialize(HttpRequest request) {
                        request.setParser(new JsonObjectParser(JSON_FACTORY));
                    }
                });
    }

    public static class PhotoFeed {

        @Key
        public List<Photo> photos;
    }

    public static class Photo {
        @Key
        public int id;

        @Key
        public int sol;

        @Key
        public CuriosityCamera camera;

        @Key("img_src")
        public String imgSrc;

        @Key
        public Rover rover;

        @Key("earth_date")
        public String earthDate;

        public boolean equals(Photo o) {
            if (this == o) {
                return true;
            } else if (o == null) {
                return false;
            } else {
                return (this.id == o.id) && (this.sol == o.sol) && (this.imgSrc.equals(o.imgSrc)) &&
                        (this.camera.equals(o.camera)) && (this.rover.equals(o.rover));
            }
        }

        public String prettyPrint() {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String prettyJson = gson.toJson(this);

            return prettyJson;
        }

        // GETTERS & SETTERS
        public CuriosityCamera getCamera() {
            return camera;
        }
    }

    public static class CuriosityCamera {
        @Key
        public int id;

        @Key
        public String name;

        @Key("rover_id")
        public int roverId;

        @Key("full_name")
        public String fullName;

        public boolean equals(CuriosityCamera o) {
            if (this == o) {
                return true;
            } else if (o == null) {
                return false;
            } else {
                return ((this.id == o.id) && (this.name.equals(o.name)) &&
                        (this.roverId == o.roverId) && (this.fullName.equals(o.fullName)));
            }
        }

        // GETTERS & SETTERS
        public String getName() {
            return name;
        }
    }

    public static class Rover {
        @Key
        public int id;

        @Key
        public String name;

        @Key("landing_date")
        public String landingDate;

        @Key("launch_date")
        public String launchDate;

        @Key("max_sol")
        public int maxSol;

        @Key("max_date")
        public String maxDate;

        @Key
        public List<GenericJson> cameras;

        public boolean equals(Rover o) {
            if (this == o) {
                return true;
            } else if (o == null) {
                return false;
            } else {
                return ((this.id == o.id) && (this.landingDate.equals(o.landingDate)) &&
                        (this.launchDate.equals(o.launchDate)) && (this.maxSol == o.maxSol) &&
                        (this.maxDate.equals(o.maxDate)) && (this.cameras.equals(o.cameras)));
            }
        }
    }

    public static class ApiUrl extends GenericUrl {
        @Key
        String sol;  // package private

        @Key("earth_date")
        String earthDate;

        @Key("api_key")
        String apiKey;

        // GETTERS & SETTERS
        public ApiUrl(String encodedUrl) {
            super(encodedUrl);
        }

        public String getSol() {
            return sol;
        }

        public void setSol(String sol) {
            this.sol = sol;
        }

        public String getEarthDate() {
            return earthDate;
        }

        public void setEarthDate(String earthDate) {
            this.earthDate = earthDate;
        }

        public String getApiKey() {
            return apiKey;
        }

        public void setApiKey(String apiKey) {
            this.apiKey = apiKey;
        }
    }
}
