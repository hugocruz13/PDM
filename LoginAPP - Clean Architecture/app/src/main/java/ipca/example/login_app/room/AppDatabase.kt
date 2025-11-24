package ipca.example.login_app.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CartItemEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cartItemDao(): CartItemDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java, "database-cart"
                )   .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE!!
        }
    }
}