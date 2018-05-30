package com.casinodivenezia.cvg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class TabTornei extends Fragment {

    private static final String TAG = "TabTornei";
    private ListView listView;
    private TorneiAdapter mAdapter;
    private List<TournamentItem> pokeritemlist = null;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference eventsChild = mRootRef.child("Tournaments");

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loadEvent();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab4, container, false);
        listView = (ListView)rootView.findViewById(R.id.torneiList);
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
        intent.setClass(getActivity(), TournamentDetailsActivity.class);
        intent.putExtra("TournamentDescription", pokeritemlist.get(index).getTournamentDescription());
        intent.putExtra("imageMain", pokeritemlist.get(index).getImageTournament());
        intent.putExtra("TournamentName", pokeritemlist.get(index).getTournamentsName());
        intent.putExtra("TournamentURL", pokeritemlist.get(index).getTournamentUrl());
        intent.putExtra("StartDate", pokeritemlist.get(index).getStartDate());
        intent.putExtra("Office", pokeritemlist.get(index).getOffice());
        intent.putStringArrayListExtra("TournamentRules", pokeritemlist.get(index).getTournamentsRules());
        intent.putStringArrayListExtra("TournamentEvent", pokeritemlist.get(index).getTournamentEvent());
        intent.putExtra("Type", pokeritemlist.get(index).getType());
        startActivity(intent);
    }

    public void setOffice () {

        mAdapter = new TorneiAdapter(getActivity(), R.layout.torneilayout, pokeritemlist);
        listView.setAdapter(mAdapter);
    }

    public void loadEvent () {
        if(MainActivity.tournamentlistitem == null) {

            eventsChild.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    pokeritemlist = new ArrayList<TournamentItem>();
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        TournamentItem map = new TournamentItem();

                        map.setOffice(child.child("office").getValue(String.class));

                        map.setTournamentDescription(child.child("TournamentDescription").getValue(String.class));
                        map.setTournamentsName(child.child("TournamentName").getValue(String.class));
                        map.setTournamentsRules((ArrayList) child.child("TournamentsRules").getValue());
                        map.setTournamentUrl(child.child("TournamentURL").getValue(String.class));
                        map.setType(child.child("Type").getValue(String.class));
                        map.setTournamentEvent((ArrayList) child.child("TournamentEvent").getValue());
                        map.setStartDate(child.child("StartDate").getValue(String.class));
                        map.setEndDate(child.child("EndDate").getValue(String.class));
                        map.setImageTournament(child.child("ImageTournament").getValue(String.class));
                        pokeritemlist.add(map);
                    }
                    Collections.sort(pokeritemlist, new Comparator<Object>() {
                        @Override
                        public int compare(Object lhs, Object rhs) {
                            TournamentItem a = (TournamentItem) lhs;
                            TournamentItem b = (TournamentItem) rhs;
                            DateFormat format =new SimpleDateFormat("dd/MM/yy");

                            Date dateA = null;
                            try {
                                dateA = format.parse(a.getStartDate());
                            } catch (java.text.ParseException e) {
                                e.printStackTrace();
                            }
                            ;
                            Date dateB = null;
                            try {
                                dateB = format.parse(b.getStartDate());
                            } catch (java.text.ParseException e) {
                                e.printStackTrace();
                            }


                            if (dateA.after(dateB)) {
                                return -1;
                            } else {
                                return 1;
                            }

                        }
                    });
                    MainActivity.tournamentlistitem=pokeritemlist;
                    setOffice();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        } else {
            pokeritemlist=MainActivity.tournamentlistitem;
            setOffice();
        }
    }
}
