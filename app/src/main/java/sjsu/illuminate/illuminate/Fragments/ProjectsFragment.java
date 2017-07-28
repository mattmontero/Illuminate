package sjsu.illuminate.illuminate.Fragments;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.ContextMenu;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Switch;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;
import pl.droidsonroids.gif.GifTextView;
import pl.droidsonroids.gif.GifTextureView;
import sjsu.illuminate.illuminate.GifIcon;
import sjsu.illuminate.illuminate.NfcMessage;
import sjsu.illuminate.illuminate.R;

import static android.content.ClipDescription.MIMETYPE_TEXT_PLAIN;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProjectsFragment.ProjectsFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProjectsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProjectsFragment extends Fragment implements
        View.OnDragListener, View.OnLongClickListener, OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    final private int PINK = 1;
    final private int RED = 2;
    final private int ORANGE = 3;
    final private int YELLOW = 4;
    final private int GREEN = 5;
    final private int BLUE = 6;
    final private int PURPLE = 7;

    private BluetoothAdapter mBluetoothAdapter;
    private ConnectThread mConnectThread;
    private ConnectedThread mConnectedThread;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Map<Integer, Character> iconToChar;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private int currentColor;
    private int[] timelineDrawableID;

    private ProjectsFragmentInteractionListener mListener;
    private RelativeLayout slot1, slot2, slot3, slot4, slot5, slot6;
    private ImageView spiralUp, spiralDown, fadeOn, fadeOff, flash;
    private LinearLayout patternLinearLayout;

    public ProjectsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProjectsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProjectsFragment newInstance(String param1, String param2) {
        ProjectsFragment fragment = new ProjectsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        createIdToCharMap();

        //Get view
        View view = inflater.inflate(R.layout.fragment_projects, container, false);
        timelineDrawableID = new int[]{0,0,0,0,0,0};
        updateCreateButton((Button) view.findViewById(R.id.createButton));
        Log.d("View", view.toString());
        defineLayouts(view);
        defineGIFs(view, PINK);
        setListeners(view);



        //1. Check if bluetooth is supported
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(mBluetoothAdapter == null){
            //Device does not support Bluetooth.
        }
        //2. Check if bluetooth is enabled
        if(!mBluetoothAdapter.isEnabled()){
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 6969);
        }
        //3. Get the Bluetooth module device
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        BluetoothDevice mDevice = null;
        if(pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                if(device.getName().equals("HC-06")) {
                    mDevice = device;
                    Log.d("device name", device.getName());
                    Log.d("device toString", device.toString());
                    break;
                }
            }
        }
        //4a. Create the connection thread
        mConnectThread = new ConnectThread(mDevice);
        Log.d("ConnectThread", "created");
        mConnectThread.start();
        Log.d("ConnectThread", "Running...");



        // Inflate the layout for this fragment
        return view;
    }

    /*
    int PINK = abcde;
    int RED = ABCDE;
    int ORANGE = fghij;
    int YELLOW = FGHIJ;
    int GREEN = klmno;
    int BLUE = KLMNO;
    int PURPLE = pqrst;
    */
    private void createIdToCharMap(){
        iconToChar = new HashMap<Integer, Character>();
        char[] charMap = new char[]
                {'a','b','c','d','e',
                 'A','B','C','D','E',
                 'f','g','h','i','j',
                 'F','G','H','I','J',
                 'k','l','m','n','o',
                 'K','L','M','N','O',
                 'p','q','r','s','t'};
        int charMapPosition = 0;
        for(int i = 1; i < 8; i++)
        {
            GifIcon gifIcon = new GifIcon(i);
            for(int j = 0; j < gifIcon.getIcons().length; j++){
                //Log.d("adding char", Character.toString(charMap[charMapPosition]));
                iconToChar.put(gifIcon.getIcons()[j], charMap[charMapPosition]);
                charMapPosition++;
            }
        }
    }

    private void defineGIFs(View view, int color){

        currentColor = color;

        GlideDrawableImageViewTarget spiralDownTarget;
        GlideDrawableImageViewTarget spiralUpTarget;
        GlideDrawableImageViewTarget fadeOnTarget;
        GlideDrawableImageViewTarget fadeOffTarget;
        GlideDrawableImageViewTarget flashTarget;

        GifIcon icons = new GifIcon(color);

        for(int i = 0; i < icons.getIcons().length; i++)
        {
            switch (i){
                case 0:
                    spiralDown = (ImageView) view.findViewById(R.id.spiralDown);
                    spiralDownTarget = new GlideDrawableImageViewTarget(spiralDown);
                    Glide.with(this).load(icons.getIcons()[i]).into(spiralDownTarget);
                    break;
                case 1:
                    spiralUp = (ImageView) view.findViewById(R.id.spiralUp);
                    spiralUpTarget = new GlideDrawableImageViewTarget(spiralUp);
                    Glide.with(this).load(icons.getIcons()[i]).into(spiralUpTarget);
                    break;
                case 2:
                    fadeOn = (ImageView) view.findViewById(R.id.fadeOn);
                    fadeOnTarget = new GlideDrawableImageViewTarget(fadeOn);
                    Glide.with(this).load(icons.getIcons()[i]).into(fadeOnTarget);
                    break;
                case 3:
                    fadeOff = (ImageView) view.findViewById(R.id.fadeOff);
                    fadeOffTarget = new GlideDrawableImageViewTarget(fadeOff);
                    Glide.with(this).load(icons.getIcons()[i]).into(fadeOffTarget);
                    break;
                case 4:
                    flash = (ImageView) view.findViewById(R.id.flash);
                    flashTarget = new GlideDrawableImageViewTarget(flash);
                    Glide.with(this).load(icons.getIcons()[i]).into(flashTarget);
                    break;
            }
        }
    }
    private void defineLayouts(View view){
        //Create LinearLayout
        patternLinearLayout = (LinearLayout) view.findViewById(R.id.patternContainer);
        //define slots
        slot1 = (RelativeLayout) view.findViewById(R.id.slot1);
        slot2 = (RelativeLayout) view.findViewById(R.id.slot2);
        slot3 = (RelativeLayout) view.findViewById(R.id.slot3);
        slot4 = (RelativeLayout) view.findViewById(R.id.slot4);
        slot5 = (RelativeLayout) view.findViewById(R.id.slot5);
        slot6 = (RelativeLayout) view.findViewById(R.id.slot6);
    }

    private void setListeners(View view){
        view.findViewById(R.id.createButton).setOnClickListener(this);

        //Add GIF Drawables to LinearLayout
        fadeOff.setOnLongClickListener(this);
        fadeOn.setOnLongClickListener(this);
        spiralUp.setOnLongClickListener(this);
        spiralDown.setOnLongClickListener(this);
        flash.setOnLongClickListener(this);

        //Set patternContainer listener and Slot listeners
        view.findViewById(R.id.patternContainer).setOnDragListener(this);
        slot1.setOnDragListener(this);
        slot2.setOnDragListener(this);
        slot3.setOnDragListener(this);
        slot4.setOnDragListener(this);
        slot5.setOnDragListener(this);
        slot6.setOnDragListener(this);
        view.findViewById(R.id.trashbin).setOnDragListener(this);
        view.findViewById(R.id.projectBackground).setOnDragListener(this);

        //Set color palette listeners
        view.findViewById(R.id.pinkButton).setOnClickListener(this);
        view.findViewById(R.id.redButton).setOnClickListener(this);
        view.findViewById(R.id.orangeButton).setOnClickListener(this);
        view.findViewById(R.id.yellowButton).setOnClickListener(this);
        view.findViewById(R.id.greenButton).setOnClickListener(this);
        view.findViewById(R.id.blueButton).setOnClickListener(this);
        view.findViewById(R.id.purpleButton).setOnClickListener(this);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onProjectsFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ProjectsFragmentInteractionListener) {
            mListener = (ProjectsFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement LearnDreamFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public boolean onDrag(View view, DragEvent dragEvent) {
        /*
            ACTION_DRAG_STARTED     = 1
            ACTION_DRAG_ENTERED     = 5
            ACTION_DRAG_LOCATION    = 2
            ACTION_DRAG_EXITED      = 6
            ACTION_DROP             = 3
            ACTION_DRAG_ENDED       = 4
         */
        //Log.d("View Boundaries", view.toString());

        switch (dragEvent.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                //Log.d("DragEvent", "ACTION_DRAG_STARTED");

                //Determine if this View can accept the dragged data
                return dragEvent.getClipDescription().hasMimeType(MIMETYPE_TEXT_PLAIN);
            case DragEvent.ACTION_DROP:
                Log.d("ACTION_DROP", "Dropped");
                //View parentView = (View) view.getParent();

                //View being dragged
                View draggedView = (View) dragEvent.getLocalState();

                Log.d("dragged View parent", draggedView.getParent().toString());

                int imageViewId = draggedView.getId();

                //Same view but now a GifImageView being dragged
                ImageView oldDraggedView = (ImageView) draggedView;

                //Creating new cloned image of what is being dragged
                ImageView cloneDraggedView = new ImageView(getContext());

                //set the copy background
                cloneDraggedView.setBackground(oldDraggedView.getBackground());

                //set the copy layoutparams
                cloneDraggedView.setLayoutParams(oldDraggedView.getLayoutParams());

                //set the onlongclicklistener to delete
                cloneDraggedView.setOnLongClickListener(this);

                //Log.d("oldDraggedView", oldDraggedView.getParent().toString());

                int rDrawableIcon = getDrawableIconId(imageViewId);
                GlideDrawableImageViewTarget gifTarget;
                gifTarget = new GlideDrawableImageViewTarget(cloneDraggedView);

                if(oldDraggedView.getParent() == patternLinearLayout){
                    switch (view.getId()) {
                        case R.id.slot1:
                            slot1.removeAllViews();
                            slot1.addView(cloneDraggedView);
                            addToTimeline(0, rDrawableIcon);
                            break;
                        case R.id.slot2:
                            slot2.removeAllViews();
                            slot2.addView(cloneDraggedView);
                            addToTimeline(1, rDrawableIcon);
                            break;
                        case R.id.slot3:
                            slot3.removeAllViews();
                            slot3.addView(cloneDraggedView);
                            addToTimeline(2, rDrawableIcon);
                            break;
                        case R.id.slot4:
                            slot4.removeAllViews();
                            slot4.addView(cloneDraggedView);
                            addToTimeline(3, rDrawableIcon);
                            break;
                        case R.id.slot5:
                            slot5.removeAllViews();
                            slot5.addView(cloneDraggedView);
                            addToTimeline(4, rDrawableIcon);
                            break;
                        case R.id.slot6:
                            slot6.removeAllViews();
                            slot6.addView(cloneDraggedView);
                            addToTimeline(5, rDrawableIcon);
                            break;
                        default:
                            break;
                    }
                    Glide.with(this).load(rDrawableIcon).into(gifTarget);
                } else {
                    int slotPosition = getSlotPosition((RelativeLayout) oldDraggedView.getParent());
                    rDrawableIcon = timelineDrawableID[slotPosition];
                    //offer to delete or to copy to new slot.
                   switch (view.getId()){
                        case R.id.slot1:
                            removeAllViews((RelativeLayout) oldDraggedView.getParent());
                            slot1.removeAllViews();
                            slot1.addView(cloneDraggedView);
                            //timelineDrawableID[getSlotPosition((RelativeLayout) oldDraggedView.getParent())] = 0;
                            removeFromTimeline(slotPosition);
                            addToTimeline(0, rDrawableIcon);
                            break;
                        case R.id.slot2:
                            removeAllViews((RelativeLayout) oldDraggedView.getParent());
                            slot2.removeAllViews();
                            slot2.addView(cloneDraggedView);
                            //timelineDrawableID[getSlotPosition((RelativeLayout) oldDraggedView.getParent())] = 0;
                            removeFromTimeline(slotPosition);
                            addToTimeline(1, rDrawableIcon);
                            break;
                        case R.id.slot3:
                            removeAllViews((RelativeLayout) oldDraggedView.getParent());
                            slot3.removeAllViews();
                            slot3.addView(cloneDraggedView);
                            //timelineDrawableID[getSlotPosition((RelativeLayout) oldDraggedView.getParent())] = 0;
                            removeFromTimeline(slotPosition);
                            addToTimeline(2, rDrawableIcon);
                            break;
                        case R.id.slot4:
                            removeAllViews((RelativeLayout) oldDraggedView.getParent());
                            slot4.removeAllViews();
                            slot4.addView(cloneDraggedView);
                            //timelineDrawableID[getSlotPosition((RelativeLayout) oldDraggedView.getParent())] = 0;
                            removeFromTimeline(slotPosition);
                            addToTimeline(3, rDrawableIcon);
                            break;
                        case R.id.slot5:
                            removeAllViews((RelativeLayout) oldDraggedView.getParent());
                            slot5.removeAllViews();
                            slot5.addView(cloneDraggedView);
                            //timelineDrawableID[getSlotPosition((RelativeLayout) oldDraggedView.getParent())] = 0;
                            removeFromTimeline(slotPosition);
                            addToTimeline(4, rDrawableIcon);
                            break;
                        case R.id.slot6:
                            removeAllViews((RelativeLayout) oldDraggedView.getParent());
                            slot6.removeAllViews();
                            slot6.addView(cloneDraggedView);
                            //timelineDrawableID[getSlotPosition((RelativeLayout) oldDraggedView.getParent())] = 0;
                            removeFromTimeline(slotPosition);
                            addToTimeline(5, rDrawableIcon);
                            break;
                        case R.id.trashbin:
                            //timelineDrawableID[getSlotPosition((RelativeLayout) oldDraggedView.getParent())] = 0;
                            removeFromTimeline(slotPosition);
                            removeAllViews((RelativeLayout) oldDraggedView.getParent());
                            //oldDraggedView.getParent().removeAllViews();
                            //oldDraggedView.delete();
                            break;
                        default:
                            break;
                    }
                    Glide.with(this).load(rDrawableIcon).into(gifTarget);
                }
        }
        return true;
    }

    private int getSlotPosition(RelativeLayout slot){
        Log.d("slot", slot.toString());
        Log.d("slot", Integer.toString(slot.getId()));
        switch (slot.getId()) {
            case R.id.slot1:
                return 0;
            case R.id.slot2:
                return 1;
            case R.id.slot3:
                return 2;
            case R.id.slot4:
                return 3;
            case R.id.slot5:
                return 4;
            case R.id.slot6:
                return 5;
            default:
                return -1;
        }
    }
    private void removeAllViews(RelativeLayout view){
        view.removeAllViews();
    }

    private void addToTimeline(int slot, int rDrawableId){
        Log.d("Timeline","Adding...");
        timelineDrawableID[slot] = rDrawableId;
        updateCreateButton((Button) getView().findViewById(R.id.createButton));
    }
    private void removeFromTimeline(int slot){
        timelineDrawableID[slot] = 0;
        updateCreateButton((Button) getView().findViewById(R.id.createButton));
    }
    private void updateCreateButton(Button createButton) {
        Log.d("CreateButton", "checking...");
        for(int i = 0; i < timelineDrawableID.length; i++){
            if(timelineDrawableID[i] == 0)
            {
                Log.d("CreateButton", "Timeline is not full");
                createButton.setBackgroundColor(Color.DKGRAY);
                createButton.setClickable(false);
                return;
            }
        }
        Log.d("CreateButton", "Timeline is full");
        createButton.setBackgroundColor(getResources().getColor(R.color.illuminateGreen));
        createButton.setClickable(true);
        Log.d("CreateButton", "set to be clickable");
    }

    private void createNfcMessage(){
        //PopupWindow popupWindow = new PopupWindow(200, 500);
        //popupWindow.setTouchable(true);
        int firstId = timelineDrawableID[0];
        int index;
        for(index = 0; index < timelineDrawableID.length; index++){
            if (firstId != timelineDrawableID[index]){
                break;
            }
        }
        if(index == timelineDrawableID.length){
            try {
                Log.d("Bluetooth char", "x");
                mConnectedThread.write("x".getBytes()
                );
            } catch (NullPointerException e) {
                Log.d("Null ConnectedThread", e.toString());
            }
        } else {
            try {
                for (int i = 0; i < timelineDrawableID.length; i++) {
                    Log.d("Bluetooth char", Character.toString(iconToChar.get(timelineDrawableID[i])));
                    mConnectedThread.write((Character.toString((iconToChar.get(timelineDrawableID[i]))).getBytes()));
                }
            } catch (NullPointerException e) {
                Log.d("Null ConnectedThread", e.toString());
            }
        }

        //NfcMessage nfcMessage = new NfcMessage(timelineDrawableID);
        //Log.d("NFC Message", nfcMessage.getMessage());
    }

    private int getDrawableIconId(int imageViewId){
        GifIcon icons = new GifIcon(currentColor);
        /*
        spiralup
        spiraldown
        fadeon
        fadeoff
        flash
         */
        switch (imageViewId){
            case R.id.spiralUp:
                return icons.getIcons()[0];
            case R.id.spiralDown:
                return icons.getIcons()[1];
            case R.id.fadeOn:
                return icons.getIcons()[2];
            case R.id.fadeOff:
                return icons.getIcons()[3];
            case R.id.flash:
                return icons.getIcons()[4];
        }
        return 0;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.pinkButton:
                defineGIFs(getActivity().findViewById(R.id.projectBackground), PINK);
                break;
            case R.id.redButton:
                defineGIFs(getActivity().findViewById(R.id.projectBackground), RED);
                break;
            case R.id.orangeButton:
                defineGIFs(getActivity().findViewById(R.id.projectBackground), ORANGE);
                break;
            case R.id.yellowButton:
                defineGIFs(getActivity().findViewById(R.id.projectBackground), YELLOW);
                break;
            case R.id.greenButton:
                defineGIFs(getActivity().findViewById(R.id.projectBackground), GREEN);
                break;
            case R.id.blueButton:
                defineGIFs(getActivity().findViewById(R.id.projectBackground), BLUE);
                break;
            case R.id.purpleButton:
                defineGIFs(getActivity().findViewById(R.id.projectBackground), PURPLE);
                break;
            case R.id.createButton:
                createNfcMessage();
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public boolean onLongClick(View view) {
        Log.d("Gesture", "onLongClick");

        //The icon has been touched
        // Create clip data holding the data of the MIMETYPE TEXT_PLAIN
        ClipData clipData = ClipData.newPlainText("","");

        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
        /*Start the drag - Contains the data to be dragged,
        * metadata for this data and callback for drawing shadow.*/
        //view.startDrag(clipData, shadowBuilder, (ImageView) view, 0);
        view.startDragAndDrop(clipData, shadowBuilder, view, 0);

        return true;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface ProjectsFragmentInteractionListener {
        // TODO: Update argument type and name
        void onProjectsFragmentInteraction(Uri uri);
    }




    /*
    Bluetooth code begins here
        ConnectThread
        ConnectedThread
        Handler
     */
    private class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

        public ConnectThread(BluetoothDevice device) {
            BluetoothSocket tmp = null;
            try {
                tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
                Log.d("UUID", MY_UUID.toString());
                Log.d("socket", tmp.toString());
            } catch (IOException e) {
                Log.d("temp Socket", e.toString());
            }
            mmSocket = tmp;
        }

        public void run() {
            mBluetoothAdapter.cancelDiscovery();
            try {
                mmSocket.connect();
            } catch (IOException connectException) {
                Log.d("Connect Exception", connectException.toString());
                try {
                    mmSocket.close();
                } catch (IOException closeException) {
                    Log.d("Close Exception", closeException.toString());
                }
                return;
            }

            mConnectedThread = new ConnectedThread(mmSocket);
            Log.d("ConnectedThread", "created");
            mConnectedThread.start();
            Log.d("connectedThread", "Running...");
        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {}
        }
    }

    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tempIn = null;
            OutputStream tempOut = null;
            try {
                tempIn = socket.getInputStream();
                tempOut = socket.getOutputStream();
            } catch (IOException e) { }

            mmInStream = tempIn;
            mmOutStream = tempOut;
        }

        public void run() {
            byte[] buffer = new byte[1024];
            int begin = 0;
            int bytes = 0;

            while (true) {
                try {
                    Log.d("bytes", "" + bytes);
                    bytes += mmInStream.read(buffer, bytes, buffer.length - bytes);
                    for(int i = begin; i < bytes; i++) {
                        if(buffer[i] == "#".getBytes()[0]) {
                            Log.d("found #", mHandler.toString());
                            mHandler.obtainMessage(1, begin, i, buffer).sendToTarget();
                            begin = i+1;
                            if(i == bytes -1) {
                                bytes = 0;
                                begin = 0;
                            }
                        }
                    }
                } catch (IOException e) {
                    Log.d("something happened", e.toString());
                    break;
                }
            }
        }

        public void write (byte[] bytes) {
            try{
                mmOutStream.write(bytes);
            } catch (IOException e) {}
        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {}
        }
    }
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            byte[] writeBuf = (byte[]) msg.obj;
            int begin = (int) msg.arg1;
            int end = (int) msg.arg2;
            Log.d("Handler","message");
            switch (msg.what) {
                case 1:
                    String writeMessage = new String(writeBuf);
                    writeMessage = writeMessage.substring(begin, end);
                    Log.d("receiving", writeMessage.toString());

                    break;
            }
        }
    };

}
