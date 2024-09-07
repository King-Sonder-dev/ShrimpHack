package aids.dev.shrimphack.auth;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Files;

public class SelfDestruct {

    private static void selfDestructWindowsJARFile() throws Exception {
        String currentJARFilePath = SelfDestruct.getCurrentJarPath().toString();
        Runtime runtime = Runtime.getRuntime();
        runtime.exec("cmd /c ping localhost -n 2 > nul && del \"" + currentJARFilePath + "\"");
    }

    public static void selfDestructJARFile() throws Exception {
	String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
			// System.out.println("DEV MODE: Self Destruct has been activated");

            SelfDestruct.selfDestructWindowsJARFile();
        } else {
			// System.out.println("DEV MODE: Self Destruct has been activated");
			
            File directoryFilePath = SelfDestruct.getCurrentJarPath();
            Files.delete(directoryFilePath.toPath());
        }
    }
	
    public static File getCurrentJarPath() throws URISyntaxException {
        return new File(SelfDestruct.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
    }
}
