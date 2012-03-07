package com.islamsharabash.cumtd;

import android.graphics.Color;
import android.text.Html;
import android.text.Spanned;

import java.text.SimpleDateFormat;
import java.util.Calendar;

//TODO this will depend on the new version of the api (to be released within a week of 11/15)
// TODO hold out for this: http://developer.cumtd.com/forum/default.aspx?g=posts&m=89&#post89
public class Departure {
	
	private String route;
	private int minutes;
	private String color;
	private float  LIGHT_OFFSET= .4f;
	private float SAT_OFFSET = -.15f;
	
	public Departure(String route, int minutes, String color) {
		this.route = route;
		this.minutes = minutes;
		this.color = brightenColor("#" + color);
	}
	
	// returns string hex of brightened color
	private String brightenColor(String str_color) {
		int color = Color.parseColor(str_color);
		float[] hsv = new float[3];
		Color.colorToHSV(color, hsv);
		
		hsv[2] = hsv[2] + LIGHT_OFFSET;
		hsv[1] = hsv[1] + SAT_OFFSET;
		
		int new_color = Color.HSVToColor(hsv);
		
		return String.format("#%06X", (0xFFFFFF & new_color));
	}

	// returns string for route name
	// using html to get perty colors :)
	public Spanned getRoute() {
		int index = this.route.indexOf(' ');
		String code = this.route.substring(0, index);
		String name = this.route.substring(index, this.route.length());
		
		return Html.fromHtml(
				"<font color=#FFFFFF>" + code + "</font>" +
				"<font color=" + this.color + ">" + name + "</font>");
	}
	
	// returns text to display for expected time
	public String getTime() {
		if (this.minutes == 0) {
			return "DUE";
		}
		
        /*TODO I used h (12 hour) instead of H (24 hour). I had difficulty trying to get access to the user's preference.
        If someone puts in a toggle for this, they should ask if they want 12 or 24 hour time, and change this to
        "H:mm" if the user prefers 24 hour time.*/ 
        SimpleDateFormat timingFormat = new SimpleDateFormat("h:mm");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, this.minutes);

		return Integer.toString(this.minutes) + "m " + timingFormat.format(cal.getTime());
	}
	
	// returns the color 
	public int getTimeColor() {
		if (this.minutes == 0) {
			return Color.RED;
		}
		
		return Color.WHITE;
	}
	

}
