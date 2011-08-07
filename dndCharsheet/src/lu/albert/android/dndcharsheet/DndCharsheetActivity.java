package lu.albert.android.dndcharsheet;

import lu.albert.d20character.CharacterImpl;
import lu.albert.d20character.ProtectiveItemImpl;
import lu.albert.d20character.api.Character;
import android.app.Activity;
import android.os.Bundle;

public class DndCharsheetActivity extends Activity {
	
	public static final String TAG = "DND_CHARSHEET";
	public static String ARG_INDEX = "index";
	private Character loadedCharacter;
	
    public DndCharsheetActivity() {
		super();
		this.loadedCharacter = new CharacterImpl();
		this.loadedCharacter.setStrength(16);
		this.loadedCharacter.setDexterity(18);
		this.loadedCharacter.setConstitution(13);
		this.loadedCharacter.setIntelligence(14);
		this.loadedCharacter.setWisdom(16);
		this.loadedCharacter.setCharisma(8);
		this.loadedCharacter.setHitPoints(32);
		this.loadedCharacter.setName("Hardcoded test character");
		this.loadedCharacter.getArmor().add(
				new ProtectiveItemImpl("foo", 6, 1, -6, 30));
	}

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    public Character getCharacter(){
    	return this.loadedCharacter;
    }
    
}