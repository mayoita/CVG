package com.casinodivenezia.cvg;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

public class TabGame extends Fragment   {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "title";
    private ListView listView;
    private GamesAdapter mAdapter;
    private int level = 0;
    private int levelTwo = 0;
    private TabGame me;
    private static final String TAG = "GAME_LIST";
    int height;
    GameDetailsFragment detailsG;

    private JSONObject gameList;
    public int currentGamePosition;
    public int currentGamePositionLevel2;
    JSONArray myjArr;


    String [] demoData = {"a", "b", "c", "d", "e","f", "g", "h", "i", "l"};

    private int[] textureArray = {
            R.drawable.tabletableview,
            R.drawable.slotstableview,
            R.drawable.onlinetableview
    };
    private int[] titleArray = {
            R.string.table_games,
            R.string.slot_games,
            R.string.online_games
    };

    private int[] arrayGames = {
            R.drawable.fair,
            R.drawable.blackj,
            R.drawable.texas,
            R.drawable.banco,
            R.drawable.carribean,
            R.drawable.french,
            R.drawable.chemin,
            R.drawable.trente
    };
    private int[] arrayGamesCN = {
            R.drawable.fair,
            R.drawable.blackj,
            R.drawable.texas,
            R.drawable.banco,
            R.drawable.carribean,
            R.drawable.french,
            R.drawable.chemin
    };

    private int[] arraySlots = {
            R.drawable.slotoffer,
            R.drawable.slotnew,
            R.drawable.ourjack,
            R.drawable.slotroom


    };
    private int[] arrayGamesTitle = {
            R.string.fair,
            R.string.black,
            R.string.texas,
            R.string.punto,
            R.string.carribean,
            R.string.francese,
            R.string.chemin,
            R.string.trente
    };
    private int[] arrayGamesTitleCN = {
            R.string.fair,
            R.string.black,
            R.string.texas,
            R.string.punto,
            R.string.carribean,
            R.string.francese,
            R.string.chemin
    };
    private int[] arraySlotsTitle = {
            R.string.what,
            R.string.whatnew,
            R.string.jackpot_slot,
            R.string.rooms

    };

    boolean mDualPane;
    int mCurCheckPosition = 0;


    private String mTitle;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * Test 2
     * @param param1 Parameter 1.

     * @return A new instance of fragment TabGame.
     */



    public static TabGame newInstance(String param1) {
        TabGame fragment = new TabGame();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);

        fragment.setArguments(args);
        return fragment;
    }

    public TabGame() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(ARG_PARAM1);

        }
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.menu_main, menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        me = this;
        try {
            gameList = new JSONObject(loadJSONFromAsset());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        View rootView = inflater.inflate(R.layout.fragment_casino_game, container, false);
        listView = (ListView) rootView.findViewById(R.id.list_games);


        Display display = getActivity().getWindowManager().getDefaultDisplay();

        int width = display.getWidth();
        height = (int) (width * 0.45);


        try {
            myjArr = gameList.getJSONArray("Tavoli").getJSONArray(0).getJSONArray(2);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mAdapter = new GamesAdapter(getActivity(), height, arrayGames, arrayGamesTitle);
        listView.setAdapter(mAdapter);
        level=0;


        if (savedInstanceState != null) {
            // Restore last state for checked position.
            mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
        }



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //final String titoloriga = (String)parent.getItemAtPosition(position);
                //Log.d("list", "Ho cliccato sull'elemento con il titolo " + titoloriga);
                currentGamePosition = position;

                            showDetails(position, 0);


            }
        });

        return rootView;
    }




    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnGameInteractionListener {

        public void onFragmentInteraction(Uri uri);
    }
    public void openPop(View v) {
        detailsG.openPopWindow(v);
    }

    void showDetails(int index, int levelGame) {
        mCurCheckPosition = index;

            // Otherwise we need to launch a new activity to display
            // the dialog fragment with selected text.
            switch (levelGame) {
                case 0:
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), CasinoGame_Item_Activity.class);
                    try {
                        intent.putExtra("jsonArray", myjArr.getJSONArray(index).toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    startActivity(intent);
                    break;
                case 1:

                    break;
                default:
                    break;
            }




    }

    public String loadJSONFromAsset() {
        String json = null;
        InputStream is;

        try {
            if (Locale.getDefault().getLanguage().equals("it") ) {
                is = getActivity().getAssets().open("base/GameDBase_it.json");
            } else {
                is = getActivity().getAssets().open("base/GameDBase.json");
            }


            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }
    public void setOffice () throws JSONException {
        if (level == 1) {
            if (Venue.currentVenue == 0) {
                switch (currentGamePositionLevel2) {
                    case 0:
                        mAdapter.textureArray = arrayGames;
                        mAdapter.titleArray = arrayGamesTitle;
                        mAdapter.notifyDataSetChanged();

                        try {
                            myjArr = gameList.getJSONArray("Tavoli").getJSONArray(0).getJSONArray(2);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 1:
                        mAdapter.textureArray = arraySlots;
                        mAdapter.titleArray = arraySlotsTitle;
                        mAdapter.notifyDataSetChanged();

//                        try {
//                            myjArr = gameList.getJSONArray("Slot").getJSONArray(0).getJSONArray(Venue.currentVenue + 2);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
                        break;
                }



            } else {
                switch (currentGamePositionLevel2) {
                    case 0:
                        mAdapter.textureArray = arrayGamesCN;
                        mAdapter.titleArray = arrayGamesTitleCN;
                        mAdapter.notifyDataSetChanged();

                        try {
                            myjArr = gameList.getJSONArray("Tavoli").getJSONArray(0).getJSONArray(2);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 1:
                        mAdapter.textureArray = arraySlots;
                        mAdapter.titleArray = arraySlotsTitle;
                        mAdapter.notifyDataSetChanged();

                        try {
                            myjArr = gameList.getJSONArray("Slot").getJSONArray(0).getJSONArray(Venue.currentVenue + 2);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                }

            }
        }
    }



}
