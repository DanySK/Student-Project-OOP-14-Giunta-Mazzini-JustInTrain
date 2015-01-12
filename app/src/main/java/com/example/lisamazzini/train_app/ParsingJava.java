package com.example.lisamazzini.train_app;


import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class ParsingJava{
	public static void main(String[] args){
		try{
			String stazione = "Pesaro";
			String baseurl = "http://mobile.viaggiatreno.it/vt_pax_internet/mobile";
			String action = "/tragitto";
			String action1 = "/stazione";
			String action2 = "/numero";
			String action3 = "/programmato";

			URL url = new URL(baseurl+action2+"?");
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
//			out.write("partenza=" + "cesena&" + "arrivo=" + "pesaro&");
			out.write("numeroTreno=" + "608");
			out.close();
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			System.out.println(in.toString());
//			String inputLine;
//			while ((inputLine = in.readLine()) != null) 
//				System.out.println(inputLine);
			in.close();

//			System.out.println(userAgent.doc.innerHTML());               //print the content as HTML
		}
		catch(IOException e){         //if an HTTP/connection error occurs, handle JauntException.
			System.err.println(e);
		}
		
		
	}
}