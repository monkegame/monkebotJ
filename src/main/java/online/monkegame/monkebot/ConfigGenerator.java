package online.monkegame.monkebot;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigGenerator {

    public ConfigGenerator() {
    }

    public void generateConfig() throws IOException, InterruptedException {
        String a = System.getProperty("user.dir") + "/config.json";
        boolean b = new File(a).createNewFile();
        if (b) {
            System.out.println("[monkebot] New config generated. Please configure it.");
            FileWriter w = new FileWriter(a);
            w.write("""
                    {
                      "token": "",
                      "databaseLoc": "",
                      "databaseTableItaly": "",
                      "databaseTableMuseum": "",
                      "databaseTableHighrise": "",
                      "databaseTableAccounts": "",
                      "serverIp": ""
                    }
                    """);
            w.close();
            Thread.sleep(15000);
            System.exit(0);
        }
    }
}
