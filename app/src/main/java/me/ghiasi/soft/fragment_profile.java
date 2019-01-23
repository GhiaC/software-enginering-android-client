package me.ghiasi.soft;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.util.HashMap;

import me.ghiasi.soft.tools.GifImageView;
import me.ghiasi.soft.tools.LoadImageTask;

public class fragment_profile extends Fragment
        implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener , LoadImageTask.Listener  {

    ImageView ProfilePic = null;
    TextView NameProfile = null, UserCategory = null, Decription = null, PhoneNume_TextView = null;//Description = sabeghe kar
    TextClock textClock1 , textClock2;
    RatingBar UserRate = null;
    String phoneNumber;
    SliderLayout mDemoSlider;

    int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    int id = -1;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_profile,
                container, false);

        ProfilePic = (ImageView) view.findViewById(R.id.ProfilePic_ImageView);
        NameProfile = (TextView) view.findViewById(R.id.NameProfile_TextView);
        UserRate = (RatingBar) view.findViewById(R.id.UserRate_RatingBar);
        UserCategory = (TextView) view.findViewById(R.id.UserCategory_TextView);
        Decription = (TextView) view.findViewById(R.id.Descrption_TextView);
        PhoneNume_TextView = (TextView) view.findViewById(R.id.PhoneNume_TextView);
        textClock1 = (TextClock) view.findViewById(R.id.textClock1);
        textClock2 = (TextClock) view.findViewById(R.id.textClock2);
        view.findViewById(R.id.call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call();
            }
        });
        Bundle extra = getArguments();



//[[1,0,"Masoud",null,null,"00:00:00","00:00:00",null,"09360840616",0,0,0]]
//`SPid`,`Cid`,`SPname`,`SPprofileImg`,`SPpictures`,`SPstartWorkTime`,`SPendWorkTime`,`SPdiscreption`,`SPphoneNumber`,`SPvote`,`SPbusy`,`SPstatus`

        if (extra != null) {
            int SPid = extra.getInt("SPid");
            id = SPid;
            int Cid = extra.getInt("Cid");
            String SPname = extra.getString("SPname");

            String SPprofileImg = extra.getString("SPprofileImg");

            String SPdiscreption = extra.getString("SPdiscreption");
            String SPpictures = extra.getString("SPpictures");
            String SPstartWorkTime = extra.getString("SPstartWorkTime");
            String SPendWorkTime = extra.getString("SPendWorkTime");
            String SPphoneNumber = extra.getString("SPphoneNumber");
            phoneNumber = SPphoneNumber;
            int SPvote = extra.getInt("SPvote");

            int SPbusy = extra.getInt("SPbusy");
            int SPstatus = extra.getInt("SPstatus");

            setNameProfile(SPname);
            setProfilePic(SPprofileImg);
            setWorkTime(SPstartWorkTime,SPendWorkTime);
            setUserRate(SPvote);
            setUserCategory(Cid);
            setDescrption(SPdiscreption);
            initGallery(SPpictures);
            setPhoneNumber(SPphoneNumber);
        }
        return view;
    }

    private void setWorkTime(String from , String to){
        textClock1.setFormat24Hour(from);
        textClock2.setFormat24Hour(to);
    }
    private void setProfilePic(String src) {
        try {
            new LoadImageTask(this).execute(src);
            Toast.makeText(view.getContext(),"Start Loading pic", Toast.LENGTH_SHORT).show();
            ((GifImageView)view.findViewById(R.id.loadingProfilePic)).setGifImageResource(R.drawable.loading);
//            URL url = new URL("https://poolsaz.biz/SP/132.JPG");
//            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//            ProfilePic.setImageBitmap(bmp);
//            ProfilePic.setImageDrawable(InputFileReader.LoadImageFromWebOperations("https://poolsaz.biz/SP/132.JPG"));
        }catch (Exception e){
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }

    private void setNameProfile(String nameProfile) {
        NameProfile.setText(nameProfile);
    }

    private void setUserRate(int SPvote) {
        UserRate.setRating(SPvote);
        UserRate.setNumStars(5);
    }

    private void setUserCategory(int Cid) {
        UserCategory.setText("chah baz kon :/" + Cid);
    }

    private void setDescrption(String discreptoin) {
        Decription.setText(discreptoin);
    }

    private void initGallery(String picAddr) {
        mDemoSlider = (SliderLayout) view.findViewById(R.id.custom_indicator);

        HashMap<String, String> url_maps = new HashMap<String, String>();
        url_maps.put("Hannibal", picAddr);

//        HashMap<String, Integer> file_maps = new HashMap<String, Integer>();
//        file_maps.put("Hannibal", R.drawable.t1);


        for (String name : url_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(view.getContext());
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Foreground2Background);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);
    }

    @Override
    public void onImageLoaded(Bitmap bitmap) {
        ProfilePic.setImageBitmap(bitmap);
    }

    @Override
    public void onError() {
        Toast.makeText(view.getContext(), "Error Loading Image !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(view.getContext(), slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
//        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNume_TextView.setText(phoneNumber);
    }

    public void call() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+phoneNumber));
        startActivity(intent);
    }

}
