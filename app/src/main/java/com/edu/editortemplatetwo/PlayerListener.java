package com.edu.editortemplatetwo;

import com.edu.discover.Books;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import java.util.List;

public interface PlayerListener {
    void clickonplay(String content, SimpleExoPlayerView simpleExoPlayer);
}
