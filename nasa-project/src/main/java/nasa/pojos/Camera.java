
package nasa.pojos;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Camera {

    @SerializedName("full_name")
    private String fullName;
    @Expose
    private Long id;
    @Expose
    private String name;
    @SerializedName("rover_id")
    private Long roverId;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getRoverId() {
        return roverId;
    }

    public void setRoverId(Long roverId) {
        this.roverId = roverId;
    }

}
