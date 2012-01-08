/**
 * ******************************************************************************************
 * Copyright (C) 2011 - Food and Agriculture Organization of the United Nations (FAO).
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice,this list
 *       of conditions and the following disclaimer.
 *    2. Redistributions in binary form must reproduce the above copyright notice,this list
 *       of conditions and the following disclaimer in the documentation and/or other
 *       materials provided with the distribution.
 *    3. Neither the name of FAO nor the names of its contributors may be used to endorse or
 *       promote products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT
 * SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,PROCUREMENT
 * OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,STRICT LIABILITY,OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * *********************************************************************************************
 */
package org.sola.common.help;

import java.awt.event.ActionListener;
import java.net.URL;
import java.util.logging.Level;
import javax.help.CSH;
import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import org.sola.common.messaging.ClientMessage;
import org.sola.common.messaging.MessageUtility;
import org.sola.common.logging.LogUtility;

/**
 *
 * @author dounnaah
 */
public class HelpUtility {

    private ActionListener al = null;
    private HelpSet hs = null;

    private HelpUtility() {
    }

    private static class HelpUtilityHolder {

        private static final HelpUtility INSTANCE = new HelpUtility();
    }

    public static HelpUtility getInstance() {
        return HelpUtilityHolder.INSTANCE;
    }

    /**
     * Returns a <code>HelpSet<code/> for the construction of an ActionListener.
     * @return Helpset 
     */
    private HelpSet getHelpSet() {
        if (hs == null) {
            String pathToHS = "defaultlang/HelpSet.xml";
            //hs = null;
            try {
                
                URL hsURL = getClass().getResource(pathToHS);
                LogUtility.log("Found HelpSet at " + pathToHS, Level.FINE);
                hs = new HelpSet(null, hsURL);
            } catch (Exception ee) {
                System.out.println("HelpSet: " + ee.getMessage());
                System.out.println("HelpSet: " + pathToHS + " not found");
                String[] params = {"" + ee.getMessage(), "" + pathToHS};
                MessageUtility.displayMessage(ClientMessage.EXCEPTION_HELPSET, params);
            }
        }
        return hs;
    }

    /**
     * Returns an <code>ActionListener<code/> for displaying help topics in a help viewer.
     *
     * @param object JButton to which the actionListener is reistered to listen for events
     * @param contextMapID MapID of the topic to be displayed
     * @return ActionListener for <code>object</code>
     */
    public ActionListener getHelpListener(JButton object, String contextMapID) {
        HelpBroker hb = getHelpSet().createHelpBroker();
        // hb.enableHelpKey(getMainForm(), "overview", hs);
        CSH.setHelpIDString(object, contextMapID);
        al = new CSH.DisplayHelpFromSource(hb);
        return al;
    }

    /**
     * Returns an <code>ActionListener<code/> for displaying help topics in a help viewer.
     *
     * @param object JMenuItem to which the actionListener is reistered to listen for events
     * @param contextMapID MapID of the topic to be displayed
     * @return ActionListener for <code>object</code>
     */
    public ActionListener getHelpListener(JMenuItem object, String contextMapID) {
        HelpBroker hb = getHelpSet().createHelpBroker();
        CSH.setHelpIDString(object, contextMapID);
        al = new CSH.DisplayHelpFromSource(hb);
        return al;

    }
}
