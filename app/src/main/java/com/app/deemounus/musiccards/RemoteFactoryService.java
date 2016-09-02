package com.app.deemounus.musiccards;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class RemoteFactoryService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
            return (new WidgetDataFactory(this.getApplicationContext(), intent));
    }
}
