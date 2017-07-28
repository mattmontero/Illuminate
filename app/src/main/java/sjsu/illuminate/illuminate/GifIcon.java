package sjsu.illuminate.illuminate;

import android.content.Intent;
import android.util.Log;

/**
 * Created by Matthew on 3/28/2017.
 */

public class GifIcon {

    final private int PINK = 1;
    final private int RED = 2;
    final private int ORANGE = 3;
    final private int YELLOW = 4;
    final private int GREEN = 5;
    final private int BLUE = 6;
    final private int PURPLE = 7;

    private int color;
    private int[] icons = new int[5];

    public GifIcon(int color)
    {
        Log.d("fadeoffblue", Integer.toString(R.drawable.fadeoffblue));
        this.color = color;
        setColor();
    }

    public void setColor(int color) {
        this.color = color;
    }

    private void setColor(){
        switch (color) {
            case PINK:
                icons[0] = R.drawable.spiraldownpink;
                icons[1] = R.drawable.spiraluppink;
                icons[2] = R.drawable.fadeonpink;
                icons[3] = R.drawable.fadeoffpink;
                icons[4] = R.drawable.flashpink;
                break;
            case RED:
                icons[0] = R.drawable.spiraldownred;
                icons[1] = R.drawable.spiralupred;
                icons[2] = R.drawable.fadeonredd;
                icons[3] = R.drawable.fadeoffred;
                icons[4] = R.drawable.flashred;
                break;
            case ORANGE:
                icons[0] = R.drawable.spiraldownorange;
                icons[1] = R.drawable.spiraluporange;
                icons[2] = R.drawable.fadeonorange;
                icons[3] = R.drawable.fadeofforange;
                icons[4] = R.drawable.flashorange;
                break;
            case YELLOW:
                icons[0] = R.drawable.spiraldownyellow;
                icons[1] = R.drawable.spiralupyellow;
                icons[2] = R.drawable.fadeonyellow;
                icons[3] = R.drawable.fadeoffyellow;
                icons[4] = R.drawable.flashyellow;
                break;
            case GREEN:
                icons[0] = R.drawable.spiraldowngreen;
                icons[1] = R.drawable.spiralupgreen;
                icons[2] = R.drawable.fadeongreen;
                icons[3] = R.drawable.fadeoffgreen;
                icons[4] = R.drawable.flashgreen;
                break;
            case BLUE:
                icons[0] = R.drawable.spiraldownblue;
                icons[1] = R.drawable.spiralupblue;
                icons[2] = R.drawable.fadeonblue;
                icons[3] = R.drawable.fadeoffblue;
                icons[4] = R.drawable.flashblue;
                break;
            case PURPLE:
                icons[0] = R.drawable.spiraldownpurple;
                icons[1] = R.drawable.spiraluppurple;
                icons[2] = R.drawable.fadeonpurple;
                icons[3] = R.drawable.fadeoffpurple;
                icons[4] = R.drawable.flashpurple;
                break;
        }

    }

    public  int[] getIcons() {
        return icons;
    }

}
