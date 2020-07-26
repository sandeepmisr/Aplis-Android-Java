package com.edu.sketch;

import android.app.Activity;
import android.view.textservice.SentenceSuggestionsInfo;
import android.view.textservice.SpellCheckerSession;
import android.view.textservice.SuggestionsInfo;

public class HelloSpellCheckerActivity extends Activity implements SpellCheckerSession.SpellCheckerSessionListener {
    @Override
    public void onGetSuggestions(final SuggestionsInfo[] arg0) {

//        Log.e("TAG",arg0[]);
        // TODO Auto-generated method stub
    }

    @Override
    public void onGetSentenceSuggestions(SentenceSuggestionsInfo[] arg0){
        // TODO Auto-generated method stub
    }
}
