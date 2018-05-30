package com.casinodivenezia.cvg;

import android.content.Context;
import android.net.Uri;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class EventiAdapter extends ArrayAdapter<EventItem> {

    private ViewHolder isSpeaking;
    private final Context mContext;
    private LayoutInflater mInflater;
    private List<EventItem> eventitemList = null;
    TextToSpeech t1;
    //Firebase
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://cmv-gioco.appspot.com/Events");

    public EventiAdapter(Context context, final int resource, List<EventItem> objects) {
        super(context,resource);
        if (context != null) {
            this.eventitemList = objects;
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.mContext = context;
        } else {
            this.mContext = null;
        }
    }

    class ViewHolder {
        public  TextToSpeech engine;

        public ImageView image;
        public TextView text;
        public TextView date;
        public ImageView speak;
        public TextView intro;
        public  TextView sede;



    }

    @Override
    public int getCount() {

        return eventitemList.size();
    }


    @Override
    public EventItem getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public class SpringRunnable implements Runnable{
        Spring _spring;
        View _view;
        SpringRunnable(Spring spring, View view){
            this._spring = spring;
            this._view = view;
        }
        @Override
        public void run() {
            _spring.setEndValue(1);

        }
    }
    public class ViewSpringListener implements SpringListener {

        View _view;

        ViewSpringListener(View view){
            this._view = view;
        }

        @Override
        public void onSpringUpdate(Spring spring) {
            render(_view,spring);
        }

        @Override
        public void onSpringAtRest(Spring spring) {
        }

        @Override
        public void onSpringActivate(Spring spring) {
        }

        @Override
        public void onSpringEndStateChange(Spring spring) {
        }
    }
    private void render(View v, Spring spring) {

        float value = (float)spring.getCurrentValue();
        float scale = 1f - (value * 0.2f);
        v.setScaleX(scale);
        v.setScaleY(scale);
    }
    private void speakOut(String text, ViewHolder view) {

        String utteranceId=this.hashCode() + "";
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, utteranceId);
        if (isSpeaking != null){
            if (view != isSpeaking) {
                if (Tab2.engine.isSpeaking()) {
                    Tab2.engine.stop();
                    isSpeaking.speak.setImageResource(R.drawable.speak);
                    Tab2.engine.speak(text, TextToSpeech.QUEUE_FLUSH, map);
                    isSpeaking = view;
                    view.speak.setImageResource(R.drawable.speak_f);
                } else{
                    Tab2.engine.speak(text, TextToSpeech.QUEUE_FLUSH, map);
                    view.speak.setImageResource(R.drawable.speak_f);
                    isSpeaking = view;
                }
            }else{
                if (Tab2.engine.isSpeaking()) {
                    Tab2.engine.stop();
                    view.speak.setImageResource(R.drawable.speak);
                } else{
                    Tab2.engine.speak(text, TextToSpeech.QUEUE_FLUSH, map);;
                    view.speak.setImageResource(R.drawable.speak_f);
                    isSpeaking = view;
                }
            }
        } else {
            if (Tab2.engine.isSpeaking()) {
                Tab2.engine.stop();
                view.speak.setImageResource(R.drawable.speak);
                isSpeaking= view;
            } else{
                Tab2.engine.speak(text, TextToSpeech.QUEUE_FLUSH, map);
                view.speak.setImageResource(R.drawable.speak_f);
                isSpeaking = view;
            }
        }

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;

        if (rowView == null) {

            rowView = mInflater.inflate(R.layout.eventilayout, null);
            //configure view holder
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.text = (TextView) rowView.findViewById(R.id.TITOLO);
            viewHolder.image = (ImageView) rowView.findViewById(R.id.imageViewEvento);
            viewHolder.speak = (ImageView) rowView.findViewById(R.id.speak);
            viewHolder.date = (TextView) rowView.findViewById(R.id.date);
            viewHolder.intro = (TextView) rowView.findViewById(R.id.INTRO);
            viewHolder.sede = (TextView) rowView.findViewById(R.id.sedeEvento);
            rowView.setTag(viewHolder);
        }

        final ViewHolder holder = (ViewHolder) rowView.getTag();
        holder.image.setImageResource(R.drawable.default_event);
        StorageReference imagesRef = storageRef.child(eventitemList.get(position).getImageMain());
        imagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(mContext).load(uri.toString()).placeholder(R.drawable.default_event).into(holder.image);
            }
        });

        holder.text.setText(eventitemList.get(position).getNameIT());
        holder.date.setText(formatMyDate(eventitemList.get(position).getStartDate()));
        holder.intro.setText(eventitemList.get(position).getDescription());

        if (eventitemList.get(position).getOffice() == "CN") {
            holder.sede.setText("Ca'Noghera");
        } else {
            holder.sede.setText("Ca'Vendramin Calergi");
        }

        //Speech
        holder.speak.setVisibility(View.GONE);
        final String textToSpeach = eventitemList.get(position).getMemo();
        if (textToSpeach != null) {
           holder.speak.setVisibility(View.VISIBLE);

        }
        holder.speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (textToSpeach != null)
                    speakOut(textToSpeach,holder);

            }
        });

        return rowView;
    }


    private String formatMyDate(String myDate) {
        DateFormat format = new SimpleDateFormat("dd/MM/yy");
        Date date = new Date();
        try {
            date = format.parse(myDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd LLL", MainActivity.currentLocale);

        return sdf.format(date);
    }
}
