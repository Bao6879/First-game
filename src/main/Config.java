package main;

import java.io.*;
import java.util.Objects;

public class Config {
    gamePanel gp;
    public Config(gamePanel gp)
    {
        this.gp=gp;
    }
    public void saveConfig()
    {
        try {
            BufferedWriter bw=new BufferedWriter(new FileWriter("config.txt"));
            if (gp.fullScreen==true)
                bw.write("On");
            if (gp.fullScreen==false)
                bw.write("Off");
            bw.newLine();
            bw.write(String.valueOf(gp.music.volumeScale));
            bw.newLine();
            bw.write(String.valueOf(gp.se.volumeScale));
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void loadConfig()
    {
        try {
            BufferedReader br=new BufferedReader(new FileReader("config.txt"));
            String s=br.readLine();
            if (Objects.equals(s, "On"))
                gp.fullScreen=true;
            else
                gp.fullScreen=false;
            s=br.readLine();
            gp.music.volumeScale=Integer.parseInt(s);
            s=br.readLine();
            gp.se.volumeScale=Integer.parseInt(s);
            br.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
