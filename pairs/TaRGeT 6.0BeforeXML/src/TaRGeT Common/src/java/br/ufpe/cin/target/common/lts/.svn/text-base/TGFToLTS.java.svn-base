/*
 * @(#)TGFToLTS.java
 *
 *
 * (Copyright (c) 2007-2009 Research Project Team-CIn-UFPE)
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE
 * USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 * 
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wsn013    Nov 28, 2006   LIBkk11577   Initial creation.
 * wsn013    Jan 18, 2007   LIBkk11577   Modification after inspection (LX135035).
 * wdt022    Jul 22, 2007   LIBmm42774   Moved from the common.util package.
 */
package br.ufpe.cin.target.common.lts;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.StringReader;
import java.util.Iterator;
import java.util.StringTokenizer;


/**
 * This class parses a TGF file into a LTS object. It is useful for DEBUG purposes.
 * 
 * <pre>
 * CLASS:
 * This class parses a TGF file into a LTS<String,String> object.
 *  
 * RESPONSIBILITIES:
 * 1) Parses a TGF file into a LTS<String,String> object. 
 *
 * COLABORATORS:
 * 1) Uses LTS class
 * 2) Uses State class
 * 3) Uses Transition class
 *
 * USAGE:
 * TGFToLTS t2l = new TGFToLTS(tgfContent);
 * LTS<String,String> lts = t2l.parse();
 */
public class TGFToLTS
{

    /**
     * Returns a LTS object from the parsed TGF string.
     * 
     * @return LTS object regarding the given TGF string.
     */
    public static LTS<String, String> parse(String tgfContent)
    {

        LTS<String, String> lts = new LTS<String, String>();

        LineNumberReader lnr = new LineNumberReader(new StringReader(tgfContent));
        String line = null;

        try
        {

            // populates states table
            line = lnr.readLine(); // state_index state_label 
            State<String, String> n = null;
            do
            {
                n = new State<String, String>();

                int spacei = line.indexOf(" ");

                int index = Integer.parseInt(line.substring(0, spacei));
                String label = line.substring(spacei + 1, line.length());

                n.setId(index);
                n.setValue(label);

                lts.insertState(n);

                if (label.equals("root"))
                    lts.setRoot(n); // identifies the root state of lts

                line = lnr.readLine(); // state_index state_label

            } while (!line.equals("#"));

            int i = 0;

            // populates transitions table
            line = lnr.readLine(); // from_state to_state transition_label
            Transition<String, String> e = null;
            do
            {
                e = new Transition<String, String>();

                StringTokenizer st = new StringTokenizer(line);

                Integer from = new Integer(st.nextToken());
                Integer to = new Integer(st.nextToken());
                String event = st.nextToken();

                e.setId(i);
                e.setFrom(lts.getState(from));
                e.setTo(lts.getState(to));
                e.setValue(event);

                lts.insertTransition(e);

                i++;

                line = lnr.readLine();

            } while (line != null);

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        Iterator<State<String, String>> ne = lts.getStates();
        Iterator<Transition<String, String>> ee = null;
        while (ne.hasNext())
        {
            State<String, String> n = (State<String, String>) ne.next();
            ee = lts.getTransitions();
            while (ee.hasNext())
            {
                Transition<String, String> e = (Transition<String, String>) ee.next();
                if (e.getFrom().equals(n))
                    n.addOutTransition(e);
                if (e.getTo().equals(n))
                    n.addInTransition(e);
            }
        }
        return lts;
    }

}
