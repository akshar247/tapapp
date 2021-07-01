package com.touchgirl.touchgirl.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.touchgirl.touchgirl.R;
import com.touchgirl.touchgirl.activity.MainActivity;
import com.touchgirl.touchgirl.config.Constants;
import com.touchgirl.touchgirl.model.PersonResponse;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PersonAdapter extends  RecyclerView.Adapter<PersonAdapter.MyViewHolder> {

    private Activity activity;

    List<PersonResponse.Data> dataEntity = new ArrayList<>();

    AdapterCallback adapterCallback;
    String def_lang;
    PersonResponse homeData;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_profile)
        ImageView iv_profile;
        @BindView(R.id.tv_person_name)
        TextView tv_person_name;
        @BindView(R.id.rl_add_survey_item)
        RelativeLayout rl_add_survey_item;
        @BindView(R.id.cv_main)
        CardView cv_main;


        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public PersonAdapter(Activity mContext, List<PersonResponse.Data> dataEntity, PersonResponse homeData, AdapterCallback adapterCallback1, String lang) {
        this.activity = mContext;
        this.dataEntity = dataEntity;
        this.adapterCallback = adapterCallback1;
        this.def_lang = lang;
        this.homeData = homeData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        try {

            final PersonResponse.Data dataEntitytemp = dataEntity.get(position);

            holder.tv_person_name.setText(dataEntitytemp.title + "");
            int roundvalu = (int) activity.getResources().getDimension(R.dimen.roundcorner);

            RequestOptions requestOptions = new RequestOptions()
                    .apply(RequestOptions.placeholderOf(R.drawable.no_image));
            Glide.with(activity)
                    .load(dataEntitytemp.image1)
                    .apply(requestOptions)
                    .into(holder.iv_profile);

            holder.cv_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });



        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return dataEntity.size();
    }

    public interface AdapterCallback {
        void onSubcategoryClick(PersonResponse.Data items);

        void onSubcategoryViewAllClick(PersonResponse.Data items);
    }
}