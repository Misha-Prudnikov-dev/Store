package by.htp.store.dao.connection;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBResourceManager {

	private static final String DB_PROPERTIES = "db.properties";

	private static final DBResourceManager instance = new DBResourceManager();
	private InputStream inputStream;
	Properties p = new Properties();

	public void getPropValue() throws IOException {

		inputStream = getClass().getClassLoader().getResourceAsStream(DB_PROPERTIES);
		p.load(inputStream);
	}

	public static DBResourceManager getInstance() {
		return instance;
	}

	public String getValue(String key) {
		return p.getProperty(key);
	}
}
