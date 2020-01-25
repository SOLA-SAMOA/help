package org.sola.common.help;

/**
 * Class used to start SOLA help file. 
 * @author andrew.mcdowell
 */
public class LaunchHelp {
    
    public static void main(String[] args) {
        HelpUtility.getInstance().showTopic("sola_overview");
    }
    
}
