package com.hllbr.survivorbird;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.hllbr.survivorbird.SurvivorBird;

public class AndroidLauncher extends AndroidApplication {//Projeyi çalıştırmak için AndroidApplication'ı extends eden bir sınıf tamamen config bir sınıf burada bir işlem yapmıyoruz
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new SurvivorBird(), config);
	}
}
