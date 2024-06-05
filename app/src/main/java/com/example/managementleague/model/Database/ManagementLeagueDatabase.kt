package com.example.managementleague.model.Database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.managementleague.model.entity.League
import com.example.managementleague.model.entity.LeagueDao
import com.example.managementleague.model.entity.Player
import com.example.managementleague.model.entity.PlayerDao
import com.example.managementleague.model.entity.Team
import com.example.managementleague.model.entity.TeamDao
import com.example.managementleague.model.entity.User
import com.example.managementleague.model.entity.UserDao
import com.example.managementleague.utils.Locator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

@Database(
    entities = [User::class, League::class, Team::class, Player::class],
    version = 2,
    exportSchema = false
)
abstract class ManagementLeagueDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun leagueDao(): LeagueDao
    abstract fun teamDao(): TeamDao
    abstract fun playerDao(): PlayerDao


    companion object {
        @Volatile
        private var INSTANCE: ManagementLeagueDatabase? = null
        fun getInstance(): ManagementLeagueDatabase {
            return INSTANCE ?: synchronized(ManagementLeagueDatabase::class) {
                val instance = buildDatabase()
                INSTANCE = instance
                instance
            }


        }

        private fun buildDatabase(): ManagementLeagueDatabase {
            return Room.databaseBuilder(
                Locator.requiredApplication, ManagementLeagueDatabase::class.java, "Manament League"
            ).fallbackToDestructiveMigration().allowMainThreadQueries()//quitarlo
                .addCallback(
                    RoomDbInitializer(INSTANCE)
                ).build()
        }
    }

    class RoomDbInitializer(val instance: ManagementLeagueDatabase?) : RoomDatabase.Callback() {

        private val applicationScope = CoroutineScope(SupervisorJob())

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            applicationScope.launch(Dispatchers.IO) {
                populateDatabase()
            }
        }

        private fun populateDatabase() {
            getInstance().let { managementLeagueDatabase ->
                managementLeagueDatabase.userDao().insert(User(1,"Alex","carnerotapiaalex@gmail.com","000000","682027895"))
            }
        }





    }
}