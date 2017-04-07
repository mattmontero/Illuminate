package sjsu.illuminate.illuminate.Activities;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import sjsu.illuminate.illuminate.Fragments.LearnMoreFragment;
import sjsu.illuminate.illuminate.Fragments.HomepageFragment;
import sjsu.illuminate.illuminate.Fragments.HowToFragment;
import sjsu.illuminate.illuminate.Fragments.MainFragment;
import sjsu.illuminate.illuminate.Fragments.ProjectsFragment;
import sjsu.illuminate.illuminate.Fragments.SubmissionsFragment;
import sjsu.illuminate.illuminate.R;

public class MainActivity extends AppCompatActivity
        implements MainFragment.MainFragmentInteractionListener, HomepageFragment.HomepageFragmentInteractionListener,
        ProjectsFragment.ProjectsFragmentInteractionListener, LearnMoreFragment.LearnMoreFragmentInteractionListener,
        SubmissionsFragment.SubmissionsFragmentInteractionListener, HowToFragment.HowToFragmentInteractionListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragment_container);

        if(fragment == null){
            fragment = new MainFragment();
            manager.beginTransaction().add(R.id.fragment_container,fragment).commit();
        }
    }

    public void loadHomepage()
    {
        HomepageFragment homepageFragment = new HomepageFragment();
        this.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homepageFragment).addToBackStack(null).commit();
    }

    //Loads Project Page
    public void loadProjects(){
        ProjectsFragment projectsFragment = new ProjectsFragment();
        this.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, projectsFragment).addToBackStack(null).commit();
    }

    //Loads How-To Page
    public void loadHowTo(){
        HowToFragment howToFragment = new HowToFragment();
        this.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, howToFragment).addToBackStack(null).commit();
    }

    //Loads Submissions Page
    public void loadSubmissions(){
        SubmissionsFragment submissionsFragment = new SubmissionsFragment();
        this.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, submissionsFragment).addToBackStack(null).commit();
    }

    //Loads SubmissionPage
    public void loadAboutUs(){
        LearnMoreFragment aboutUsFragment = new LearnMoreFragment();
        this.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, aboutUsFragment).addToBackStack(null).commit();
    }

    @Override
    public void onMainFragmentInteraction(Uri uri) {

    }

    @Override
    public void onHomepageFragmentInteraction(Uri uri) {

    }

    @Override
    public void onProjectsFragmentInteraction(Uri uri) {

    }

    @Override
    public void onLearnMoreFragmentInteraction(Uri uri) {

    }

    @Override
    public void onHowToFragmentInteraction(Uri uri) {

    }

    @Override
    public void onSubmissionsFragmentInteraction(Uri uri) {

    }
}
