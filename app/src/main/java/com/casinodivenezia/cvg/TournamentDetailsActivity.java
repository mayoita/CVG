package com.casinodivenezia.cvg;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TournamentDetailsActivity extends Activity {

    private String imageMain;
    private Context myContext;
    String bodyString;
    String bodyTitolo;
    String office;

    private ImageButton condividi;
    private ImageButton map;
    FirebaseStorage storage = FirebaseStorage.getInstance();



    StorageReference storageRef = storage.getReferenceFromUrl("gs://cmv-gioco.appspot.com/Tournaments");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        myContext = this;


        setContentView(R.layout.activity_tournament_details);

        TextView name = (TextView) findViewById(R.id.textView8);
        TextView date = (TextView) findViewById(R.id.textView9);
        final TextView description = (TextView) findViewById(R.id.textView10);
        final ImageView immagine = (ImageView)findViewById(R.id.imageView7);
        name.setText(i.getStringExtra("TournamentName"));
        date.setText(i.getStringExtra("date"));
        description.setText(i.getStringExtra("TournamentDescription"));
        bodyString = i.getStringExtra("TournamentDescription");
        bodyTitolo = i.getStringExtra("TournamentName");
        office = i.getStringExtra("Office");

        Typeface XLight = Typeface.createFromAsset(getAssets(), "fonts/gothamxlight.otf");

        date.setTypeface(XLight);
        description.setTypeface(XLight);

        imageMain = (i.getStringExtra("imageMain"));
        if (imageMain != null){
            StorageReference imagesRef = storageRef.child(imageMain);
            imagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.with(getApplicationContext()).load(uri.toString()).placeholder(R.drawable.default_event).into(immagine);
                }
            });
        }
        condividi = (ImageButton)findViewById(R.id.buttonCondividi);
        map = (ImageButton)findViewById(R.id.buttonMap);
        condividi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String shareBody = bodyString;
                String shareSub = bodyTitolo;
                myIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                myIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(myIntent, getString(R.string.condividiSu)));
            }
        });
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(myContext, MappaActivity.class);
                intent.putExtra("Sede", office);
                startActivity(intent);
            }
        });




    }

}

