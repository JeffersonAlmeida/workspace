package br.ufpe.cin.target.cnl.dialogs;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import br.ufpe.cin.cnlframework.postagger.POSUtil;
import br.ufpe.cin.cnlframework.vocabulary.terms.LexicalEntry;

class LexicalTemsLabelProvider extends LabelProvider implements ITableLabelProvider
{
    public String getColumnText(Object obj, int index)
    {
        LexicalEntry lexicalEntry = (LexicalEntry) obj;
        
        String result = "";
        
        if(index == 0)
        {
            result = POSUtil.getInstance().getDefaultTerm(lexicalEntry);
        }
        else if(index == 1)
        {
            result = POSUtil.getInstance().explainTerm(lexicalEntry);
        }

        return result;
    }

    /*
     * (non-Javadoc)
     * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object, int)
     */
    
    public Image getColumnImage(Object element, int columnIndex)
    {
        // TODO Auto-generated method stub
        return null;
    }
}
