package kg.askaromurkanov.appliancestoreandroid.data;

import android.content.Context;

import androidx.room.Room;

import kg.askaromurkanov.appliancestoreandroid.data.room.AppDatabase;

public class App {
    private static AppDatabase appDatabase;

    public static AppDatabase getAppDatabase(Context context){
        if (appDatabase == null){
            appDatabase = Room
                    .databaseBuilder(context, AppDatabase.class, "database")
                    .allowMainThreadQueries()
                    .build();
        }
        return appDatabase;
    }
}
