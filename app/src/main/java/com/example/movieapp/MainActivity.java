package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.movieapp.adapter.BannerMoviePageAdapter;
import com.example.movieapp.model.BannerMovies;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        indicatorTab = findViewById(R.id.tab_indicator);
        categoryTab = findViewById(R.id.tabLayout);

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
                        setBannerMoviesPageAdapter(tvShowsBannerList);
                        return;
                    case 2:
                        setBannerMoviesPageAdapter(moviesBannerList);
                        return;
                    case 3:
                        setBannerMoviesPageAdapter(kidsBannerList);
                        return;
                    default:
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
    }

    private void setBannerMoviesPageAdapter(List<BannerMovies> bannerMoviesList){
        bannerMovieViewPager = findViewById(R.id.banner_viewPager);
        bannerMoviePageAdapter = new BannerMoviePageAdapter( this, bannerMoviesList);
        bannerMovieViewPager.setAdapter(bannerMoviePageAdapter);
        indicatorTab.setupWithViewPager(bannerMovieViewPager);

        Timer sliderTimer = new Timer();
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
}