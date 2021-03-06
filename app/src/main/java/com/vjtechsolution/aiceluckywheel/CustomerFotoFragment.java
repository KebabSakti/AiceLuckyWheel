package com.vjtechsolution.aiceluckywheel;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.io.File;


/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerFotoFragment extends Fragment {

    private String username, api_token, kode_asset;
    private Context context;
    private SharedPreferences sharedPreferences;
    private ImageView goCamera;
    private ImageView fotoContainer;
    private TextView goCameraText;
    LinearLayout bioContainer;
    private View v;
    private String foto;
    private EditText nama;
    private EditText no_telp;
    private CheckBox conf;

    public CustomerFotoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_customer_foto, container, false);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        context = getContext();

        sharedPreferences = context.getSharedPreferences(getString(R.string.key_preference), Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username","");
        api_token = sharedPreferences.getString("api_token", "");
        kode_asset = sharedPreferences.getString("kode_asset", "");

        goCamera = v.findViewById(R.id.goCamera);
        fotoContainer = v.findViewById(R.id.fotoContainer);
        bioContainer = v.findViewById(R.id.bioContainer);
        nama = v.findViewById(R.id.nama);
        no_telp = v.findViewById(R.id.no_telp);
        conf = v.findViewById(R.id.confCb);
        goCameraText = v.findViewById(R.id.goCameraText);

        goCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent camera = new Intent(getActivity(), CameraActivity.class);
                startActivityForResult(camera, 1);
            }
        });

        conf.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Log.d("FOCUSDEB", nama.getText().toString()+" | "+no_telp.getText().toString());

                    sentEvent(foto, nama.getText().toString(), no_telp.getText().toString());

                    nama.setEnabled(false);
                    no_telp.setEnabled(false);
                }else {
                    nama.setEnabled(true);
                    no_telp.setEnabled(true);

                    sentEvent(foto, "", "");
                }
            }
        });
    }

    public void sentEvent(String foto, String nama, String no_telp){
        EventBus.getDefault().post(new EvBusProduct("",0, foto, nama, no_telp));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) switch (requestCode) {
            case 1:
                goCameraText.setText("Sentuh untuk mengganti foto");
                foto = data.getStringExtra("image");
                File img = new File(foto);
                Picasso.get().load(img).into(fotoContainer);

                bioContainer.setVisibility(View.VISIBLE);
                break;
        }
    }
}
