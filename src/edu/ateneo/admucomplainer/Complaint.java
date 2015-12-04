package edu.ateneo.admucomplainer;

import java.io.Serializable;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

@ParseClassName("Complaint")
public class Complaint extends ParseObject {
	public void setId(int i){
		put("idNumber", i);
	}
	public int getId(){
		return getInt("idNumber");
	}
	public void setTitle(String n){
		put("name", n);
	}
	public String getTitle(){
		return getString("name");
	}
	public void setInfo(String n){
		put("info", n);
	}
	public String getInfo(){
		return getString("info");
	}
	public void setFactor(int f){
		put("inconFactor", f);
	}
	public int getFactor(){
		return getInt("inconFactor");
	}
	public void setStatus(String n){
		put("status", n);
	}
	public String getStatus(){
		return getString("status");
	}
	public void setThumb(ParseFile f)
	{
		put("thumb", f);
	}
	public ParseFile getThumb()
	{
		return getParseFile("thumb");
	}	
	public void setPic(ParseFile p)
	{
		put("photo", p);
	}
	public ParseFile getPic()
	{
		return getParseFile("photo");
	}
		
}
