package lu.albert.android.dndcharsheet.util;

import java.util.List;

import lu.albert.android.dndcharsheet.R;
import lu.albert.d20character.api.Character;
import lu.albert.d20character.api.Weapon;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class WeaponAdapter extends ArrayAdapter<Weapon>{

		private final Activity context;
		private final List<Weapon> weapons;
		private Character mChar;

		public WeaponAdapter(Activity context, Character theChar) {
			super(context, R.layout.weapon_row, theChar.getWeapons()); 
			this.context = context;
			this.weapons = theChar.getWeapons();
			this.mChar = theChar;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = context.getLayoutInflater();
			View rowView = inflater.inflate(R.layout.weapon_row, null, true);
			Weapon wp = weapons.get(position);
			TextView attackBonus = (TextView) rowView.findViewById(R.id.txtAttackBonus);
			TextView name = (TextView) rowView.findViewById(R.id.txtWeaponName);
			TextView damage =  (TextView) rowView.findViewById(R.id.txtDamage);
			TextView critical =  (TextView) rowView.findViewById(R.id.txtCritical);
			TextView range =  (TextView) rowView.findViewById(R.id.txtRange);
			TextView type =  (TextView) rowView.findViewById(R.id.txtType);
			TextView notes =  (TextView) rowView.findViewById(R.id.txtNotes);
			
			attackBonus.setText(String.format("%+d", mChar.getAttackBonus(wp)));
			name.setText(wp.getName());
			damage.setText(wp.getDamage());
			critical.setText(wp.getCritical());
			range.setText(String.format("%d", wp.getRange()));
			type.setText(String.format("%s", wp.getDamageType()));
			notes.setText(wp.getNotes());

			return rowView;
		}

	
}
