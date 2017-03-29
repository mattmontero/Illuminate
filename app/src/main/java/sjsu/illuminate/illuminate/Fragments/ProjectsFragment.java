package sjsu.illuminate.illuminate.Fragments;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.Context;
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
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;
import pl.droidsonroids.gif.GifTextView;
import pl.droidsonroids.gif.GifTextureView;
import sjsu.illuminate.illuminate.GifIcon;
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

    private enum gifImage {

    }

    final private int PINK = 1;
    final private int RED = 2;
    final private int ORANGE = 3;
    final private int YELLOW = 4;
    final private int GREEN = 5;
    final private int BLUE = 6;
    final private int PURPLE = 7;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ProjectsFragmentInteractionListener mListener;
    RelativeLayout slot1, slot2, slot3, slot4, slot5, slot6;
    ImageView spiralUp, spiralDown, fadeOn, fadeOff, flash;
    LinearLayout patternLinearLayout;

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
        //Get view
        View view = inflater.inflate(R.layout.fragment_projects, container, false);
        Log.d("View", view.toString());
        defineLayouts(view);
        defineGIFs(view, PINK);
        setListeners(view);

        // Inflate the layout for this fragment
        return view;
    }
    private void defineGIFs(View view, int color){
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
                    + " must implement OnFragmentInteractionListener");
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
        Log.d("View Boundaries", view.toString());

        switch (dragEvent.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                Log.d("DragEvent", "ACTION_DRAG_STARTED");

                //Determine if this View can accept the dragged data
                if(dragEvent.getClipDescription().hasMimeType(MIMETYPE_TEXT_PLAIN)) {
                    return true;
                }
                return false;
            case DragEvent.ACTION_DROP:
                Log.d("ACTION_DROP", "Dropped");
                View parentView = (View) view.getParent();

                //View being dragged
                View draggedView = (View) dragEvent.getLocalState();

                //Same view but now a GifImageView being dragged
                ImageView oldDraggedView = (ImageView) draggedView;

                //Creating new cloned image of what is being dragged
                ImageView cloneDraggedView = new ImageView(getContext());

                //set the copy background
                cloneDraggedView.setBackground(oldDraggedView.getBackground());
                Log.d("old background", oldDraggedView.getBackground().toString());

                //set the copy layoutparams
                cloneDraggedView.setLayoutParams(oldDraggedView.getLayoutParams());

                //set the onlongclicklistener to delete
                cloneDraggedView.setOnLongClickListener(this);

                Log.d("clone","Complete");

                if(oldDraggedView.getParent() != patternLinearLayout){
                    //offer to delete or to copy to new slot.

                } else {
                    GlideDrawableImageViewTarget gifTarget;
                    switch (view.getId()) {
                        case R.id.slot1:
                            slot1.removeAllViews();
                            slot1.addView(cloneDraggedView);
                            gifTarget = new GlideDrawableImageViewTarget(cloneDraggedView);
                            Glide.with(this).load(R.drawable.flashblue).into(gifTarget);
                            return true;
                        case R.id.slot2:
                            slot2.removeAllViews();
                            slot2.addView(cloneDraggedView);
                            gifTarget = new GlideDrawableImageViewTarget(cloneDraggedView);
                            Glide.with(this).load(R.drawable.flashblue).into(gifTarget);
                            return true;
                        case R.id.slot3:
                            slot3.removeAllViews();
                            slot3.addView(cloneDraggedView);
                            gifTarget = new GlideDrawableImageViewTarget(cloneDraggedView);
                            Glide.with(this).load(R.drawable.flashblue).into(gifTarget);
                            return true;
                        case R.id.slot4:
                            slot4.removeAllViews();
                            slot4.addView(cloneDraggedView);
                            gifTarget = new GlideDrawableImageViewTarget(cloneDraggedView);
                            Glide.with(this).load(R.drawable.flashblue).into(gifTarget);
                            return true;
                        case R.id.slot5:
                            slot5.removeAllViews();
                            slot5.addView(cloneDraggedView);
                            gifTarget = new GlideDrawableImageViewTarget(cloneDraggedView);
                            Glide.with(this).load(R.drawable.flashblue).into(gifTarget);
                            return true;
                        case R.id.slot6:
                            slot6.removeAllViews();
                            slot6.addView(cloneDraggedView);
                            gifTarget = new GlideDrawableImageViewTarget(cloneDraggedView);
                            Glide.with(this).load(R.drawable.flashblue).into(gifTarget);
                            return true;
                    }
                }
        }
        return true;
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

        if(view.getParent() != patternLinearLayout) {
            view.setVisibility(View.INVISIBLE);
        }

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
}
