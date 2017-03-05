package sjsu.illuminate.illuminate.Fragments;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.Context;
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
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;
import pl.droidsonroids.gif.GifTextView;
import pl.droidsonroids.gif.GifTextureView;
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
        View.OnDragListener, View.OnLongClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ProjectsFragmentInteractionListener mListener;

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
        Log.d("onCreateView", "set view");
        View view = inflater.inflate(R.layout.fragment_projects, container, false);

        //Create LinearLayout
        Log.d("onCreateView", "pattern layout");
        LinearLayout patternLinearLayout = (LinearLayout) view.findViewById(R.id.patternContainer);

        //Add GIF Drawables to LinearLayout
        view.findViewById(R.id.fadeOff).setOnLongClickListener(this);
        view.findViewById(R.id.fadeOn).setOnLongClickListener(this);
        view.findViewById(R.id.spiralUp).setOnLongClickListener(this);
        view.findViewById(R.id.spiralDown).setOnLongClickListener(this);
        view.findViewById(R.id.flash).setOnLongClickListener(this);
        view.findViewById(R.id.patternContainer).setOnDragListener(this);
        view.findViewById(R.id.storyboardContainer).setOnDragListener(this);
        view.findViewById(R.id.projectBackground).setOnDragListener(this);

        // Inflate the layout for this fragment
        return view;
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

                View draggedView = (View) dragEvent.getLocalState();

                GifImageView oldDraggedView = (GifImageView) draggedView;
                GifImageView cloneDraggedView = new GifImageView(getContext());

                //Bitmap bitmap;
                //oldDraggedView.buildDrawingCache();
                //bitmap = oldDraggedView.getDrawingCache();

                switch (oldDraggedView.getId()) {
                    case R.id.spiralDown:
                        cloneDraggedView.setBackground(getResources().getDrawable(R.drawable.spiraldown));
                    case R.id.spiralUp:
                        cloneDraggedView.setBackground(getResources().getDrawable(R.drawable.spiralup));
                    case R.id.fadeOn:
                        cloneDraggedView.setBackground(getResources().getDrawable(R.drawable.fadeoff));
                    case R.id.fadeOff:
                        cloneDraggedView.setBackground(getResources().getDrawable(R.drawable.fadeon));
                    case R.id.flash:
                        cloneDraggedView.setBackground(getResources().getDrawable(R.drawable.flash));
                }

                Log.d("old scaleX", Float.toString(oldDraggedView.getScaleX()));
                Log.d("old scaleY", Float.toString(oldDraggedView.getScaleY()));

                cloneDraggedView.setScaleX((float)0.25);
                cloneDraggedView.setScaleY((float)0.5);



                //cloneDraggedView.setImageBitmap(bitmap);
                Log.d("clone","Complete");

                LinearLayout storyboardLinearLayout = (LinearLayout) parentView.findViewById(R.id.storyboardContainer);
                LinearLayout patternLinearLayout = (LinearLayout) parentView.findViewById(R.id.patternContainer);


                //ViewGroup draggedImageViewParentLayout = (ViewGroup) draggedView.getParent();
                switch (view.getId()) {
                    case R.id.storyboardContainer:
                        Log.d("Container", "storyboard");
                        //draggedImageViewParentLayout = (ViewGroup) draggedView.getParent();
                        //draggedImageViewParentLayout.removeView(draggedCopy);
                        storyboardLinearLayout.addView(cloneDraggedView);

                        return true;
                    case R.id.patternContainer:
                        Log.d("Container", "pattern");
                        //draggedImageViewParentLayout = (ViewGroup) draggedView.getParent();
                        return true;
                    case R.id.projectBackground:
                        Log.d("Container", "background");
                        return true;
                }
        }
        return true;
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public boolean onLongClick(View view) {
        Log.d("Gesture", "onLongClick");


        //The icon has been touched
        // Create clip data holding the data of the MIMETYPE TEXT_PLAIN
        ClipData clipData = ClipData.newPlainText("","");

        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder((ImageView) view);
        /*Start the drag - Contains the data to be dragged,
        * metadata for this data and callback for drawing shadow.*/
        //view.startDrag(clipData, shadowBuilder, (ImageView) view, 0);
        view.startDragAndDrop(clipData, shadowBuilder, (ImageView) view, 0);
        //view.setVisibility(View.INVISIBLE);

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
