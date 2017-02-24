package com.xavient.adServer.dataAccess;

import java.io.File;
import java.io.IOException;

public class CommonDAO {
	
	private static File db = new File("AdDatabase");

	public CommonDAO() {
		// TODO Auto-generated constructor stub
	}
	
	File getDB() throws IOException{
		
		if(!db.exists())
			db.createNewFile();
		return db;
	}

}

