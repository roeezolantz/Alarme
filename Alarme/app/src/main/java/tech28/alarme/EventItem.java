package tech28.alarme;

import org.json.JSONObject;

/**
 * Created by hack on 8/28/2016.
 */
public class EventItem {

    private String fromNumber;
    private String toNumber;
    private String message;
    private JSONObject geoLocation;

    public EventItem() {
        this.message = "";
        this.fromNumber = "";
        this.toNumber = "";
        this.geoLocation = null;
    }

    public EventItem(String fromNumber, String toNumber, String message, JSONObject geoLocation) {
        this.fromNumber = fromNumber;
        this.toNumber = toNumber;
        this.message = message;
        this.geoLocation = geoLocation;
    }

    public String getFromNumber() {
        return fromNumber;
    }

    public void setFromNumber(String fromNumber) {
        this.fromNumber = fromNumber;
    }

    public String getToNumber() {
        return toNumber;
    }

    public void setToNumber(String toNumber) {
        this.toNumber = toNumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JSONObject getGeoLocation() {
        return geoLocation;
    }

    public void setGeoLocation(JSONObject geoLocation) {
        this.geoLocation = geoLocation;
    }
}
