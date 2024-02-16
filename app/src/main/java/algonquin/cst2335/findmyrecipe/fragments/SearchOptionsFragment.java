package algonquin.cst2335.findmyrecipe.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.Map;

import algonquin.cst2335.findmyrecipe.R;

public class SearchOptionsFragment extends Fragment {

    private Context context;
    private View view;
    private SharedPreferences searchConfig;
    private SharedPreferences.Editor configEditor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_advanced_search, container, false);
        this.context = container.getContext();

        this.searchConfig = getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);
        this.configEditor = this.searchConfig.edit();



        /**
         * EVERY CHECKBOX GETS IT'S OWN LISTENER! YAY!
         */
        CheckBox gluten = view.findViewById(R.id.fragAdvGluten);
        gluten.setChecked(this.searchConfig.getBoolean("gluten", false));
        gluten.setOnCheckedChangeListener( (buttonView, isChecked) -> {
            Log.d("options", "gluten: " + String.valueOf(isChecked));
            this.configEditor.putBoolean("gluten", isChecked );
            this.configEditor.commit();
        });
        CheckBox dairy = view.findViewById(R.id.fragAdvDairy);
        dairy.setChecked(this.searchConfig.getBoolean("dairy", false));
        dairy.setOnCheckedChangeListener( (buttonView, isChecked) -> {
            Log.d("options", "dairy: " + String.valueOf(isChecked));
            this.configEditor.putBoolean("dairy", isChecked );
            this.configEditor.commit();
        });
        CheckBox wheat = view.findViewById(R.id.fragAdvWheat);
        wheat.setChecked(this.searchConfig.getBoolean("wheat", false));
        wheat.setOnCheckedChangeListener( (buttonView, isChecked) -> {
            Log.d("options", "wheat: " + String.valueOf(isChecked));
            this.configEditor.putBoolean("wheat", isChecked );
            this.configEditor.commit();
        });
        CheckBox sesame = view.findViewById(R.id.fragAdvSesame);
        sesame.setChecked(this.searchConfig.getBoolean("sesame", false));
        sesame.setOnCheckedChangeListener( (buttonView, isChecked) -> {
            Log.d("options", "sesame: " + String.valueOf(isChecked));
            this.configEditor.putBoolean("sesame", isChecked );
            this.configEditor.commit();
        });
        CheckBox grain = view.findViewById(R.id.fragAdvGrain);
        grain.setChecked(this.searchConfig.getBoolean("grain", false));
        grain.setOnCheckedChangeListener( (buttonView, isChecked) -> {
            Log.d("options", "grain: " + String.valueOf(isChecked));
            this.configEditor.putBoolean("grain", isChecked );
            this.configEditor.commit();
        });
        CheckBox seafood = view.findViewById(R.id.fragAdvSeafood);
        seafood.setChecked(this.searchConfig.getBoolean("seafood", false));
        seafood.setOnCheckedChangeListener( (buttonView, isChecked) -> {
            Log.d("options", "seafood: " + String.valueOf(isChecked));
            this.configEditor.putBoolean("seafood", isChecked );
            this.configEditor.commit();
        });
        CheckBox shellfish = view.findViewById(R.id.fragAdvShellfish);
        shellfish.setChecked(this.searchConfig.getBoolean("shellfish", false));
        shellfish.setOnCheckedChangeListener( (buttonView, isChecked) -> {
            Log.d("options", "shellfish: " + String.valueOf(isChecked));
            this.configEditor.putBoolean("shellfish", isChecked );
            this.configEditor.commit();
        });
        CheckBox peanuts = view.findViewById(R.id.fragAdvPeanuts);
        peanuts.setChecked(this.searchConfig.getBoolean("peanuts", false));
        peanuts.setOnCheckedChangeListener( (buttonView, isChecked) -> {
            Log.d("options", "peanuts: " + String.valueOf(isChecked));
            this.configEditor.putBoolean("peanuts", isChecked );
            this.configEditor.commit();
        });
        CheckBox treenuts = view.findViewById(R.id.fragAdvTreenuts);
        treenuts.setChecked(this.searchConfig.getBoolean("treenuts", false));
        treenuts.setOnCheckedChangeListener( (buttonView, isChecked) -> {
            Log.d("options", "treenuts: " + String.valueOf(isChecked));
            this.configEditor.putBoolean("treenuts", isChecked );
            this.configEditor.commit();
        });
        CheckBox egg = view.findViewById(R.id.fragAdvEgg);
        egg.setChecked(this.searchConfig.getBoolean("egg", false));
        egg.setOnCheckedChangeListener( (buttonView, isChecked) -> {
            Log.d("options", "egg: " + String.valueOf(isChecked));
            this.configEditor.putBoolean("egg", isChecked );
            this.configEditor.commit();
        });
        CheckBox soy = view.findViewById(R.id.fragAdvSoy);
        soy.setChecked(this.searchConfig.getBoolean("soy", false));
        soy.setOnCheckedChangeListener( (buttonView, isChecked) -> {
            Log.d("options", "soy: " + String.valueOf(isChecked));
            this.configEditor.putBoolean("soy", isChecked );
            this.configEditor.commit();
        });
        CheckBox sulfite = view.findViewById(R.id.fragAdvSulfite);
        sulfite.setChecked(this.searchConfig.getBoolean("sulfite", false));
        sulfite.setOnCheckedChangeListener( (buttonView, isChecked) -> {
            Log.d("options", "sulfite: " + String.valueOf(isChecked));
            this.configEditor.putBoolean("sulfite", isChecked );
            this.configEditor.commit();
        });



        Button close = view.findViewById(R.id.fragAdvClose);
        close.setOnClickListener(v -> {
            //getParentFragmentManager().popBackStackImmediate();
            //getActivity().getSupportFragmentManager().popBackStackImmediate();
            FragmentTransaction frag = getParentFragmentManager().beginTransaction();
            frag.replace(R.id.homeFragment, new SearchFragment());
            frag.commit();
        });

        Switch strict = view.findViewById(R.id.fragAdvStrict);
        strict.setChecked( this.searchConfig.getBoolean("strict", false) );
        strict.setOnCheckedChangeListener( ((buttonView, isChecked) -> {
            this.configEditor.putBoolean("strict", isChecked);
            this.configEditor.commit();
        }) );


        return view;
    }
}
