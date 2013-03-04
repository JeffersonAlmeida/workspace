package br.ufpe.cin.target.xlsinput.extractor;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.ufpe.cin.target.common.exceptions.TargetException;
import br.ufpe.cin.target.common.exceptions.UseCaseDocumentXMLException;
import br.ufpe.cin.target.common.ucdoc.Feature;
import br.ufpe.cin.target.common.ucdoc.Flow;
import br.ufpe.cin.target.common.ucdoc.FlowStep;
import br.ufpe.cin.target.common.ucdoc.StepId;
import br.ufpe.cin.target.common.ucdoc.UseCase;
import br.ufpe.cin.target.common.ucdoc.UseCaseDocument;
import br.ufpe.cin.target.tcg.util.ExcelExtractor;


public class UseCaseDocumentExtractor
{
    private File file;

    private ExcelExtractor excelExtractor;

    public UseCaseDocumentExtractor(File file)
    {
        this.file = file;
        this.excelExtractor = new ExcelExtractor();
    }

    public UseCaseDocument buildUseCaseDocument() throws TargetException
    {
        UseCaseDocument useCaseDocument = null;

        try
        {
            this.excelExtractor.inicializeExcelFile(this.file);

            this.excelExtractor.setSheet(1);

            List<Feature> features = this.extractFeatures();

            useCaseDocument = new UseCaseDocument(this.file.getAbsolutePath(),
                    this.file.lastModified(), features);
            
            this.excelExtractor.closeStream();
        }
        catch (Exception e)
        {
            e.printStackTrace();

            throw new TargetException();
        }

        return useCaseDocument;
    }

    private List<Feature> extractFeatures()
    {
        List<Feature> features = new Vector<Feature>();
        DecimalFormat df = new DecimalFormat("000");
        
        int currentRow = 22;
        
        this.excelExtractor.setCurrentRow(currentRow);
        
        String featureID = ""; 
        for (int i = 23; !this.excelExtractor.finished() && !this.isFinished() && featureID.equals(""); i++)
        {
            featureID = this.parseID(this.excelExtractor.getCellString(1));
            this.excelExtractor.setCurrentRow(i);
        }
        
        int useCaseID = 1;
        this.excelExtractor.setCurrentRow(currentRow);
        
        while (!this.excelExtractor.finished() && !this.isFinished())
        {
            List<UseCase> useCases = new Vector<UseCase>();


            List<Flow> flows = new Vector<Flow>();
            List<FlowStep> flowsStep = new Vector<FlowStep>();

            String coverageAreaUseCaseText = this.excelExtractor.getCellString(3);

            StepId flowStepID = new StepId(featureID, df.format(useCaseID), "1M");
            
            //Inserted code
            
            String userAction  = "";
            String response = "";
            
            String condition = this.getCondition(coverageAreaUseCaseText);
                
            if (!condition.equals(""))
            {
                coverageAreaUseCaseText = coverageAreaUseCaseText.replace(condition.substring(0, condition.length()-1), "");
                coverageAreaUseCaseText = coverageAreaUseCaseText.replace("Conditions:", "").replace(";", "").trim();
            }
            
            List<String> sentences = this.extractSentences(coverageAreaUseCaseText);
            
            for (String string : sentences)
            {
                if(string.toLowerCase().startsWith("verify"))
                {
                    response = response + string + ". ";
                }
                else{
                    userAction = userAction + string + ". ";
                }
            }
            if (response.equals(""))
            {
                response = "EMPTY";
            }
            if (userAction.equals(""))
            {
                userAction = "EMPTY";
            }
                        
            //end of inserted code
            
            FlowStep flowStep = new FlowStep(flowStepID, userAction, condition, response, this
                    .retrieveRequirements(this.excelExtractor.getCellString(1)));

            flowsStep.add(flowStep);

            Set<StepId> resultStart = new LinkedHashSet<StepId>();
            resultStart.add(StepId.START);

            Set<StepId> resultEnd = new LinkedHashSet<StepId>();
            resultEnd.add(StepId.END);

            Flow flow = new Flow(flowsStep, resultStart, resultEnd, "");
            flows.add(flow);

            UseCase useCase = new UseCase(df.format(useCaseID), "Use Case " + df.format(useCaseID),
                    "", flows, "");

            Feature featureToCompare = new Feature(featureID, "", new Vector<UseCase>());
            int index = features.indexOf(featureToCompare);

            if (index > -1)
            {
                featureToCompare = features.get(index);
                
                flows = new Vector<Flow>();
                flowsStep = new Vector<FlowStep>();
                
                flowStepID = new StepId(featureID, df.format(useCaseID), "1M");

                flowStep = new FlowStep(flowStepID, userAction, condition, response, this
                        .retrieveRequirements(this.excelExtractor.getCellString(1)));
                
                flowsStep.add(flowStep);
                
                flow = new Flow(flowsStep, resultStart, resultEnd, "");
                flows.add(flow);

                useCase = new UseCase(df.format(useCaseID),
                        "Use Case " + df.format(useCaseID), "",
                        flows, "");

                featureToCompare.getRealUseCases().add(useCase);
            }
            else
            {
                useCases.add(useCase);
            }

            this.excelExtractor.nextRow();

            useCaseID = useCaseID + 1;

            if (!features.contains(featureToCompare))
            {
                features.add(new Feature(featureID, "Feature " + featureID, useCases));
            }
            
            currentRow = currentRow + 1; 
            this.excelExtractor.setCurrentRow(currentRow);
        }
        
        return features;
    }

    /**
     * Comment for method.
     * 
     * @return
     */
    private boolean isFinished()
    {
        return this.excelExtractor.getCellString(1).equals("")
                && this.excelExtractor.getCellString(2).equals("")
                && this.excelExtractor.getCellString(3).equals("")
                && this.excelExtractor.getCellString(4).equals("")
                && this.excelExtractor.getCellString(5).equals("")
                && this.excelExtractor.getCellString(6).equals("");
    }

    private String getCondition(String coverageAreaUseCaseText)
    {
        String result = "";

        if (coverageAreaUseCaseText.contains(";"))
        {
            result = coverageAreaUseCaseText.split(Pattern.quote(";"))[0]
                    .replace("Conditions:", "").trim();
            
            if(!result.endsWith("."))
            {
                result = result + ".";
            }
        }

        return result;
    }

    /**
     * Receives a step field (action, condition or response) and extracts the related requirements.
     * A single requirement in the specifications may be any set of characters except brackets and
     * comma. A list of single requirements starts with a "[" and may contain one ore more single
     * requirements, separated by comma, and finishes with a "]". For instance, the processing of
     * the step field "Test the step field [req1, req2]" would return a set containing the strings
     * "req1" and "req2".
     * 
     * @param stepField The step field
     * @return A set containing all related requirements.
     * @throws UseCaseDocumentXMLException
     */

    private Set<String> retrieveRequirements(String stepField)
    {
        Set<String> requirements = new LinkedHashSet<String>();

        if (!stepField.equals(""))
        {
            String supostoRequirement = "";

            for (int i = 0; i < stepField.length(); i++)
            {
                if (stepField.charAt(i) == '[')
                {
                    supostoRequirement = supostoRequirement + stepField.charAt(i++);

                    while (stepField.charAt(i) != ']' && i < stepField.length())
                    {
                        supostoRequirement = supostoRequirement + stepField.charAt(i++);
                    }

                    if (stepField.charAt(i) == ']' && i < stepField.length())
                    {
                        supostoRequirement = supostoRequirement + stepField.charAt(i++);
                    }

                    if (supostoRequirement.startsWith("[") && supostoRequirement.endsWith("]"))
                    {
                        requirements.add(supostoRequirement);
                    }

                    supostoRequirement = "";
                }
            }
        }

        return requirements;
    }

    private String parseID(String cellString)
    {
        String result = "";

        for (int i = 0; i < cellString.length(); i++)
        {
            if (cellString.charAt(i) >= '0' && cellString.charAt(i) <= '9')
            {
                result = result + cellString.charAt(i) + "";
            }
            else if (!result.equals(""))
            {
                return result;
            }
        }

        return result;
    }
    
    //INSPECT Lais new Method
    private List<String> extractSentences(String text)
    {
        List<String> result = new ArrayList<String>();

        // accepts 0..n blank spaces + (full stop | exclamation mark | question mark) + 0..n blank
        // spaces
        Pattern sentenceSeparator = Pattern
                .compile("\\p{Space}*[\\u0021\\u003F\\u002E]+\\p{Space}*");

        Matcher matcher = sentenceSeparator.matcher(text);

        int last = 0;
        String sentence = null;

        while (matcher.find())
        {

            sentence = text.substring(last, matcher.start());
            if (!sentence.trim().equals(""))
            {
                result.add(sentence);
            }
            last = matcher.end();

        }

        sentence = text.substring(last, text.length());
        if (!sentence.trim().equals(""))
        {

            result.add(sentence);
        }

        return result;
    }
}
