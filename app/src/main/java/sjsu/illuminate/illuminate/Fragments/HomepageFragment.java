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
 * {@link HomepageFragment.HomepageFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomepageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomepageFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button projectsButton, howToButton, submissionsButton, aboutUsButton;

    private HomepageFragmentInteractionListener mListener;

    public HomepageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomepageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomepageFragment newInstance(String param1, String param2) {
        HomepageFragment fragment = new HomepageFragment();
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
        View view = inflater.inflate(R.layout.fragment_homepage, container, false);
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/Frontage-Regular.otf");


        TextView appName = (TextView) view.findViewById(R.id.illuminateHomepage);
        projectsButton = (Button) view.findViewById(R.id.projectsButton);
        howToButton = (Button) view.findViewById(R.id.howToButton);
        submissionsButton = (Button) view.findViewById(R.id.submissionsButton);
        aboutUsButton = (Button) view.findViewById(R.id.learnMoreButton);

        //set font
        appName.setTypeface(typeface);
        projectsButton.setTypeface(typeface);
        howToButton.setTypeface(typeface);
        submissionsButton.setTypeface(typeface);
        aboutUsButton.setTypeface(typeface);

        projectsButton.setOnClickListener(this);
        howToButton.setOnClickListener(this);
        submissionsButton.setOnClickListener(this);
        aboutUsButton.setOnClickListener(this);
        // Inflate the layout for this fragment
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onHomepageFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof HomepageFragmentInteractionListener) {
            mListener = (HomepageFragmentInteractionListener) context;
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
    public void onClick(View view) {
        MainActivity mainActivity = (MainActivity) getActivity();
        Log.d("onClick"," in Onclick with " + view.getId());
        switch(view.getId()){
            case R.id.projectsButton:
                mainActivity.loadProjects();
                break;
            case R.id.howToButton:
                mainActivity.loadHowTo();
                break;
            case R.id.submissionsButton:
                mainActivity.loadSubmissions();
                break;
            case R.id.learnMoreButton:
                mainActivity.loadAboutUs();
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
    public interface HomepageFragmentInteractionListener {
        // TODO: Update argument type and name
        void onHomepageFragmentInteraction(Uri uri);
    }
}
