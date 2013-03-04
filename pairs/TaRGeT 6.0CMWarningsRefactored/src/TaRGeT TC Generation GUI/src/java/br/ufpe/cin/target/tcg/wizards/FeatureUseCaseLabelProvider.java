/*
 * @(#)FeatureUseCaseLabelProvider.java
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
 * dhq348    Oct 22, 2007   LIBoo10574   Initial creation.
 * dhq348    Jan 22, 2008   LIBoo10574   Rework after inspection LX229632.
 */
package br.ufpe.cin.target.tcg.wizards;

import java.util.HashMap;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import br.ufpe.cin.target.tcg.TCGActivator;

import br.ufpe.cin.target.common.ucdoc.Feature;
import br.ufpe.cin.target.common.ucdoc.FlowStep;
import br.ufpe.cin.target.common.ucdoc.UseCase;
import br.ufpe.cin.target.pm.common.TreeObject;

/**
 * Class that provides image and text for tree objects used in a feature - use cases tree.
 */

public class FeatureUseCaseLabelProvider extends LabelProvider
{
    /**
     * Cache created in order to load the icons from the disk only when necessary.
     */
    private HashMap<ImageDescriptor, Image> imageCache = new HashMap<ImageDescriptor, Image>();

    /**
     * @return An image according to the type on the given <code>element</code>. It is expected
     * that the <code>element</code> passed as a parameter is a TreeObject.
     */
    
    public Image getImage(Object element)
    {
        Image image = null;
        if (element instanceof TreeObject)
        {
            TreeObject node = (TreeObject) element;
            if (node.getValue() instanceof Feature)
            {
                ImageDescriptor descriptor = TCGActivator.imageDescriptorFromPlugin(
                        TCGActivator.PLUGIN_ID, "icons/feature_icon.gif");
                image = imageCache.get(descriptor);
                if (image == null)
                {
                    image = descriptor.createImage();
                    imageCache.put(descriptor, image);
                }
            }
            else if (node.getValue() instanceof UseCase)
            {
                ImageDescriptor descriptor = TCGActivator.imageDescriptorFromPlugin(
                        TCGActivator.PLUGIN_ID, "icons/usecase.gif");
                image = imageCache.get(descriptor);
                if (image == null)
                {
                    image = descriptor.createImage();
                    imageCache.put(descriptor, image);
                }
            }
            else if (node.getValue() instanceof FlowStep)
            {
                ImageDescriptor descriptor = TCGActivator.imageDescriptorFromPlugin(
                        TCGActivator.PLUGIN_ID, "icons/step.gif");
                image = imageCache.get(descriptor);
                if (image == null)
                {
                    image = descriptor.createImage();
                    imageCache.put(descriptor, image);
                }
            }

            if (image != null)
            {
                image = new Image(image.getDevice(), image.getImageData().scaledTo(16, 16));

            }
        }

        return image;
    }

    /**
     * @return A string according to the type on the given <code>element</code>. It is expected
     * that the <code>element</code> passed as a parameter is a TreeObject.
     */
    
    public String getText(Object element)
    {
        String result = "";

        if (element instanceof TreeObject)
        {
            TreeObject node = (TreeObject) element;
            if (node.getValue() instanceof Feature)
            {
                result = ((Feature) node.getValue()).toString();
            }
            else if (node.getValue() instanceof UseCase)
            {
                result = ((UseCase) node.getValue()).toString();
            }
            else if (node.getValue() instanceof FlowStep)
            {
                FlowStep flowStep = ((FlowStep) node.getValue());
                result = flowStep.getId().getStepId() + " - " + flowStep.getUserAction();
            }
        }
        return result;
    }
}
