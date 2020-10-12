package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toolbar;

import com.example.movieapp.adapter.BannerMoviePageAdapter;
import com.example.movieapp.adapter.MainRecyclerAdapter;
import com.example.movieapp.model.AllCategory;
import com.example.movieapp.model.BannerMovies;
import com.example.movieapp.model.CategoryItem;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    BannerMoviePageAdapter bannerMoviePageAdapter;
    TabLayout indicatorTab, categoryTab;
    ViewPager bannerMovieViewPager;
    List<BannerMovies> homeBannerList;
    List<BannerMovies> tvShowsBannerList;
    List<BannerMovies> kidsBannerList;
    List<BannerMovies> moviesBannerList;
    Timer sliderTimer;
    NestedScrollView nestedScrollView;
    AppBarLayout appBarLayout;

    MainRecyclerAdapter mainRecyclerAdapter;
    RecyclerView mainRecycler;
    List<AllCategory> allCategoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        indicatorTab = findViewById(R.id.tab_indicator);
        categoryTab = findViewById(R.id.tabLayout);
        nestedScrollView = findViewById(R.id.nested_scroll);
        appBarLayout = findViewById(R.id.appbar);

        homeBannerList = new ArrayList<>();
        homeBannerList.add(new BannerMovies(1,"Mondal","http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/banners/homebanner1.png",""));
        homeBannerList.add(new BannerMovies(2,"Little Women","http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/banners/homebanner2.png",""));
        homeBannerList.add(new BannerMovies(3,"Bhoot","http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/banners/homebanner3.png",""));
        homeBannerList.add(new BannerMovies(4,"Mirzapur","http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/banners/homebanner4.png",""));
        homeBannerList.add(new BannerMovies(5,"Pickachu","http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/banners/homebanner5.png",""));

        tvShowsBannerList = new ArrayList<>();
        tvShowsBannerList.add(new BannerMovies(1,"Skulls","http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/banners/tvshowbanner1.png",""));
        tvShowsBannerList.add(new BannerMovies(2,"Comicstaan","http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/banners/tvshowbanner2.png",""));
        tvShowsBannerList.add(new BannerMovies(3,"Upload","http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/banners/tvshowbanner3.png",""));
        tvShowsBannerList.add(new BannerMovies(4,"Hunters","http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/banners/tvshowbanner4.png",""));
        tvShowsBannerList.add(new BannerMovies(5,"Mondal","http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/banners/tvshowbanner5.png",""));

        moviesBannerList = new ArrayList<>();
        moviesBannerList.add(new BannerMovies(1,"Skulls","http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/banners/moviebanner1.png",""));
        moviesBannerList.add(new BannerMovies(2,"Skulls","http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/banners/moviebanner2.png",""));
        moviesBannerList.add(new BannerMovies(3,"Skulls","http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/banners/moviebanner3.png",""));
        moviesBannerList.add(new BannerMovies(4,"Skulls","http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/banners/moviebanner4.png",""));
        moviesBannerList.add(new BannerMovies(5,"Skulls","http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/banners/moviebanner5.png",""));

        kidsBannerList = new ArrayList<>();
        kidsBannerList.add(new BannerMovies(1,"Skulls","http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/banners/kidsbanner1.png",""));
        kidsBannerList.add(new BannerMovies(2,"Skulls","http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/banners/kidsbanner2.png",""));
        kidsBannerList.add(new BannerMovies(3,"Skulls","http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/banners/kidsbanner3.png",""));

        setBannerMoviesPageAdapter(homeBannerList);
        categoryTab.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 1 :
                        setScrollDefaultState();
                        setBannerMoviesPageAdapter(tvShowsBannerList);
                        return;
                    case 2:
                        setScrollDefaultState();
                        setBannerMoviesPageAdapter(moviesBannerList);
                        return;
                    case 3:
                        setScrollDefaultState();
                        setBannerMoviesPageAdapter(kidsBannerList);
                        return;
                    default:
                        setScrollDefaultState();
                        setBannerMoviesPageAdapter(homeBannerList);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        List<CategoryItem> homeCatListItem1 = new ArrayList<>();
        homeCatListItem1.add(new CategoryItem(1, "Pickachu", "http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/banners/kidsbanner4.png", ""));
        homeCatListItem1.add(new CategoryItem(2, "Picachu", "http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/banners/kidsbanner5.png", ""));
        homeCatListItem1.add(new CategoryItem(3, "Pickachu", "http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/banners/kidsbanner6.png", ""));
        homeCatListItem1.add(new CategoryItem(4, "Pckachu", "http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/banners/kidsbanner7.png", ""));
        homeCatListItem1.add(new CategoryItem(5, "Pickach", "http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/banners/kidsbanner8.png", ""));

        allCategoryList = new ArrayList<>();
        allCategoryList.add(new AllCategory(1, "Watch Next TV and Movies", homeCatListItem1));
        allCategoryList.add(new AllCategory(2,"Movies in Hindi", homeCatListItem1));
        allCategoryList.add(new AllCategory(3,"Kids and Family Movies", homeCatListItem1));
        allCategoryList.add(new AllCategory(4,"Amazon Original Series", homeCatListItem1));

        setMainRecycler(allCategoryList);
    }

    private void setBannerMoviesPageAdapter(List<BannerMovies> bannerMoviesList){
        bannerMovieViewPager = findViewById(R.id.banner_viewPager);
        bannerMoviePageAdapter = new BannerMoviePageAdapter( this, bannerMoviesList);
        bannerMovieViewPager.setAdapter(bannerMoviePageAdapter);
        indicatorTab.setupWithViewPager(bannerMovieViewPager);

        sliderTimer = new Timer();
        sliderTimer.scheduleAtFixedRate(new AutoSlider(), 5000,10000);
        indicatorTab.setupWithViewPager(bannerMovieViewPager, true);
    }

    class AutoSlider extends TimerTask {

        @Override
        public void run() {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (bannerMovieViewPager.getCurrentItem() < homeBannerList.size()-1) {
                        bannerMovieViewPager.setCurrentItem(bannerMovieViewPager.getCurrentItem() + 1);
                    } else {
                        bannerMovieViewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }
    public void setMainRecycler(List<AllCategory> allCategoryList){
        mainRecycler = findViewById(R.id.main_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mainRecycler.setLayoutManager(layoutManager);
        mainRecyclerAdapter = new MainRecyclerAdapter(this, allCategoryList);
        mainRecycler.setAdapter(mainRecyclerAdapter);
    }

    private void setScrollDefaultState(){
        nestedScrollView.fullScroll(View.FOCUS_UP);
        nestedScrollView.scrollTo(0,0);
        appBarLayout.setExpanded(true);
    }
}