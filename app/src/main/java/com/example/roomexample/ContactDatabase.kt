package com.example.roomexample

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(entities = [Contact::class], version = 3)
@TypeConverters(Convertors::class)
abstract class ContactDatabase : RoomDatabase(){

    abstract fun contactDao() : ContactDao

    companion object{

        val migration_2_3 = object : Migration(2,3){
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE contact ADD COLUMN isActive INTEGER NOT NULL DEFAULT(1)")
            }
        }

        @Volatile
        private var INSTANCE: ContactDatabase? = null

        fun getDatabase(context: Context) : ContactDatabase{
            if (INSTANCE == null){
                synchronized(this){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        ContactDatabase::class.java,
                        "contactDB")
                        .addMigrations(migration_2_3)
//                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE!!
        }
    }

}