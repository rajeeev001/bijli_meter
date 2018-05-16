package bijli.meter;

/**
 * Created by prashant on 14/3/18.
 */

public class CreateDevice {
    private String ID;
    private String Device;
    private String Room;
    private String Status;

    public CreateDevice(){

    }
    public  CreateDevice(String ID, String Device, String Room,String status){
        this.ID=ID;
        this.Device=Device;
        this.Room=Room;
        this.Status=status;
    }

    public String getID() {
        return ID;
    }

    public String getDevice() {
        return Device;
    }

    public String getRoom() {
        return Room;
    }
    public String getStatus(){return Status;}
}
