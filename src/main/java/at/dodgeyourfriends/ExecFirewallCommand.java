package at.dodgeyourfriends;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ExecFirewallCommand {

    public static void blockRegion(String ipAdress) throws IOException, InterruptedException {
        Runtime.getRuntime().exec("netsh advfirewall firewall add rule name=\"lolchat\" dir=out remoteip=" + ipAdress + " protocol=TCP action=block");
        System.out.println("netsh advfirewall firewall add rule name=\"lolchat\" dir=out remoteip=" + ipAdress + " protocol=TCP action=block");
    }

    public static void openRegion() throws IOException, InterruptedException {
        Runtime.getRuntime().exec("netsh advfirewall firewall delete rule name=\"lolchat\"");
        System.out.println("netsh advfirewall firewall delete rule name=\"lolchat\"");
    }

    public static boolean checkFirewallEntry() throws IOException {
        Process checkRule = Runtime.getRuntime().exec("netsh advfirewall firewall show rule dir=out name=lolchat");
        BufferedReader reader = new BufferedReader(new InputStreamReader(checkRule.getInputStream()));
        String line;
        boolean isRuleAvaiable = false;

        while ((line = reader.readLine()) != null) {
            if(line.contains("lolchat")) {
                isRuleAvaiable = true;
            }
        }

        System.out.println(isRuleAvaiable);
        return isRuleAvaiable;
    }
}