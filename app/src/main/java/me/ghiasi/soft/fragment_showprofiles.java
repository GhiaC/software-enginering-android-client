package me.ghiasi.soft;

import me.ghiasi.soft.tools.*;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.endless.Endless;

import java.util.ArrayList;
import java.util.List;

import me.ghiasi.soft.tools.DataProvider;
import me.ghiasi.soft.tools.GifImageView;
import me.ghiasi.soft.tools.MyRecyclerProfileAdapter;
import me.ghiasi.soft.tools.ParallaxRecyclerAdapter;
import it.gmariotti.recyclerview.adapter.AlphaAnimatorAdapter;
import it.gmariotti.recyclerview.itemanimator.SlideInOutLeftItemAnimator;


public class fragment_showprofiles extends Fragment implements MyRecyclerProfileAdapter.ItemClickListener {

    ViewGroup view;
    RecyclerView myRecycler;
    ParallaxRecyclerAdapter<Person> stringAdapter;
    Endless endless;
    int mode;
    String modeStr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = container;
        return inflater.inflate(R.layout.fragment_showprofiles, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle extra = getArguments();
        if (extra != null) {
            mode = extra.getInt("mode");
            modeStr = extra.getString("modeStr");
            ((TextView) view.findViewById(R.id.TitleMode)).setText(modeStr);
        }
        myRecycler = (RecyclerView) view.findViewById(R.id.recyclerview);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        myRecycler.setLayoutManager(manager);
        myRecycler.setHasFixedSize(true);

        final List<Person> content = new ArrayList<>();

        stringAdapter = ParallaxRecyclerAdapter(content);
        stringAdapter.setParallaxHeader(getActivity().getLayoutInflater().inflate(R.layout.header, myRecycler, false), myRecycler);
        stringAdapter.setOnParallaxScroll(new ParallaxRecyclerAdapter.OnParallaxScroll() {
            @Override
            public void onParallaxScroll(float percentage, float offset, View parallax) {
                ImageView c = (ImageView) parallax.findViewById(R.id.imageView2);
                c.setAlpha(1 - percentage);
            }
        });
        stringAdapter.setOnClickEvent(new ParallaxRecyclerAdapter.OnClickEvent() {
            @Override
            public void onClick(View v, int position) {
//                Toast.makeText(getActivity(),position,Toast.LENGTH_LONG);
            }
        });

        //set Animation
        final AlphaAnimatorAdapter animatorAdapter = new AlphaAnimatorAdapter(stringAdapter, myRecycler);
        myRecycler.setItemAnimator(new SlideInOutLeftItemAnimator(myRecycler));
        myRecycler.setAdapter(animatorAdapter);

        // Endless
        View loadingView = View.inflate(getActivity(), R.layout.loading, null);
        loadingView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        GifImageView gifView = (GifImageView) loadingView.findViewById(R.id.loading);
        gifView.setGifImageResource(R.drawable.loading);

        endless = Endless.applyTo(myRecycler, loadingView);
        endless.setLoadMoreListener(new Endless.LoadMoreListener() {
            @Override
            public void onLoadMore(int page) {
                loadData(mode, page);
            }
        });
        loadData(mode, 1);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (asyncTask != null) {
            asyncTask.cancel(true);
        }
    }

    private AsyncTask asyncTask;

    private void loadData(final int mode, final int page) {
//        final int lastId = stringAdapter.getLastId();
        asyncTask = new AsyncTask<String, String, List<Person>>() {
            private List<Person> data;

            @Override
            protected List<Person> doInBackground(String[] strings) {
                if (page != 1) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return DataProvider.request(mode, page);
            }

            @Override
            protected void onPostExecute(List<Person> integers) {
                data = integers;
//                if (page == 0) {
//                    stringAdapter.addData(data);
//                } else {
                stringAdapter.addData(data);
                endless.loadMoreComplete();
//                }
                super.onPostExecute(integers);
            }
        }.execute();
    }


    private ParallaxRecyclerAdapter<Person> ParallaxRecyclerAdapter(final List<Person> content) {
        ParallaxRecyclerAdapter<Person> Adapter = new ParallaxRecyclerAdapter<Person>(content) {
            private LayoutInflater mInflater;
            private CircularImageView circularImageView;
            private ProgressBar progressBar;

            class loadProfPic implements LoadImageTask.Listener {
                @Override
                public void onImageLoaded(Bitmap bitmap) {
//                    if (bitmap != null) {
                    circularImageView.setImageBitmap(bitmap);
                    progressBar.setVisibility(View.INVISIBLE); // hide the ProgressBar
//                    }
                }

                @Override
                public void onError() {
                    Toast.makeText(getContext(), R.string.errLoadPic, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onBindViewHolderImpl(RecyclerView.ViewHolder viewHolder, ParallaxRecyclerAdapter<Person> adapter, int i) {
                viewHolder.itemView.setOnClickListener(new showPersonDetails(content.get(i)));
                ((TextView) viewHolder.itemView.findViewById(R.id.ProfName)).setText(content.get(i).getName());
                ((RatingBar) viewHolder.itemView.findViewById(R.id.ratingBar)).setNumStars(content.get(i).getVote());
                circularImageView = ((CircularImageView) viewHolder.itemView.findViewById(R.id.ProfilePic_showProfiles_ImageView));
                progressBar = ((ProgressBar) viewHolder.itemView.findViewById(R.id.progressBar1));
                new LoadImageTask(new loadProfPic()).execute(content.get(i).getProfileImg());
            }

            @Override
            public int getItemCountImpl(ParallaxRecyclerAdapter<Person> adapter) {
                return content.size();
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolderImpl(ViewGroup viewGroup, ParallaxRecyclerAdapter<Person> adapter, int i) {
                this.mInflater = LayoutInflater.from(getActivity());
                View view = mInflater.inflate(R.layout.recyclerview_profile_row, viewGroup, false);
                ParallaxRecyclerAdapter.ViewHolder viewHolder = new ParallaxRecyclerAdapter.ViewHolder(view);
                return viewHolder;
            }
        };
        return Adapter;
    }

    class showPersonDetails implements View.OnClickListener {
        Person person;

        public showPersonDetails(Person person) {
            this.person = person;
        }

        @Override
        public void onClick(View v) {
            ((MainActivity) getActivity()).showProfile(person);
        }
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myRecycler = (RecyclerView) view.findViewById(R.id.recyclerview);
    }


    MyRecyclerProfileAdapter adapter;

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(getActivity(), "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }


}