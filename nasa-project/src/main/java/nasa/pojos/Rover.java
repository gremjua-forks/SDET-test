
package nasa.pojos;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Rover {

    @Expose
    private List<Camera> cameras;
    @Expose
    private Long id;
    @SerializedName("landing_date")
    private String landingDate;
    @SerializedName("launch_date")
    private String launchDate;
    @SerializedName("max_date")
    private String maxDate;
    @SerializedName("max_sol")
    private Long maxSol;
    @Expose
    private String name;
    @Expose
    private String status;
    @SerializedName("total_photos")
    private Long totalPhotos;

    public List<Camera> getCameras() {
        return cameras;
    }

    public void setCameras(List<Camera> cameras) {
        this.cameras = cameras;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLandingDate() {
        return landingDate;
    }

    public void setLandingDate(String landingDate) {
        this.landingDate = landingDate;
    }

    public String getLaunchDate() {
        return launchDate;
    }

    public void setLaunchDate(String launchDate) {
        this.launchDate = launchDate;
    }

    public String getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(String maxDate) {
        this.maxDate = maxDate;
    }

    public Long getMaxSol() {
        return maxSol;
    }

    public void setMaxSol(Long maxSol) {
        this.maxSol = maxSol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getTotalPhotos() {
        return totalPhotos;
    }

    public void setTotalPhotos(Long totalPhotos) {
        this.totalPhotos = totalPhotos;
    }

}
