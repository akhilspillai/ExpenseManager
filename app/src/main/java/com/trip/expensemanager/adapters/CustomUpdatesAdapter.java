package com.trip.expensemanager.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.trip.expensemanager.R;
import com.trip.utils.Constants;

public class CustomUpdatesAdapter extends ArrayAdapter<String> {

	private Context context;
	private List<String> labels;
	private List<String> actions;

	static class ViewHolder {
		public TextView tvLabel;
		public ImageView ivIcon;
	}

	public CustomUpdatesAdapter(Context context, List<String> labels, List<String> actions) {
		super(context, R.layout.update_row_layout, labels);
		this.context=context;
		this.labels=labels;
		this.actions=actions;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		// reuse views
		if (rowView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = inflater.inflate(R.layout.update_row_layout, parent, false);
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.tvLabel = (TextView) rowView.findViewById(R.id.tv_update);
			viewHolder.ivIcon = (ImageView) rowView.findViewById(R.id.iv_update);
			rowView.setTag(viewHolder);
		}
		String strItem=labels.get(position);
		String action=actions.get(position);
		ViewHolder viewHolder=(ViewHolder) rowView.getTag();
		viewHolder.tvLabel.setText(strItem);
		
		if(action.equals(Constants.STR_USER_ADDED)){
			viewHolder.ivIcon.setImageResource(R.drawable.ic_person_added);
		} else if(action.equals(Constants.STR_USER_DELETED)){
			viewHolder.ivIcon.setImageResource(R.drawable.ic_person_exited);
		} else if(action.equals(Constants.STR_TRIP_DELETED)){
			viewHolder.ivIcon.setImageResource(R.drawable.ic_eg_deleted);
		} else if(action.equals(Constants.STR_TRIP_UPDATED)){
			viewHolder.ivIcon.setImageResource(R.drawable.ic_eg_updated);
		} else if(action.equals(Constants.STR_EXPENSE_ADDED)){
			viewHolder.ivIcon.setImageResource(R.drawable.ic_expense_added);
		} else if(action.equals(Constants.STR_EXPENSE_DELETED)){
			viewHolder.ivIcon.setImageResource(R.drawable.ic_expense_deleted);
		} else if(action.equals(Constants.STR_EXPENSE_UPDATED)){
			viewHolder.ivIcon.setImageResource(R.drawable.ic_expense_updated);
		}  else if(action.equals(Constants.STR_DISTRIBUTION_ADDED)){
			viewHolder.ivIcon.setImageResource(R.drawable.ic_debt_paid);
		} else if(action.equals(Constants.STR_NOT_EXITED)){
			viewHolder.ivIcon.setImageResource(R.drawable.ic_not_exited);
		} else if(action.equals(Constants.STR_NOT_UPDATED)){
			viewHolder.ivIcon.setImageResource(R.drawable.ic_not_updated);
		} else if(action.equals(Constants.STR_NOT_DELETED)){
			viewHolder.ivIcon.setImageResource(R.drawable.ic_not_deleted);
		} else if(action.equals(Constants.STR_NOT_SYNCED)){
			viewHolder.ivIcon.setImageResource(R.drawable.ic_not_synced);
		}
		
		/*Animation animation = AnimationUtils.loadAnimation(getContext(), (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
		rowView.startAnimation(animation);
		lastPosition = position;
	    animation = null;*/
		
		return rowView;
	}

	@Override
	public String getItem(int position) {
		return labels.get(position);
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

}