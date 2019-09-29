
package nasa.pojos;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Photo {

    @Expose
    private Camera camera;
    @SerializedName("earth_date")
    private String earthDate;
    @Expose
    private Long id;
    @SerializedName("img_src")
    private String imgSrc;
    @Expose
    private Rover rover;
    @Expose
    private Long sol;

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public String getEarthDate() {
        return earthDate;
    }

    public void setEarthDate(String earthDate) {
        this.earthDate = earthDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public Rover getRover() {
        return rover;
    }

    public void setRover(Rover rover) {
        this.rover = rover;
    }

    public Long getSol() {
        return sol;
    }

    public void setSol(Long sol) {
        this.sol = sol;
    }

}
