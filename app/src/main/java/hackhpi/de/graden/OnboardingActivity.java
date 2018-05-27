package hackhpi.de.graden;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class OnboardingActivity extends AppCompatActivity implements ViewPager.PageTransformer {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private ImageButton mNextBtn;
    private Button mFinishBtn;
    private ImageView[] indicators;
    private int page = 0;   //  to track page position
    private ImageView backgroundImage, colawnyIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.black_trans80));

        setContentView(R.layout.activity_onboarding);

        backgroundImage = (ImageView) findViewById(R.id.background_logo);
        colawnyIcon = (ImageView) findViewById(R.id.colawny_icon);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mNextBtn = (ImageButton) findViewById(R.id.intro_btn_next);

        Button mSkipBtn = (Button) findViewById(R.id.intro_btn_skip);
        mFinishBtn = (Button) findViewById(R.id.intro_btn_finish);

        ImageView zero = (ImageView) findViewById(R.id.intro_indicator_0);
        ImageView one = (ImageView) findViewById(R.id.intro_indicator_1);
        ImageView two = (ImageView) findViewById(R.id.intro_indicator_2);

        indicators = new ImageView[]{zero, one, two};

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setPageTransformer(false, this);

        mViewPager.setCurrentItem(page);
        updateIndicators(page);

        final int color1 = ContextCompat.getColor(this, R.color.lime);
        final int color2 = ContextCompat.getColor(this, R.color.light_green);
        final int color3 = ContextCompat.getColor(this, R.color.green);

        final int[] colorList = new int[]{color1, color2, color3};

        final ArgbEvaluator evaluator = new ArgbEvaluator();

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int colorUpdate = (Integer) evaluator.evaluate(positionOffset, colorList[position], colorList[position == 2 ? position : position + 1]);
                mViewPager.setBackgroundColor(colorUpdate);

            }

            @Override
            public void onPageSelected(int position) {

                page = position;

                updateIndicators(page);

                switch (position) {
                    case 0:
                        mViewPager.setBackgroundColor(color1);
                        break;
                    case 1:
                        mViewPager.setBackgroundColor(color2);
                        break;
                    case 2:
                        mViewPager.setBackgroundColor(color3);
                        break;
                }

                mNextBtn.setVisibility(position == 2 ? View.GONE : View.VISIBLE);
                mFinishBtn.setVisibility(position == 2 ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page += 1;
                mViewPager.setCurrentItem(page, true);
            }
        });

        mSkipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMain();
            }
        });

        mFinishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMain();
            }
        });
    }

    public void startMain() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    void updateIndicators(int position) {
        for (int i = 0; i < indicators.length; i++) {
            indicators[i].setBackgroundResource(
                    i == position ? R.drawable.indicator_selected : R.drawable.indicator_unselected
            );
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pager, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();
        int fragmentPosition = (int) view.getTag();

//        if (fragmentPosition == 0) {
//            if (position < -1) { // [-Infinity,-1)
//                // This page is way off-screen to the left.
//                colawnyIcon.setAlpha(1f);
//
//            } else if (position <= 1) { // [-1,1]
//
////                colawnyIcon.setAlpha((float) (-(1 - position) * 0.5 * pageWidth));
//                colawnyIcon.setAlpha(position);
//
//            } else { // (1,+Infinity]
//                // This page is way off-screen to the right.
//                colawnyIcon.setAlpha(0f);
//            }
//        }

        if (fragmentPosition == 1) {
            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setRotation(0);

            } else if (position <= 1) { // [-1,1]

                backgroundImage.setRotation((float) (-(1 - position) * 0.1 * pageWidth));

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setRotation(0);
            }
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        ImageView img;

        int[] bgs = new int[]{R.drawable.ic_account_circle, R.drawable.ic_message, R.drawable.ic_nature};

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = null;

            switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
                case 1:
                    rootView = inflater.inflate(R.layout.fragment_pager_1, container, false);
                    rootView.setTag(getArguments().getInt(ARG_SECTION_NUMBER) - 1);

                    break;
                case 2:
                    rootView = inflater.inflate(R.layout.fragment_pager_2, container, false);
                    rootView.setTag(getArguments().getInt(ARG_SECTION_NUMBER) - 1);
                    break;
                case 3:
                    rootView = inflater.inflate(R.layout.fragment_pager_3, container, false);
                    rootView.setTag(getArguments().getInt(ARG_SECTION_NUMBER) - 1);

                    Button button1 = rootView.findViewById(R.id.button1);
                    setRoundedDrawable(getContext(), button1, R.color.green, R.color.light_blue);
                    Button button2 = rootView.findViewById(R.id.button2);
                    setRoundedDrawable(getContext(), button2, R.color.green, R.color.light_blue);

                    button1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getContext(), MainActivity.class);
                            startActivity(intent);
                        }
                    });

                    button2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getContext(), MainActivity.class);
                            startActivity(intent);
                        }
                    });


                    break;
                default:
                    throw new IllegalStateException();
            }

            return rootView;
        }
    }

    public static void setRoundedDrawable(Context context, View view, int backgroundColor, int borderColor) {
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setCornerRadius(20f);
        shape.setColor(backgroundColor);
        if (borderColor != 0){
            shape.setStroke(2, borderColor);
        }
        view.setBackgroundDrawable(shape);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }
}
