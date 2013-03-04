/*
 * @(#)StepSimilarity.java
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
 * faas      08/07/2008                  Initial creation.
 */
package br.ufpe.cin.target.cm.tcsimilarity.logic;

import java.util.StringTokenizer;

import br.ufpe.cin.target.cm.tcsimilarity.metrics.LevenshteinDistance;
import br.ufpe.cin.target.cm.tcsimilarity.metrics.LevenshteinDistanceWordByWord;
import br.ufpe.cin.target.tcg.extractor.TextualStep;
import br.ufpe.cin.target.tcg.util.TCGUtil;


/**
 * Represents the similarity between two test steps.
 */
public class StepSimilarity
{
    private LevenshteinDistance levChar;

    private LevenshteinDistanceWordByWord levWord;

    private TextualStep oldTestCaseStep;

    private TextualStep newTestCaseStep;

    private int orderDistance; // order that steps are grouped

    private double levDistanceUserAction;

    private double levDistanceExpectedResults;

    private double wordDistanceUserAction;

    private double wordDistanceExpectedResults;

    private int maxStepsSize; //Receives the value: Math.max(newTestCaseSteps.size(), oldTestCaseSteps.size());

    private String newTestCaseId;

    private String oldTestCaseId;

    private boolean alreadyVisited; // tells whether this StepSimilarity has been already visited in an array

    private String useCaseId;

    //INSPECT
    public StepSimilarity(TextualStep newTestCaseStep, TextualStep oldTestCaseStep, int newTestCaseId,
            int oldTestCaseId, String useCaseId)
    {
        if (oldTestCaseStep != null && newTestCaseStep != null)
        {
            //INSPECT - Lais inserted code
#if($xmloutput || $html)
            newTestCaseStep.setAction(TCGUtil.replaceNotUnicodeCharacters(newTestCaseStep.getAction()));
            newTestCaseStep.setSystemState(TCGUtil.replaceNotUnicodeCharacters(newTestCaseStep.getSystemState()));
            newTestCaseStep.setResponse(TCGUtil.replaceNotUnicodeCharacters(newTestCaseStep.getResponse()));
#end
            this.oldTestCaseStep = oldTestCaseStep;
            this.newTestCaseStep = newTestCaseStep;
            
            // Levenstein calculation of characters
            this.levChar = new LevenshteinDistance();
            this.levWord = new LevenshteinDistanceWordByWord();

            this.newTestCaseId = newTestCaseId + "'";
            this.oldTestCaseId = oldTestCaseId + "";
            this.useCaseId = useCaseId;

            double stepDistanceUsAction = this.levChar.modifiedLevenshteinDistance(oldTestCaseStep
                    .getAction(), newTestCaseStep.getAction());

            this.levDistanceUserAction = stepDistanceUsAction;

            double stepDistanceExpResults = this.levChar.modifiedLevenshteinDistance(oldTestCaseStep
                    .getResponse(), newTestCaseStep.getResponse());

            this.levDistanceExpectedResults = stepDistanceExpResults;

            // Levenstein calculation of words
            double wordDistanceUsAction = this.levWord.modifiedLevenshteinDistanceWordByWord(
                    oldTestCaseStep.getAction(), newTestCaseStep.getAction());

            this.wordDistanceUserAction = wordDistanceUsAction;

            double wordDistanceExpResults = this.levWord.modifiedLevenshteinDistanceWordByWord(
                    oldTestCaseStep.getResponse(), newTestCaseStep.getResponse());

            this.wordDistanceExpectedResults = wordDistanceExpResults;
        }
    }

    public double getSimilarityByWord()
    {
        double result = 0;

        if (this.oldTestCaseStep != null)
        {
            StringTokenizer tokenizer1 = new StringTokenizer(oldTestCaseStep.getAction());
            StringTokenizer tokenizer2 = new StringTokenizer(oldTestCaseStep.getResponse());
            int sourceTokens = tokenizer1.countTokens() + tokenizer2.countTokens();

            tokenizer1 = new StringTokenizer(newTestCaseStep.getAction());
            tokenizer2 = new StringTokenizer(newTestCaseStep.getResponse());
            int updatedTokens = tokenizer1.countTokens() + tokenizer2.countTokens();

            double wordDistanceTotal = getSumOfWordDistance()
                    / Math.max(sourceTokens, updatedTokens);
            result = wordDistanceTotal;
            result = (result + (this.orderDistance / this.maxStepsSize)) / 2;
        }
        else
        {
            result = 1 - (1 / this.maxStepsSize);
        }
        return result;
    }

    public double getSimilarityByChar()
    {
        double result = 0;

        if (this.oldTestCaseStep != null)
        {
            int lenghtSource = this.oldTestCaseStep.getAction().length();
            int lenghtSource2 = this.oldTestCaseStep.getResponse().length();
            int sourceLenght = lenghtSource + lenghtSource2;

            int lenghtUpdated = this.newTestCaseStep.getAction().length();
            int lenghtUpdated2 = this.newTestCaseStep.getResponse().length();
            int updatedLenght = lenghtUpdated + lenghtUpdated2;

            double charDistanceTotal = getSumOfCharDistance()
                    / Math.max(sourceLenght, updatedLenght);
            result = charDistanceTotal;
            result = (result + (this.orderDistance / this.maxStepsSize)) / 2;
        }
        else
        {
            result = 1 - (1 / this.maxStepsSize);
        }
        return result;
    }

    public void setOrderDistance(int i)
    {
        orderDistance = i;
    }

    public double getLevDistanceUserAction()
    {
        return levDistanceUserAction;
    }

    public double getWordDistanceUserAction()
    {
        return wordDistanceUserAction;
    }

    public double getLevDistanceExpectedResults()
    {
        return levDistanceExpectedResults;
    }

    public double getWordDistanceExpectedResults()
    {
        return wordDistanceExpectedResults;
    }

    public TextualStep getOldTestCaseStep()
    {
        return oldTestCaseStep;
    }

    public TextualStep getNewTestCaseStep()
    {
        return newTestCaseStep;
    }

    public int getOrderDistance()
    {
        return orderDistance;
    }

    public int getMaxStepsSize()
    {
        return maxStepsSize;
    }

    public void setMaxStepsSize(int maxStepsSize)
    {
        this.maxStepsSize = maxStepsSize;
    }

    public double getMeanWord()
    {
        return (this.wordDistanceUserAction + this.wordDistanceExpectedResults) / 2;
    }

    public double getMeanChar()
    {
        return (this.levDistanceUserAction + this.levDistanceExpectedResults) / 2;
    }

    public void clearStepSimilarity(int i, int k)
    {
        this.levDistanceExpectedResults = 100000 - i;
        this.levDistanceUserAction = 100000 - i;
        this.orderDistance = 100000 - k;
        this.wordDistanceExpectedResults = 100000 - i;
        this.wordDistanceUserAction = 100000 - i;
    }

    public String getNewTestCaseId()
    {
        return newTestCaseId;
    }

    public void setNewTestCaseId(String newTestCaseId)
    {
        this.newTestCaseId = newTestCaseId;
    }

    public String getOldTestCaseId()
    {
        return oldTestCaseId;
    }

    public void setOldTestCaseId(String oldTestCaseId)
    {
        this.oldTestCaseId = oldTestCaseId;
    }

    public double getSumOfWordDistance()
    {
        return this.wordDistanceUserAction + this.wordDistanceExpectedResults;
    }

    public double getSumOfCharDistance()
    {
        return this.levDistanceUserAction + this.levDistanceExpectedResults;
    }

    public boolean hasLowerSimilarityThan(StepSimilarity other)
    {
        boolean boo = false;

        if (other != null)
        {
        	if (this.hasLowerSumOfWordDistanceThan(other))
            {
                boo = true;
            }
            //Laís - penaliza o valor da similaridade quando os steps não estão perfeitamente emparelhados - não deve entrar na comparação.
//            else
//            {
//                if (this.hasLowerOrderDistanceThan(other))
//                {
//                    boo = true;
//                }
//            }
        }

        return boo;
    }

    private boolean hasLowerSumOfWordDistanceThan(StepSimilarity other)
    {
        boolean boo = false;
        if (other != null)
        {
            double diff = this.getSumOfWordDistance() - other.getSumOfWordDistance();
            if (diff < 0)
                boo = true;
        }
        return boo;
    }

    private boolean hasLowerOrderDistanceThan(StepSimilarity other)
    {
        boolean boo = false;
        if (other != null)
        {
            double diff = this.getOrderDistance() - other.getOrderDistance();
            if (diff < 0)
                boo = true;
        }
        return boo;
    }

    public boolean isAlreadyVisited()
    {
        return alreadyVisited;
    }

    public void setAlreadyVisited(boolean alreadyVisited)
    {
        this.alreadyVisited = alreadyVisited;
    }

    public String getUseCaseId()
    {
        return useCaseId;
    }

}
