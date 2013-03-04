/*
 * @(#)TrackUsageClient.java
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
 * dhq348   Apr 10, 2007    LIBll91788   Initial creation.
 * dhq348   Apr 18, 2007    LIBll91788   Rework of inspection LX164695.
 */
package br.ufpe.cin.target.common.tuclientagent;

import br.ufpe.cin.target.common.util.FileUtil;

/**
 * This class represents a call to the application 'TUClientAgent.exe' given a number of parameters
 * that the application uses to send data to a remote server.
 */
public class TrackUsageClient
{

    /**
     * The folder that contains the application
     */
    protected static String folder = FileUtil.getUserDirectory() + FileUtil.getSeparator()
            + "resources" + FileUtil.getSeparator();


    /** URL separator */
    public static String PARAMETER_INTERNAL_SEPARATOR = "$";

    /**
     * Makes an URL to the server. Uses the parameters to compose such string. Later executes the
     * application 'TUClientAgent.exe' given the composed string.
     * 
     * @param param1 The first parameter used to compose the string to the server.
     * @param param2 The second parameter used to compose the string to the server.
     */
    public static void executeAgent(String param1, String param2)
    {
        String toolName = "TARGET";
        String toolVersion = "RSCH-TARGT_N_01.00.01I";
        String command = folder + "TUClientAgent.exe";

        command += " -u ";

        Process p = null;

        try
        {
            p = Runtime.getRuntime().exec(command);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        try
        {
            p.waitFor();
            int exitValue = p.exitValue();
            if (exitValue != 0)
            {
                System.out.println("exitValue != 0");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
