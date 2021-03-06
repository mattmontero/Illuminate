package sjsu.illuminate.illuminate.Fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import sjsu.illuminate.illuminate.Activities.MainActivity;
import sjsu.illuminate.illuminate.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LearnMoreFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LearnMoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LearnMoreFragment extends Fragment implements View.OnClickListener {
    //About us page
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private LearnMoreFragmentInteractionListener mListener;

    public LearnMoreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LearnMoreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LearnMoreFragment newInstance(String param1, String param2) {
        LearnMoreFragment fragment = new LearnMoreFragment();
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
        View view = inflater.inflate(R.layout.fragment_learn_more, container, false);
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/Frontage-Regular.otf");


        TextView learnMoreTitle = (TextView) view.findViewById(R.id.learnMoreTitlePage);
        Button learnMoreHunger = (Button) view.findViewById(R.id.learnMoreHunger);
        Button learnMoreDream = (Button) view.findViewById(R.id.learnMoreDream);
        Button learnAboutUs = (Button) view.findViewById(R.id.learnMoreAboutUs);

        //set font
        learnMoreTitle.setTypeface(typeface);

        //
        learnMoreHunger.setOnClickListener(this);
        learnMoreDream.setOnClickListener(this);
        learnAboutUs.setOnClickListener(this);

        // Inflate the layout for this fragment
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onLearnMoreFragmentInteraction(uri);
        }
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof LearnMoreFragmentInteractionListener) {
            mListener = (LearnMoreFragmentInteractionListener) context;
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
        MainActivity mainActivity = (MainActivity) getActivity();
        Log.d("onClick"," in Onclick with " + view.getId());
        switch(view.getId()){
            case R.id.learnMoreAboutUs:
                mainActivity.loadAboutUs();
                break;
            case R.id.learnMoreHunger:
                mainActivity.loadLearnMoreHunger();
                break;
            case R.id.learnMoreDream:
                mainActivity.loadLearnMoreDream();
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
    public interface LearnMoreFragmentInteractionListener {
        // TODO: Update argument type and name
        void onLearnMoreFragmentInteraction(Uri uri);
    }
}
