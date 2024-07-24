package com.pro.resume.craft.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.pro.resume.craft.database.dao.UserDao;
import com.pro.resume.craft.models.DTOCoverLetter;
import com.pro.resume.craft.models.DTOEducation;
import com.pro.resume.craft.models.DTOExperience;
import com.pro.resume.craft.models.DTOHobbies;
import com.pro.resume.craft.models.DTOObjective;
import com.pro.resume.craft.models.DTOPersonalInfo;
import com.pro.resume.craft.models.DTOProfile;
import com.pro.resume.craft.models.DTOReference;
import com.pro.resume.craft.models.DTOSavedResumes;
import com.pro.resume.craft.models.DTOSkills;
import com.pro.resume.craft.models.DTOTemplate;
import com.pro.resume.craft.models.DTOlanguages;
import com.pro.resume.craft.utils.BitmapTypeConverter;

@Database(entities = {DTOProfile.class, DTOPersonalInfo.class, DTOObjective.class, DTOExperience.class, DTOEducation.class, DTOHobbies.class, DTOlanguages.class, DTOReference.class, DTOSkills.class, DTOTemplate.class, DTOSavedResumes.class, DTOCoverLetter.class}, version = 2)
@TypeConverters({BitmapTypeConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase instance;
    private static final Object LOCK = new Object();
    public abstract UserDao userDao();

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (LOCK) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "resumecraft.db")
                            .allowMainThreadQueries() // Should be avoided for production apps
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return instance;
    }
}
