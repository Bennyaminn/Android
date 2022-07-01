package com.example.lab3;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Phone.class}, version = 1, exportSchema = false)
public abstract class PhoneRoomDatabase extends RoomDatabase {
    public abstract  PhoneDao phoneDao();

    private static volatile  PhoneRoomDatabase INSTANCE;
    private static final int NUMBERE_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBERE_OF_THREADS);

    static PhoneRoomDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (PhoneRoomDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            PhoneRoomDatabase.class, "phone_database")
                                .addCallback(sRoomDatabaseCallback)
                                .fallbackToDestructiveMigration()
                                .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                    databaseWriteExecutor.execute(()->{
                        PhoneDao dao = INSTANCE.phoneDao();
                        Phone[] phones = {
                                new Phone("Google", "Piksel", "1.2", "www.google.com"),
                                new Phone("Nokia", "Costam", "1.6", "www.nokia.com"),
                                new Phone("Samsung", "Galaxy", "10", "www.samsung.com"),
                        };
                        for(Phone p : phones){
                            dao.insert(p);
                        }
                    });
                }
            };


}
