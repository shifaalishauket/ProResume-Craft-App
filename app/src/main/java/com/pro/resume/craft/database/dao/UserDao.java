package com.pro.resume.craft.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

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

import java.util.List;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DTOProfile user);


    @Query("UPDATE profiles SET profileId = :profileId, firstName = :firstName, lastName = :lastName, profilePhotoUrl = :profilePhotoUrl, email = :email WHERE id = :id")
    void updateProfileById(int id, String profileId, String firstName, String lastName, String profilePhotoUrl, String email);

    @Query("DELETE FROM profiles WHERE email =:email")
    void deleteProfileById(String email);


    @Query("SELECT * FROM profiles WHERE email =:email")
    DTOProfile findByEmail(String email);

    @Insert
    void insertCoverLetter(DTOCoverLetter coverLetter);

    @Query("DELETE FROM cover WHERE email =:email")
    void deleteCoverByEmail(String email);

    @Query("SELECT * FROM cover WHERE email =:email")
    LiveData<List<DTOCoverLetter>> findCoverByEmail(String email);


    @Query("UPDATE personalInfo SET firstName = :firstName, lastName = :lastName, profilePhotoUrl = :profilePhotoUrl, email = :email, uid = :uid, profession = :profession, phonenumber = :phonenumber, address = :address WHERE id = :id")
    void updatePersonalInfoById(int id, String firstName, String lastName, String profilePhotoUrl, String email, String uid, String profession, String phonenumber, String address);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPersonalInfo(DTOPersonalInfo DTOPersonalInfo);


    @Query("SELECT * FROM personalInfo WHERE email = :email LIMIT 1")
    DTOPersonalInfo getPersonalInfo(String email);


    @Query("SELECT * FROM Objective WHERE email = :email LIMIT 1")
    DTOObjective getObjectiveByEmail(String email);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertObjective(DTOObjective dtoObjective);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertExperiecne(DTOExperience dtoExperience);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEducation(DTOEducation dtoEducation);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertLanguage(DTOlanguages dtOlanguages);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void inserthobby(DTOHobbies dtoHobbies);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSkills(DTOSkills dtoSkills);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRefference(DTOReference dtoReference);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTemplate(DTOTemplate dtoTemplate);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertResume(DTOSavedResumes dtoSavedResumes);

    @Query("SELECT * FROM savedresume WHERE email = :email")
    LiveData<List<DTOSavedResumes>> getSavedResumes(String email);

    @Query("DELETE FROM savedresume WHERE id =:id")
    void deleteResumeById(int id);

    @Query("SELECT * FROM experience WHERE email = :email")
    LiveData<List<DTOExperience>> getExperiencesByEmail(String email);

    @Query("DELETE FROM experience WHERE id =:id")
    void deleteExperienceById(int id);

    @Query("SELECT * FROM education WHERE email = :email")
    LiveData<List<DTOEducation>> getEducationByEmail(String email);

    @Query("DELETE FROM education WHERE id =:id")
    void deleteEducationById(int id);


    @Query("SELECT * FROM languages WHERE email = :email")
    LiveData<List<DTOlanguages>> getLanguagesByEmail(String email);

    @Query("DELETE FROM languages WHERE id =:id")
    void deleteLanguageById(int id);


    @Query("SELECT * FROM hobbies WHERE email = :email")
    LiveData<List<DTOHobbies>> getHobbiesByEmail(String email);

    @Query("DELETE FROM Hobbies WHERE id =:id")
    void deleteHobbiesById(int id);


    @Query("SELECT * FROM skills WHERE email = :email")
    LiveData<List<DTOSkills>> getSkillsByEmail(String email);

    @Query("DELETE FROM skills WHERE id =:id")
    void deleteSkillsById(int id);


    @Query("SELECT * FROM reference WHERE email = :email")
    LiveData<List<DTOReference>> getReferebceByEmail(String email);

    @Query("DELETE FROM Reference WHERE id =:id")
    void deleteReferenceById(int id);

    @Query("SELECT * FROM template WHERE email = :email LIMIT 1")
    DTOTemplate getTemplateByEmail(String email);

    @Query("SELECT * FROM skills WHERE email=:email")
    List<DTOSkills> getAllSkills(String email);

    @Query("SELECT * FROM languages WHERE email=:email")
    List<DTOlanguages> getAllLanguages(String email);

    @Query("SELECT * FROM education WHERE email=:email")
    List<DTOEducation> getAllEducations(String email);

    @Query("SELECT * FROM experience WHERE email=:email")
    List<DTOExperience> getAllExperience(String email);


    @Query("SELECT * FROM hobbies WHERE email=:email")
    List<DTOHobbies> getAllHobbies(String email);


    @Query("SELECT * FROM reference WHERE email=:email")
    List<DTOReference> getAllReference(String email);




}
