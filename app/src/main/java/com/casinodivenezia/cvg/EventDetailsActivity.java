package com.casinodivenezia.cvg;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class EventDetailsActivity extends Activity{

    private String imageMain;
    private Context myContext;
    String bodyString;
    String bodyTitolo;
    String office;

    private ImageButton condividi;
    private ImageButton map;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://cmv-gioco.appspot.com/Events");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        myContext = this;


        setContentView(R.layout.activity_event_details);

        TextView name = (TextView) findViewById(R.id.textView8);
        TextView date = (TextView) findViewById(R.id.textView9);
        final TextView description = (TextView) findViewById(R.id.textView10);
        final ImageView immagine = (ImageView)findViewById(R.id.imageView7);
        name.setText(i.getStringExtra("name"));
        date.setText(i.getStringExtra("date"));
        description.setText(i.getStringExtra("description"));
        bodyString = i.getStringExtra("description");
        bodyTitolo = i.getStringExtra("name");
        office = i.getStringExtra("Office");

        Typeface XLight = Typeface.createFromAsset(getAssets(), "fonts/gothamxlight.otf");

        date.setTypeface(XLight);
        description.setTypeface(XLight);

        imageMain = (i.getStringExtra("imageMain"));
        StorageReference imagesRef = storageRef.child(imageMain);
        imagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(getApplicationContext()).load(uri.toString()).placeholder(R.drawable.default_event).into(immagine);
            }
        });
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
