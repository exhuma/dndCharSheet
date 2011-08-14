package lu.albert.android.dndcharsheet.util;

import java.util.List;

import lu.albert.android.dndcharsheet.R;
import lu.albert.d20character.api.Character;
import lu.albert.d20character.api.ProtectiveItem;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ArmorAdapter extends ArrayAdapter<ProtectiveItem>{

		private final Activity context;
		private final List<ProtectiveItem> protectiveItems;

		public ArmorAdapter(Activity context, Character theChar) {
			super(context, R.layout.armor_row, theChar.getArmor()); 
			this.context = context;
			this.protectiveItems = theChar.getArmor();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = context.getLayoutInflater();
			View rowView = inflater.inflate(R.layout.armor_row, null, true);
			ProtectiveItem prot = protectiveItems.get(position);
			TextView name = (TextView) rowView.findViewById(R.id.txtArmorName);
			TextView type = (TextView) rowView.findViewById(R.id.txtArmorType);
			TextView armorRating = (TextView) rowView.findViewById(R.id.txtArmorRating);
			TextView notes = (TextView) rowView.findViewById(R.id.txtNotes);
			
			name.setText(prot.getName());
			type.setText(prot.getType());
			armorRating.setText(String.format("%+d", prot.getArmorClassBonus()));
			notes.setText(prot.getNotes());

			return rowView;
		}

	
}
