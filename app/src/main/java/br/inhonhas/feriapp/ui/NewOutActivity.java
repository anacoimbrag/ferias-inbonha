package br.inhonhas.feriapp.ui;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;
import java.util.Locale;

import br.com.sapereaude.maskedEditText.MaskedEditText;
import br.inhonhas.feriapp.Event;
import br.inhonhas.feriapp.R;
import br.inhonhas.feriapp.service.Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewOutActivity extends AppCompatActivity {

    TextView nameTextView;
    MaskedEditText dateTextView;
    TextView addressTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_out);
        setTitle("Adicionar Evento");

        nameTextView = (TextView) findViewById(R.id.name);
        dateTextView = (MaskedEditText) findViewById(R.id.date);
        addressTextView = (TextView) findViewById(R.id.address);

        dateTextView.setMask("##/##/####");

        Button button = (Button) findViewById(R.id.add_event);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEvent();
            }
        });
    }

    private LatLng getLatLongFromAddress(String address)
    {
        double lat= 0.0, lng= 0.0;

        Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
        try
        {
            List<Address> addresses = geoCoder.getFromLocationName(address , 1);
            if (addresses.size() > 0)
            {
                lat=addresses.get(0).getLatitude();
                lng=addresses.get(0).getLongitude();

                return new LatLng(lat, lng);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    private void addEvent() {
        LatLng latLng = getLatLongFromAddress(addressTextView.getText().toString());
        Event event = new Event();
        event.setName(nameTextView.getText().toString());
        event.setWhen(dateTextView.getText().toString());
        event.setAddress(addressTextView.getText().toString());
        if (latLng != null) {
            event.setLat(latLng.latitude);
            event.setLng(latLng.longitude);
        }

        Service.getInstance(true)
                .registerEvet(event)
                .enqueue(new Callback<Event>() {
                    @Override
                    public void onResponse(Call<Event> call, Response<Event> response) {
                        if(response.errorBody() == null) {
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<Event> call, Throwable t) {

                    }
                });
    }
}
