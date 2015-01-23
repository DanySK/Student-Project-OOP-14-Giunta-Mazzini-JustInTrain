package com.example.lisamazzini.train_app.Older;

import java.io.IOException;
import java.text.ParseException;


public interface IScraper {
	
	Train computeResult() throws ParseException, IOException;
	
}
