package com.rkbapps.imagesearch.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {MyFav.class}, version = 1)
public abstract class MyFavDatabase extends RoomDatabase {

    public abstract MyFavDao getMyFavDao();
}
