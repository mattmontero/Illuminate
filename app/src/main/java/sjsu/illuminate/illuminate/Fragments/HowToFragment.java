package sjsu.illuminate.illuminate.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import sjsu.illuminate.illuminate.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HowToFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HowToFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HowToFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private HowToFragmentInteractionListener mListener;

    GlideDrawableImageViewTarget gifTarget;
    int step = 0;

    public HowToFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HowToFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HowToFragment newInstance(String param1, String param2) {
        HowToFragment fragment = new HowToFragment();
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
        View view = inflater.inflate(R.layout.fragment_how_to, container, false);
        view.findViewById(R.id.stepImage).setOnClickListener(this);

        gifTarget = new GlideDrawableImageViewTarget((ImageView) view.findViewById(R.id.stepImage));
        Glide.with(this).load(R.drawable.stepzero).into(gifTarget);
        // Inflate the layout for this fragment
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onHowToFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof HowToFragmentInteractionListener) {
            mListener = (HowToFragmentInteractionListener) context;
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
    public void onClick(View view) {
        View parent = (View) view.getParent();

        switch (view.getId()){
            case R.id.stepImage:
                switch (step){
                case 0:
                    Glide.with(this).load(R.drawable.stepone).into(gifTarget);
                    step++;
                    break;
                case 1:
                    Glide.with(this).load(R.drawable.steptwo).into(gifTarget);
                    step++;
                    break;
                case 2:
                    Glide.with(this).load(R.drawable.stepthree).into(gifTarget);
                    step++;
                    break;
                case 3:
                    Glide.with(this).load(R.drawable.stepfour).into(gifTarget);
                    step++;
                    break;
                case 4:
                    Glide.with(this).load(R.drawable.stepfive).into(gifTarget);
                    step++;
                    break;
                case 5:
                    Glide.with(this).load(R.drawable.stepsix).into(gifTarget);
                    step++;
                    break;
            }
                break;
        }
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
    public interface HowToFragmentInteractionListener {
        // TODO: Update argument type and name
        void onHowToFragmentInteraction(Uri uri);
    }
}
