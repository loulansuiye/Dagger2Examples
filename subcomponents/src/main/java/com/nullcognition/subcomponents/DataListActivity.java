package com.nullcognition.subcomponents;
// ersin 11/09/15 Copyright (c) 2015+ All rights reserved.

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;

public class DataListActivity extends BaseActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_data_list);
	}

	@Override
	protected void setupActivityComponent(){
		App.get(this).getUserComponent()
		   .plus(new DataListActivity.DataListActivityModule(this))
		   .inject(this);
	}

	@Override
	public void finish(){
		super.finish();
		App.get(this).releaseUserComponent();
	}

	@Module public static class DataListActivityModule{

		private DataListActivity dataListActivity;
		public DataListActivityModule(DataListActivity dla){dataListActivity = dla;}

		@Provides @ActivityScope DataListActivity provideDataListActivity(){return dataListActivity;}
	}


	@UserScope @Subcomponent(modules = DataListActivityModule.class)
	public interface DataListActivityComponent{

		DataListActivity inject(DataListActivity dataListActivity);
	}
}


abstract class BaseActivity extends AppCompatActivity{

	@Override protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setupActivityComponent();
	}

	protected abstract void setupActivityComponent();
}


@Scope @Retention(RetentionPolicy.RUNTIME) @interface ActivityScope{ }
