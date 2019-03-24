package ifb.db3d.der6.util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FxmlResource {

	public static URL getFxmlPath(String file) {
		Path path = Paths.get("fxml" + File.separator + file + ".fxml");
		URL url = null;
		try {
			url = path.toUri().toURL();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return url;
	}
	
	public static String getIconPath(String file) {
		String path = "";
		try {
			URL url = Paths.get("fxml" + File.separator + "icons" + File.separator + file + ".png").toUri().toURL();
			path = url.toExternalForm();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return path;
	}

	public static String getCssPath(String file) {
		String path = "";
		try {
			URL url = Paths.get("fxml" + File.separator + "css" + File.separator + file + ".css").toUri().toURL();
			path = url.toExternalForm();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return path;
	}
}
