package com.casinodivenezia.cvg;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Tab2 extends Fragment implements TextToSpeech.OnInitListener{

    private static final String TAG = "Tab2Fragment";
    private EventiAdapter mAdapter;
    private List<EventItem> eventitemlist = null;
    private ListView listView;
    public static TextToSpeech engine;

    // [START declare_database_ref]
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference eventsChild = mRootRef.child("Events");

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loadEvent();

    }
    @Override
    public void onInit(int status) {
        Log.d("Speech", "OnInit - Status [" + status + "]");

        if (status == TextToSpeech.SUCCESS) {
            Log.d("Speech", "Success!");
            engine.setLanguage(MainActivity.currentLocale);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2, container, false);
        listView = (ListView)rootView.findViewById(R.id.eventiList);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                showDetails(position);
            }
        });

        return rootView;
    }

    void showDetails(int index) {

        Intent intent = new Intent();
        intent.setClass(getActivity(), EventDetailsActivity.class);
        intent.putExtra("name", eventitemlist.get(index).getName());
        intent.putExtra("description", eventitemlist.get(index).getDescription());
        intent.putExtra("date", eventitemlist.get(index).getStartDate());
        intent.putExtra("objectId", eventitemlist.get(index).getMyId());
        intent.putExtra("image1", eventitemlist.get(index).getImage1());
        intent.putExtra("image2", eventitemlist.get(index).getImage2());
        intent.putExtra("image3", eventitemlist.get(index).getImage3());
        intent.putExtra("imageMain", eventitemlist.get(index).getImageMain());
        intent.putExtra("Office", eventitemlist.get(index).getOffice());
        startActivity(intent);
    }

    public void setOffice () {
        if (Tab2.engine != null) {
            Tab2.engine.stop();
        }
        mAdapter = new EventiAdapter(getActivity(), R.layout.eventilayout, eventitemlist);
        listView.setAdapter(mAdapter);
    }
    public void loadEvent () {
        if(MainActivity.eventitemlist == null) {
            eventitemlist = new ArrayList<EventItem>();


            eventsChild.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.


                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        EventItem map = new EventItem();
                        map.setImageMain(child.child("ImageName").getValue(String.class));
                        map.setImage1(child.child("ImageEvent1").getValue(String.class));
                        map.setImage2(child.child("ImageEvent2").getValue(String.class));
                        map.setImage3(child.child("ImageEvent3").getValue(String.class));
                        map.setOffice(child.child("office").getValue(String.class));
                        map.setIsSlotEvent(child.child("isSlotEvents").getValue(Boolean.class));
                        //Need for SlotDetailsActivity
                        map.setDescriptionIT(child.child("DescriptionIT").getValue(String.class));
                        map.setNameIT(child.child("NameIT").getValue(String.class));
                        //    map.setMyId((String) event.getObjectId());

                        switch (Locale.getDefault().getLanguage()) {
                            case "it":
                                map.setDescription(child.child("DescriptionIT").getValue(String.class));
                                map.setName(child.child("NameIT").getValue(String.class));
                                map.setMemo(child.child("memoIT").getValue(String.class));
                                break;
                            case "es":
                                map.setDescription(child.child("DescriptionES").getValue(String.class));
                                map.setName(child.child("NameES").getValue(String.class));
                                map.setMemo(child.child("memoES").getValue(String.class));
                                break;
                            case "fr":
                                map.setDescription(child.child("DescriptionFR").getValue(String.class));
                                map.setName(child.child("NameFR").getValue(String.class));
                                map.setMemo(child.child("memoFR").getValue(String.class));
                                break;
                            case "de":
                                map.setDescription(child.child("DescriptionDE").getValue(String.class));
                                map.setName(child.child("NameDE").getValue(String.class));
                                map.setMemo(child.child("memoDE").getValue(String.class));
                                break;
                            case "ru":
                                map.setDescription(child.child("DescriptionRU").getValue(String.class));
                                map.setName(child.child("NameRU").getValue(String.class));
                                map.setMemo(child.child("memoRU").getValue(String.class));
                                break;
                            case "zh":
                                map.setDescription(child.child("DescriptionZH").getValue(String.class));
                                map.setName(child.child("NameZH").getValue(String.class));
                                map.setMemo(child.child("memoZH").getValue(String.class));
                                break;
                            default:
                                map.setDescription(child.child("Description").getValue(String.class));
                                map.setName(child.child("Name").getValue(String.class));
                                map.setMemo(child.child("memo").getValue(String.class));
                                break;
                        }
                        map.setStartDate(child.child("StartDate").getValue(String.class));
                        map.setEndDate(child.child("EndDate").getValue(String.class));

                        eventitemlist.add(map);
                    }
                    Collections.sort(eventitemlist, new Comparator<Object>() {
                        @Override
                        public int compare(Object lhs, Object rhs) {
                            EventItem a = (EventItem) lhs;
                            EventItem b = (EventItem) rhs;
                            DateFormat format =new SimpleDateFormat("dd/MM/yy");

                            Date dateA = null;
                            try {
                                dateA = format.parse(a.getStartDate());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            ;
                            Date dateB = null;
                            try {
                                dateB = format.parse(b.getStartDate());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }


                            if (dateA.after(dateB)) {
                                return -1;
                            } else {
                                return 1;
                            }

                        }
                    });
                    MainActivity.eventitemlist = eventitemlist;
                    if (isAdded()) {
                        setOffice();
                    } else {
                        Log.d(TAG, "EventsFr not added");
                    }

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.d(TAG, "Failed to read value.", error.toException());
                }
            });

        } else {
            eventitemlist = MainActivity.eventitemlist;
            if (isAdded()) {
                setOffice();
            } else {
                Log.d(TAG, "EventsFr not added");

            }
        }
    }
    @Override
    public void onStop() {
        super.onStop();
        engine.shutdown();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mAdapter != null)
            mAdapter.notifyDataSetChanged();
        engine = new TextToSpeech(getContext(), this);
        engine.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String utteranceId) {

            }

            @Override
            public void onDone(String utteranceId) {
                Log.d("aa","bb");
//                if (isSpeaking != null) {
//                    isSpeaking.speak.setImageResource(R.drawable.speak);
//                }
            }

            @Override
            public void onError(String utteranceId) {

            }
        });
    }
}
