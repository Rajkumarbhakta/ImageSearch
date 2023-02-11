package com.rkbapps.imagesearch.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MyFavDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long addToMyFav(MyFav myFav);

    @Update
    public void updateMyFav(MyFav myFav);

    @Delete
    public void deleteMyFav(MyFav myFav);

    @Query("select * from myfav")
    public List<MyFav> getAllFav();

    @Query("select imageId from myfav where imageId=:enterImageId")
    public boolean isMyFavExist(int enterImageId);

    @Query("select * from myFav where id=:enterId")
    public MyFav getMyFav(int enterId);

    @Query("delete from myfav where imageId=:enterImageId")
    public void removeMyFav(int enterImageId);


}
