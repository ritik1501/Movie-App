package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toolbar;

import com.example.movieapp.adapter.BannerMoviePageAdapter;
import com.example.movieapp.adapter.MainRecyclerAdapter;
import com.example.movieapp.model.AllCategory;
import com.example.movieapp.model.BannerMovies;
import com.example.movieapp.model.CategoryItem;
import com.example.movieapp.retrofit.RetrofitClient;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

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
        tvShowsBannerList = new ArrayList<>();
        moviesBannerList = new ArrayList<>();
        kidsBannerList = new ArrayList<>();

        getBannerData();

        getAllMoviesData(1);

        categoryTab.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 1 :
                        setScrollDefaultState();
                        setBannerMoviesPageAdapter(tvShowsBannerList);
                        getAllMoviesData(2);
                        return;
                    case 2:
                        setScrollDefaultState();
                        setBannerMoviesPageAdapter(moviesBannerList);
                        getAllMoviesData(3);
                        return;
                    case 3:
                        setScrollDefaultState();
                        setBannerMoviesPageAdapter(kidsBannerList);
                        getAllMoviesData(4);
                        return;
                    default:
                        setScrollDefaultState();
                        setBannerMoviesPageAdapter(homeBannerList);
                        getAllMoviesData(1);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });

        allCategoryList = new ArrayList<>();

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

    private void getBannerData(){

        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(RetrofitClient.getRetrofitClient().getAllBanners()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<BannerMovies>>() {
                    @Override
                    public void onNext(List<BannerMovies> bannerMovies) {
                        for(int i=0; i<bannerMovies.size(); i++){
                            if(bannerMovies.get(i).getBannerCategoryId().toString().equals("1")){
                                homeBannerList.add(bannerMovies.get(i));
                            }
                            else if(bannerMovies.get(i).getBannerCategoryId().toString().equals("2")){
                                tvShowsBannerList.add(bannerMovies.get(i));
                            }
                            else if(bannerMovies.get(i).getBannerCategoryId().toString().equals("3")){
                                moviesBannerList.add(bannerMovies.get(i));
                            }
                            else if(bannerMovies.get(i).getBannerCategoryId().toString().equals("4")){
                                kidsBannerList.add(bannerMovies.get(i));
                            }
                            else{

                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("bannerData",""+e);
                    }

                    @Override
                    public void onComplete() {
                        setBannerMoviesPageAdapter(homeBannerList);
                    }
                }));
    }

    private void getAllMoviesData(int categoryId){

        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(RetrofitClient.getRetrofitClient().getAllCategoryMovies(categoryId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<AllCategory>>() {
                    @Override
                    public void onNext(List<AllCategory> allCategoryList) {
                        setMainRecycler(allCategoryList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("bannerData",""+e);
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }
}