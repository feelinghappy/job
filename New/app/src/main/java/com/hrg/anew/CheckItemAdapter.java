package com.hrg.anew;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CheckItemAdapter extends ArrayAdapter<checkitem>
{
    Map<Integer, Boolean> isCheckMap = new HashMap();
    public List<Integer> isSelected = new ArrayList();
    private int resourceId;

    public CheckItemAdapter(Context paramContext, int paramInt, List<checkitem> paramList)
    {
        super(paramContext, paramInt, paramList);
        this.resourceId = paramInt;
    }

    public static String listToString(List<Integer> paramList) {
        if (paramList == null)
            return null;
        StringBuilder localStringBuilder = new StringBuilder();
        int i = 0;
        Iterator itr = paramList.iterator();
        if (!itr.hasNext())
            return localStringBuilder.toString();
        Integer localInteger = (Integer) itr.next();
        if (i != 0)
            localStringBuilder.append(",");
        while (itr.hasNext()) {
            localStringBuilder.append(localInteger.toString());
        }
        return  localStringBuilder.toString();
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
        ViewHolder holder =new ViewHolder();
        checkitem localcheckitem = (checkitem)getItem(paramInt);
        if (paramView == null)
        {
            Log.e("paramView","null");
            LayoutInflater inflater=(LayoutInflater)paramViewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            paramView = inflater.inflate(R.layout.result_item, null);
            holder.checktext = ((TextView)paramView.findViewById(R.id.list_result_item_txt)); //  public static final int item_text = 2131165205;
            paramView.setTag(holder);
        }
        else
        {
            Log.e("paramView","not null");
            holder = (ViewHolder) paramView.getTag();
        }
        holder.checktext.setText(localcheckitem.getName());
        return paramView;
    }

    class ViewHolder
    {
        TextView checktext;

        ViewHolder()
        {
        }
    }
}