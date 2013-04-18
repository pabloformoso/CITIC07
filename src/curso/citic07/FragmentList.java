package curso.citic07;

import curso.citic07.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import curso.citic07.Quake;
import curso.citic07.adapters.QuakesListAdapter;
import curso.citic07.network.CommManager;


import android.annotation.SuppressLint;
import android.app.ListFragment;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;


public class FragmentList extends ListFragment {
	
	QuakesListAdapter adapter;
	ArrayList<Quake> earthquakes = new ArrayList<Quake>();
	
	private static final String TAG = "EARTHQUAKE";	
	
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			handleIncomingMsg(msg);
		}
	};

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.d(TAG, "ACTIVITY CREATEAD");
		
		adapter = new QuakesListAdapter(getActivity(), earthquakes);
		setListAdapter(adapter);
		
		CommManager.doReadHeartQuakes(getActivity(), handler);
	}
	
	private void handleIncomingMsg(Message msg) {
		Log.d(TAG, "handleMultimediaMessage()");
		
		switch (msg.what) {
		case 1:
			Log.d("TAG", "Load OK");	
		
			if (earthquakes != null) 
				earthquakes.clear();
			
			Bundle b = msg.getData();
			earthquakes = b.getParcelableArrayList("quakes");
			adapter = new QuakesListAdapter(getActivity(), this.earthquakes);
			setListAdapter(adapter);
			
			break;
			
		default:
			Log.w(TAG, "Load Error");
			break;
		}	
		
	}
}
