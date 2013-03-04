package com.motorola.btc.research.target.cm.tcsimilarity.logic;

import java.util.StringTokenizer;

import com.motorola.btc.research.target.cm.tcsimilarity.metrics.LevenshteinDistance;
import com.motorola.btc.research.target.cm.tcsimilarity.metrics.LevenshteinDistanceWordByWord;
import com.motorola.btc.research.target.tcg.extractor.TextualStep;

/**
 * Represents the similarity between two test steps.
 */
public class StepSimilarity
{

    private LevenshteinDistance levChar;

    private LevenshteinDistanceWordByWord levWord;

    private TextualStep sourceStep;

    private TextualStep modifiedStep;

    private int orderDistance;

    private double levDistanceUsAction;

    private double levDistanceExpResults;

    private double wordDistanceUsAction;

    private double wordDistanceExpResults;

    private int maxStepsSize;

    private String modifiedId;

    private String originalId;

    private boolean alreadyVisited; // tells whether this StepSimilarity has been already visited in

    // an array

    private String UCId;

    public StepSimilarity(TextualStep modifiedStep, TextualStep originalStep, int modifId,
            int origId, String ucId)
    {
        if (originalStep != null && modifiedStep != null)
        {
            this.sourceStep = originalStep;
            this.modifiedStep = modifiedStep;
            // calculo do levenstein de caracteres
            levChar = new LevenshteinDistance();
            levWord = new LevenshteinDistanceWordByWord();

            this.modifiedId = modifId + "'";
            this.originalId = origId + "";
            UCId = ucId;

            double stepDistanceUsAction = levChar.modifiedLevenshteinDistance(originalStep
                    .getAction(), modifiedStep.getAction());

            levDistanceUsAction = stepDistanceUsAction;

            double stepDistanceExpResults = levChar.modifiedLevenshteinDistance(originalStep
                    .getResponse(), modifiedStep.getResponse());

            levDistanceExpResults = stepDistanceExpResults;

            // calclo do levenstein de palavras
            double wordDistanceUsAction = levWord.modifiedLevenshteinDistanceWordByWord(
                    originalStep.getAction(), modifiedStep.getAction());

            this.wordDistanceUsAction = wordDistanceUsAction;

            double wordDistanceExpResults = levWord.modifiedLevenshteinDistanceWordByWord(
                    originalStep.getResponse(), modifiedStep.getResponse());

            this.wordDistanceExpResults = wordDistanceExpResults;
        }

    }

    public double getSimilarityByWord()
    {

        double result = 0;

        if (this.sourceStep != null)
        {
            StringTokenizer tokenizer1 = new StringTokenizer(sourceStep.getAction());
            StringTokenizer tokenizer2 = new StringTokenizer(sourceStep.getResponse());
            int sourceTokens = tokenizer1.countTokens() + tokenizer2.countTokens();

            tokenizer1 = new StringTokenizer(modifiedStep.getAction());
            tokenizer2 = new StringTokenizer(modifiedStep.getResponse());
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

        if (this.sourceStep != null)
        {
            int lenghtSource = this.sourceStep.getAction().length();
            int lenghtSource2 = this.sourceStep.getResponse().length();
            int sourceLenght = lenghtSource + lenghtSource2;

            int lenghtUpdated = this.modifiedStep.getAction().length();
            int lenghtUpdated2 = this.modifiedStep.getResponse().length();
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

    public double getLevDistanceUsAction()
    {
        return levDistanceUsAction;
    }

    public double getWordDistanceUsAction()
    {
        return wordDistanceUsAction;
    }

    public double getLevDistanceExpResults()
    {
        return levDistanceExpResults;
    }

    public double getWordDistanceExpResults()
    {
        return wordDistanceExpResults;
    }

    public TextualStep getSourceStep()
    {
        return sourceStep;
    }

    public TextualStep getModifiedStep()
    {
        return modifiedStep;
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
        return (this.wordDistanceUsAction + this.wordDistanceExpResults) / 2;
    }

    public double getMeanChar()
    {
        return (this.levDistanceUsAction + this.levDistanceExpResults) / 2;
    }

    // o k varia a distancia física... ela estava permanecendo igual para os ghosts de uma mesma
    // linha OU coluna
    public void clearStepSimilarity(int i, int k)
    {
        this.levDistanceExpResults = 100000 - i;
        this.levDistanceUsAction = 100000 - i;
        this.orderDistance = 100000 - k;
        this.wordDistanceExpResults = 100000 - i;
        this.wordDistanceUsAction = 100000 - i;
    }

    public String getModifiedId()
    {
        return modifiedId;
    }

    public void setModifiedId(String modifiedId)
    {
        this.modifiedId = modifiedId;
    }

    public String getOriginalId()
    {
        return originalId;
    }

    public void setOriginalId(String originalId)
    {
        this.originalId = originalId;
    }

    public double getSumOfWordDistance()
    {
        return this.wordDistanceUsAction + this.wordDistanceExpResults;
    }

    public double getSumOfCharDistance()
    {
        return this.levDistanceUsAction + this.levDistanceExpResults;
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
            else
            {
                if (this.hasLowerOrderDistanceThan(other))
                {
                    boo = true;
                }
            }
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

    public String getUCId()
    {
        return UCId;
    }

}
