package sjsu.illuminate.illuminate;

/**
 * Created by Matthew on 4/6/2017.
 */

public class NfcMessage {
    private int timeline[];

    public NfcMessage(int[] tl){
        timeline = tl;
    }

    public void setTimeline(int[] newTl){
        timeline = newTl;
    }

    public String getMessage() {
        String returnString = "";
        for(int i = 0; i < timeline.length; i++){
            returnString += Integer.toString(timeline[i]);
        }
        return returnString;
    }
}