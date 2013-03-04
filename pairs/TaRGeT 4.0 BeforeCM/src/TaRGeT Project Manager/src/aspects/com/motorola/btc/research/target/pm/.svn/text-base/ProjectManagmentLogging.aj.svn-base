/*
 * @(#)ProjectManagmentLogging.aj
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * dhq348    Apr 9, 2007    LIBll91788   Initial creation.
 * dhq348    Apr 18, 2007   LIBll91788   Rework of inspection LX164695.
 */
package com.motorola.btc.research.target.pm;

import java.util.HashMap;
import java.util.List;

import com.motorola.btc.research.target.common.tuclientagent.TrackUsageClient;
import com.motorola.btc.research.target.common.ucdoc.Feature;
import com.motorola.btc.research.target.common.ucdoc.UseCase;
import com.motorola.btc.research.target.pm.controller.ProjectManagerController;
import com.motorola.btc.research.target.pm.controller.TargetProjectRefreshInformation;
import com.motorola.btc.research.target.pm.editors.HTMLGenerator;
import com.motorola.btc.research.target.pm.errors.Error;

/**
 * This aspect is responsible for sending log information about project management operations.
 * 
 * <pre>
 * More specifically, it logs information about: 
 *      1) Use case preview
 *          1.1) Number of flows of the visualized use case
 *          1.2) A string indicating if the use case has errors or not
 *      2) Project reloading
 *          2.1) The classes of errors and their amount
 * </pre>
 */
public aspect ProjectManagmentLogging
{

    /**
     * Pointcut for opening use cases
     */
    pointcut useCasePreview(UseCase usecase, Feature feature, List<Error> errors):
        call(void HTMLGenerator.openUseCase*(UseCase , Feature , List<Error>))
        && args(usecase, feature, errors);

    /**
     * Pointcut for reloading the project
     */
    pointcut reloadProject(ProjectManagerController controller) : 
        call (TargetProjectRefreshInformation reloadProject())
        && target(controller);

    /**
     * Checks if the given use case has any error.
     * 
     * @param usecase The use case to be verified.
     * @param feature The feature which contains the use case.
     * @return A string 'true' if there is any error or the string 'false' otherwise.
     */
    private String checkUseCaseError(UseCase usecase, Feature feature)
    {
        return ProjectManagerController.getInstance().hasUseCaseError(usecase, feature) ? "true"
                : "false";
    }

    /**
     * Advice after useCasePreview pointcut. Logs information about the use case preview.
     */
    after(UseCase usecase, Feature feature, List<Error> errors) : useCasePreview(usecase, feature, errors) {
        String param1 = "USECASE_PREVIEW";
        String param2 = usecase.getFlows().size() + TrackUsageClient.PARAMETER_INTERNAL_SEPARATOR
                + checkUseCaseError(usecase, feature);
        TrackUsageClient.executeAgent(param1, param2);
    }

    /**
     * Advice after reloadProject pointcut. Log information about project reloading.
     */
    after(ProjectManagerController controller) : reloadProject(controller) {
        HashMap<String, Integer> errors = new HashMap<String, Integer>();
        for (Error error1 : controller.getErrorList())
        {
            String name = error1.getClass().getName();
            if (errors.get(name) != null)
            {
                errors.put(name, errors.remove(name) + 1);
            }
            else
            {
                errors.put(name, 1);
            }
        }
        String errorString = "";
        for (String key : errors.keySet())
        {
            errorString += key + "_" + errors.get(key)
                    + TrackUsageClient.PARAMETER_INTERNAL_SEPARATOR;
        }
        if (errorString.length() > 0)
        {
            errorString = errorString.substring(0, errorString.length() - 1);
            String param1 = "PROJ_RELOAD";
            TrackUsageClient.executeAgent(param1, errorString);
        }
    }
}
