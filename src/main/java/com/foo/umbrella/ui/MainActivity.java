package com.foo.umbrella.ui;

import android.app.Application;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.foo.umbrella.R;
import com.foo.umbrella.data.ApiServicesProvider;
import com.foo.umbrella.data.model.CurrentObservation;
import com.foo.umbrella.data.model.ForecastCondition;
import com.foo.umbrella.data.model.WeatherData;
import com.squareup.moshi.Json;
import com.squareup.moshi.Moshi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

  private TextView temp;
  private TextView location;
  private TextView icon;
  private ImageView background_color;
  private ImageView settings;
  retrofit2.Call<WeatherData> call;

  private TextView hourly_time;

  GridView gridView;

  String times[] = {"12:00 AM",
          "1:00 PM",
          "2:00 PM",
          "3:00 PM",
          "4:00 PM"};

  int icons[] = {R.drawable.weather_hail,
          R.drawable.weather_sunny,
          R.drawable.weather_cloudy,
          R.drawable.weather_lightning,
          R.drawable.weather_lightning_rainy};

  String temp_Data[] = {"22","22","23","23","22"};

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    temp = (TextView)findViewById(R.id.temperature);
    location = (TextView)findViewById(R.id.location);
    icon = (TextView)findViewById(R.id.icon);
    background_color = (ImageView)findViewById(R.id.BackGround_Color);
    settings = (ImageView)findViewById(R.id.settings);


    settings.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent i = new Intent(getApplicationContext(),SettingActivity.class);
        startActivity(i);
      }
    });


    ApiServicesProvider apiServicesProvider = new ApiServicesProvider(getApplication());
    Bundle extras = getIntent().getExtras();

    if(extras != null && extras.getString("zipCode").length()>0 ){

      apiServicesProvider.getWeatherService().forecastForZipCallable(extras.getString("zipCode"));
      call = apiServicesProvider.getWeatherService().
              forecastForZipCallable(extras.getString("zipCode"));
    }
    else{
      apiServicesProvider.getWeatherService().forecastForZipCallable("10010");
      call = apiServicesProvider.getWeatherService().
              forecastForZipCallable("10010");
    }

    if(extras != null && extras.getString("unit").length()>8){
      call.enqueue(new Callback<WeatherData>() {
        @Override
        public void onResponse(retrofit2.Call<WeatherData> call, Response<WeatherData> response) {

          String temperature = response.body().getCurrentObservation().getTempFahrenheit();

          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(Double.parseDouble(temperature)> 60.0)
              background_color.setBackgroundColor(getResources().getColor(R.color.weather_warm, getTheme()));
            else
              background_color.setBackgroundColor(getResources().getColor(R.color.weather_cool,getTheme()));
          }

          temp.setText(temperature + "\u2109");
          location.setText(response.body().getCurrentObservation().getDisplayLocation().getFullName());
          icon.setText(response.body().getCurrentObservation().getIconName());

//          while(response.body().getForecast().get(i).getDisplayTime() != 0);


        }

        @Override
        public void onFailure(retrofit2.Call<WeatherData> call, Throwable t) {

        }
      });

    }
    else{
      call.enqueue(new Callback<WeatherData>() {
        @Override
        public void onResponse(retrofit2.Call<WeatherData> call, Response<WeatherData> response) {

          String temperature = response.body().getCurrentObservation().getTempFahrenheit();

          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(Double.parseDouble(temperature)> 60.0)
              background_color.setBackgroundColor(getResources().getColor(R.color.weather_warm, getTheme()));
            else
              background_color.setBackgroundColor(getResources().getColor(R.color.weather_cool,getTheme()));
          }

          temperature = response.body().getCurrentObservation().getTempCelsius();
          temp.setText(temperature + "\u2103");

          location.setText(response.body().getCurrentObservation().getDisplayLocation().getFullName());
          icon.setText(response.body().getCurrentObservation().getIconName());

        }

        @Override
        public void onFailure(retrofit2.Call<WeatherData> call, Throwable t) {

        }
      });

    }


    gridView = (GridView)findViewById(R.id.gridview);
    GridViewAdapter adapter = new GridViewAdapter(MainActivity.this, icons, times, temp_Data);
    gridView.setAdapter(adapter);

  }

}
